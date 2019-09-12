package com.joy.xxfy.informationaldxn.module.driving.service;

import com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo;
import com.joy.xxfy.informationaldxn.module.common.enums.FillDailyStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.common.web.res.FillResultRes;
import com.joy.xxfy.informationaldxn.module.driving.web.req.*;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.RateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.SaveReqUtil;
import com.joy.xxfy.informationaldxn.validate.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.less;
import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.more;

@Transactional
@Service
public class DrivingDailyService {

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;


    @Autowired
    private DepartmentRepository departmentRepository;


    /**
     * 添加
     */
    public JoyResult add(DrivingDailyAddReq req, UserEntity loginUser) {
        // 验证工作面信息是否存在
        DrivingFaceEntity drivingFace = drivingFaceRepository.findAllById(req.getDrivingFaceId());
        if(drivingFace == null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NOT_EXIST);
        }
        // == 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getDrivingTeamId());
        if(team == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同工作面、同日期、同队伍、同班次)
        DrivingDailyEntity detailRepeat = drivingDailyRepository.findFirstByDrivingFaceAndDailyTimeAndDrivingTeamAndShifts(drivingFace, req.getDailyTime(), team, req.getShifts());
        if(detailRepeat != null){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
        }
        // 若当前提交的长度大于工作面的剩余长度，不合法
        if(more(req.getDoneLength(),drivingFace.getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + drivingFace.getLeftLength());
        }

        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L : req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO : req.getOutput());

        // == 修改工作面信息
        // 已掘长度：oldDoneLength + doneLength
        drivingFace.setDoneLength(drivingFace.getDoneLength().add(req.getDoneLength()));
        // 剩余长度：total - doneLength
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
        // 修改时间
        drivingFace.setUpdateTime(new Date());


        // 添加日报信息
        DrivingDailyEntity daily = new DrivingDailyEntity();
        daily.setDailyTime(req.getDailyTime());
        daily.setDrivingFace(drivingFace);
        // 班次
        daily.setShifts(req.getShifts());
        // 掘进队伍
        daily.setDrivingTeam(team);
        // 人数
        daily.setPeopleNumber(req.getPeopleNumber());
        // 进尺(工作长度)
        daily.setDoneLength(req.getDoneLength());
        // 产量
        daily.setOutput(req.getOutput());
        // 其他工作内容
        daily.setWorkContent(req.getWorkContent());
        // 备注信息
        daily.setRemarks(req.getRemarks());

        // 添加日报
        // 更新：工作面信息（已掘长度，剩余长度）
        return JoyResult.buildSuccessResultWithData(drivingDailyRepository.save(daily));
    }

    /**
     * 上报（批量）
     */
    public JoyResult batchSave(ValidList<DrivingDailySaveReq> reqs, UserEntity loginUser) {
        SaveReqUtil.sort(reqs);
        for (DrivingDailySaveReq req : reqs) {
            if (req.getId() == null || req.getId().equals(0L)) {// new
                // 验证工作面信息是否存在
                DrivingFaceEntity drivingFace = drivingFaceRepository.findAllById(req.getDrivingFaceId());
                if (drivingFace == null) {
                    throw new JoyException(Notice.DRIVING_FACE_NOT_EXIST);
                }
                // == 验证队伍是否存在
                DepartmentEntity team = departmentRepository.findAllById(req.getDrivingTeamId());
                if (team == null) {
                    throw new JoyException(Notice.DEPARTMENT_NOT_EXIST);
                }
                // 验证该记录是否重复(同工作面、同日期、同队伍、同班次)
                DrivingDailyEntity detailRepeat = drivingDailyRepository.findFirstByDrivingFaceAndDailyTimeAndDrivingTeamAndShifts(drivingFace, req.getDailyTime(), team, req.getShifts());
                if (detailRepeat != null) {
                    throw new JoyException(Notice.DAILY_ALREADY_EXIST);
                }
                // 若当前提交的长度大于工作面的剩余长度，不合法
                if (more(req.getDoneLength(), drivingFace.getLeftLength())) {
                    throw new JoyException(Notice.SET_LENGTH_MORE_LEFT_LENGTH.getMessage() + ":" + ResultDataConstant.MESSAGE_LEFT_LENGTH + drivingFace.getLeftLength());
                }
                // 初始化某些值
                req.setPeopleNumber(req.getPeopleNumber() == null ? 0L : req.getPeopleNumber());
                req.setOutput(req.getOutput() == null ? BigDecimal.ZERO : req.getOutput());
                // == 修改工作面信息
                // 已掘长度：oldDoneLength + doneLength
                drivingFace.setDoneLength(drivingFace.getDoneLength().add(req.getDoneLength()));
                // 剩余长度：total - doneLength
                drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
                drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
                // 修改时间
                drivingFace.setUpdateTime(new Date());
                // 添加日报信息
                DrivingDailyEntity daily = new DrivingDailyEntity();
                daily.setDailyTime(req.getDailyTime());
                daily.setDrivingFace(drivingFace);
                // 班次
                daily.setShifts(req.getShifts());
                // 掘进队伍
                daily.setDrivingTeam(team);
                // 人数
                daily.setPeopleNumber(req.getPeopleNumber());
                // 进尺(工作长度)
                daily.setDoneLength(req.getDoneLength());
                // 产量
                daily.setOutput(req.getOutput());
                // 其他工作内容
                daily.setWorkContent(req.getWorkContent());
                // 备注信息
                daily.setRemarks(req.getRemarks());
                // 更新所有信息
                drivingDailyRepository.save(daily);
            } else if (req.getId() > 0) {// update
                // 获取日报信息
                DrivingDailyEntity info = drivingDailyRepository.findAllById(req.getId());
                if (info == null) {
                    throw new JoyException(Notice.DAILY_NOT_EXIST);
                }
                // == 验证队伍是否存在
                DepartmentEntity team = departmentRepository.findAllById(req.getDrivingTeamId());
                if (team == null) {
                    throw new JoyException(Notice.DEPARTMENT_NOT_EXIST);
                }
                // 验证该记录是否重复(同工作面、同日期、同队伍、同班次)
                DrivingDailyEntity repeat = drivingDailyRepository.findFirstByDrivingFaceAndDailyTimeAndDrivingTeamAndShifts(info.getDrivingFace(), info.getDailyTime(), team, req.getShifts());
                if (repeat != null && !repeat.getId().equals(info.getId())) {
                    throw new JoyException(Notice.DAILY_ALREADY_EXIST);
                }
                // 若提交的长度需要大于0
                if (less(req.getDoneLength(), BigDecimal.ZERO)) {
                    throw new JoyException(Notice.LENGTH_SHOULD_MORE_ZERO);
                }
                // 初始化某些值
                req.setPeopleNumber(req.getPeopleNumber() == null ? 0L : req.getPeopleNumber());
                req.setOutput(req.getOutput() == null ? BigDecimal.ZERO : req.getOutput());

                // 变化值
                BigDecimal offsetDoneLength = req.getDoneLength().subtract(info.getDoneLength());

                // 若差值大于工作面的剩余长度，不合法
                DrivingFaceEntity drivingFace = info.getDrivingFace();
                if (more(offsetDoneLength, drivingFace.getLeftLength())) {
                    throw new JoyException(Notice.SET_LENGTH_MORE_LEFT_LENGTH.getMessage() + ":" + ResultDataConstant.MESSAGE_LEFT_LENGTH + drivingFace.getLeftLength());
                }
                // 班次
                info.setShifts(req.getShifts());
                // 掘进队伍
                info.setDrivingTeam(departmentRepository.findAllById(req.getDrivingTeamId()));
                // 人数
                info.setPeopleNumber(req.getPeopleNumber());
                // 进尺(工作长度)
                info.setDoneLength(req.getDoneLength());
                // 产量
                info.setOutput(req.getOutput());
                // 其他工作内容
                info.setWorkContent(req.getWorkContent());
                // 备注信息
                info.setRemarks(req.getRemarks());

                // == 修改工作面信息
                // 修改已掘长度
                drivingFace.setDoneLength(drivingFace.getDoneLength().add(offsetDoneLength));
                // 修改剩余长度
                drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
                // 进度计算
                drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
                // 修改时间
                drivingFace.setUpdateTime(new Date());
                // save.
                drivingDailyRepository.save(info);
            } else{ // delete
                Long id = Math.abs(req.getId());
                // 获取日报信息
                DrivingDailyEntity info = drivingDailyRepository.findAllById(id);
                if (info == null) {
                    throw new JoyException(Notice.DAILY_NOT_EXIST);
                }
                // == 修改工作面信息
                DrivingFaceEntity drivingFace = info.getDrivingFace();
                // 修改已掘长度
                drivingFace.setDoneLength(drivingFace.getDoneLength().subtract(info.getDoneLength()));
                // 修改剩余长度
                drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
                drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
                // 修改更新时间
                drivingFace.setUpdateTime(new Date());
                // 更新工作面信息
                drivingFaceRepository.save(drivingFace);
                // 删除日报信息
                drivingDailyRepository.deleteById(id);
            }
        }
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_ADD_SUCCESS);
    }

    /**
     * 新增
     */
    private void add(DrivingDailySaveReq req){
        // 验证工作面信息是否存在
        DrivingFaceEntity drivingFace = drivingFaceRepository.findAllById(req.getDrivingFaceId());
        if(drivingFace == null){
            throw new JoyException(Notice.DRIVING_FACE_NOT_EXIST);
        }
        // == 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getDrivingTeamId());
        if(team == null){
            throw new JoyException(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同工作面、同日期、同队伍、同班次)
        DrivingDailyEntity detailRepeat = drivingDailyRepository.findFirstByDrivingFaceAndDailyTimeAndDrivingTeamAndShifts(drivingFace, req.getDailyTime(), team, req.getShifts());
        if(detailRepeat != null){
            throw new JoyException(Notice.DAILY_ALREADY_EXIST);
        }
        // 若当前提交的长度大于工作面的剩余长度，不合法
        if(more(req.getDoneLength(),drivingFace.getLeftLength())){
            throw new JoyException(Notice.SET_LENGTH_MORE_LEFT_LENGTH.getMessage() + ":" + ResultDataConstant.MESSAGE_LEFT_LENGTH + drivingFace.getLeftLength());
        }
        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L : req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO : req.getOutput());
        // == 修改工作面信息
        // 已掘长度：oldDoneLength + doneLength
        drivingFace.setDoneLength(drivingFace.getDoneLength().add(req.getDoneLength()));
        // 剩余长度：total - doneLength
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
        // 修改时间
        drivingFace.setUpdateTime(new Date());
        // 添加日报信息
        DrivingDailyEntity daily = new DrivingDailyEntity();
        daily.setDailyTime(req.getDailyTime());
        daily.setDrivingFace(drivingFace);
        // 班次
        daily.setShifts(req.getShifts());
        // 掘进队伍
        daily.setDrivingTeam(team);
        // 人数
        daily.setPeopleNumber(req.getPeopleNumber());
        // 进尺(工作长度)
        daily.setDoneLength(req.getDoneLength());
        // 产量
        daily.setOutput(req.getOutput());
        // 其他工作内容
        daily.setWorkContent(req.getWorkContent());
        // 备注信息
        daily.setRemarks(req.getRemarks());
        // 更新所有信息
        drivingDailyRepository.save(daily);
    }


    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        // 获取日报信息
        DrivingDailyEntity info = drivingDailyRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // == 修改工作面信息
        DrivingFaceEntity drivingFace = info.getDrivingFace();
        // 修改已掘长度
        drivingFace.setDoneLength(drivingFace.getDoneLength().subtract(info.getDoneLength()));
        // 修改剩余长度
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
        // 修改时间
        drivingFace.setUpdateTime(new Date());

        // 删除日报信息
        info.setUpdateTime(new Date());
        info.setIsDelete(true);
        drivingDailyRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingDailyRepository.findAllById(id));
    }

    /**
     * 改
     */
    public JoyResult update(DrivingDailyUpdateReq req, UserEntity loginUser) {
        // 获取日报信息
        DrivingDailyEntity info = drivingDailyRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // == 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getDrivingTeamId());
        if(team == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同工作面、同日期、同队伍、同班次)
        DrivingDailyEntity repeat = drivingDailyRepository.findFirstByDrivingFaceAndDailyTimeAndDrivingTeamAndShifts(info.getDrivingFace(), info.getDailyTime(), team, req.getShifts());
        if(repeat != null && !repeat.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
        }
        // 若提交的长度需要大于0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L: req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO: req.getOutput());

        // 变化值
        Long offsetPeopleNumber = req.getPeopleNumber() - info.getPeopleNumber();
        BigDecimal offsetDoneLength = req.getDoneLength().subtract(info.getDoneLength());
        BigDecimal offsetOutput = req.getOutput().subtract(info.getOutput());

        // 若差值大于工作面的剩余长度，不合法
        DrivingFaceEntity drivingFace = info.getDrivingFace();
        if(more(offsetDoneLength,drivingFace.getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + drivingFace.getLeftLength());
        }

        // 班次
        info.setShifts(req.getShifts());
        // 掘进队伍
        info.setDrivingTeam(departmentRepository.findAllById(req.getDrivingTeamId()));
        // 人数
        info.setPeopleNumber(req.getPeopleNumber());
        // 进尺(工作长度)
        info.setDoneLength(req.getDoneLength());
        // 产量
        info.setOutput(req.getOutput());
        // 其他工作内容
        info.setWorkContent(req.getWorkContent());
        // 备注信息
        info.setRemarks(req.getRemarks());


        // == 修改工作面信息
        // 修改已掘长度
        drivingFace.setDoneLength(drivingFace.getDoneLength().add(offsetDoneLength));
        // 修改剩余长度
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        // 进度计算
        drivingFace.setProgress(RateUtil.compute(drivingFace.getDoneLength(), drivingFace.getTotalLength(), false));
        // 修改时间
        drivingFace.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(drivingDailyRepository.save(info));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrivingDailyGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingDailyRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrivingDailyGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingDailyRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrivingDailyEntity> getPredicates(DrivingDailyGetListReq req, UserEntity loginUser){
        return (Specification<DrivingDailyEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("drivingFace").get("belongCompany"), loginUser.getCompany()));
            if(req.getDrivingFaceId() != null){
                predicates.add(builder.equal(root.get("drivingFace").get("id"), req.getDrivingFaceId()));
            }
            // drill_time between
            if(req.getDailyTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("dailyTime"), req.getDailyTimeStart()));
            }
            if(req.getDailyTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("dailyTime"), req.getDailyTimeEnd()));
            }
            if(req.getShifts() != null){
                predicates.add(builder.equal(root.get("shifts"), req.getShifts()));
            }
            if(req.getDrivingTeamId() != null){
                predicates.add(builder.equal(root.get("drivingTeam").get("id"), req.getDrivingTeamId()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    public JoyResult getMonthFillStatus(TimeReq req, UserEntity loginUser) {
        Date start = DateUtil.getMonthFirstDay(req.getTime());
        Date end = DateUtil.getMonthLastDay(req.getTime());
        Set<DateVo> allFillDate = drivingDailyRepository.findAllFillDate(start, end, loginUser.getCompany());
        List<FillResultRes> result = new ArrayList<>();
        while(DateUtil.compare(start, end) <= 0){
            FillResultRes res = new FillResultRes();
            res.setDate(DateUtil.format(start));
            // 查询本日是否存在日报信息
            if(allFillDate.contains(new DateVo(start))){
                res.setInfo(FillDailyStatusEnum.FILL_YES);
                allFillDate.remove(start);
            }else{
                res.setInfo(FillDailyStatusEnum.FILL_NO);
            }
            start = DateUtil.addDay(start, 1);
            result.add(res);
        }
        return JoyResult.buildSuccessResultWithData(result);
    }
}
