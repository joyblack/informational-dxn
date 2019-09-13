package com.joy.xxfy.informationaldxn.module.backmining.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.*;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo;
import com.joy.xxfy.informationaldxn.module.common.enums.FillDailyStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.common.web.res.FillResultRes;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.RateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.TransferValueUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
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
public class BackMiningDailyService {

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 添加
     */
    public JoyResult add(BackMiningDailyAddReq req) {
        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L: req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO: req.getOutput());
        // 验证工作面信息是否存在
        BackMiningFaceEntity backMiningFace = backMiningFaceRepository.findAllById(req.getBackMiningFaceId());
        if(backMiningFace == null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NOT_EXIST);
        }
        // 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getTeamId());
        if(team == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同工作面、日期、同队伍、同班次)
        BackMiningDailyEntity repeat = backMiningDailyRepository.findFirstByBackMiningFaceAndDailyTimeAndTeamAndShifts(backMiningFace, req.getDailyTime(), team, req.getShifts());
        if(repeat != null){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST,  ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
        }
        // 若提交的长度需要小0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 若当前提交的长度大于剩余长度，不合法
        if(more(req.getDoneLength(), backMiningFace.getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + backMiningFace.getLeftLength());
        }
        // == 修改工作面信息
        // 已掘长度：oldDoneLength + doneLength
        backMiningFace.setDoneLength(backMiningFace.getDoneLength().add(req.getDoneLength()));
        // 剩余长度
        backMiningFace.setLeftLength(backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength()));
        // 进度
        backMiningFace.setProgress(RateUtil.compute(backMiningFace.getDoneLength(),backMiningFace.getSlopeLength(),false));
        // 更新时间
        backMiningFace.setUpdateTime(new Date());

        // 装配实体：日报信息
        BackMiningDailyEntity info = new BackMiningDailyEntity();
        // 日期
        info.setDailyTime(req.getDailyTime());
        // 工作面信息
        info.setBackMiningFace(backMiningFace);
        // 班次
        info.setShifts(req.getShifts());
        // 掘进队伍
        info.setTeam(team);
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
        return JoyResult.buildSuccessResultWithData(backMiningDailyRepository.save(info));
    }

    /**
     * 上报（批量）
     */
    public JoyResult batchSave(BackMiningDailySaveReq req) {
        /**
         * 获取工作面信息
         */
        BackMiningFaceEntity backMiningFace = backMiningFaceRepository.findAllById(req.getBackMiningFaceId());
        if(backMiningFace == null){
            throw new JoyException(Notice.BACK_MINING_NOT_EXIST);
        }

        /**
         * 清空旧数据
         * 1. 统计旧数据的总数值：进尺(产煤未统计)
         * 2. 删除旧数据
         * 3. 还原工作面的进尺、进度等信息
         */
        SingleValueVo<BigDecimal> vo = backMiningDailyRepository.statisticDoneLength(backMiningFace, req.getDailyTime());
        backMiningDailyRepository.deleteAllByBackMiningFaceAndDailyTime(backMiningFace, req.getDailyTime());
        BigDecimal voDoneLength = TransferValueUtil.get(vo.getValue());
        if(!voDoneLength.equals(BigDecimal.ZERO)){
            // 已掘长度：oldDoneLength + doneLength
            backMiningFace.setDoneLength(backMiningFace.getDoneLength().subtract(vo.getValue()));
            // 剩余长度：total - doneLength
            backMiningFace.setLeftLength(backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength()));
            backMiningFace.setProgress(RateUtil.compute(backMiningFace.getDoneLength(), backMiningFace.getSlopeLength(), false));
        }
        /**
         * 批量添加新数据
         */
        BigDecimal totalLength = BigDecimal.ZERO;
        List<BackMiningDailyEntity> dailies = new ArrayList<>();
        for (BackMiningDailySaveItem item : req.getItems()) {
            /**
             * 验证：
             * 1. 队伍是否存在
             * 2. 该记录是否重复(同工作面、同日期、同队伍、同班次)
             * 3. 提交的长度是否非法:若当前提交的总长度大于工作面的剩余长度，不合法
             */
            DepartmentEntity team = departmentRepository.findAllById(item.getTeamId());
            if (team == null) {
                throw new JoyException(Notice.DEPARTMENT_NOT_EXIST);
            }
            BackMiningDailyEntity repeat = backMiningDailyRepository.findFirstByBackMiningFaceAndDailyTimeAndTeamAndShifts(backMiningFace, req.getDailyTime(), team, item.getShifts());
            if(repeat != null){
                throw new JoyException(Notice.DAILY_ALREADY_EXIST);
            }
            totalLength = totalLength.add(item.getDoneLength());
            if (more(totalLength, backMiningFace.getLeftLength())) {
                throw new JoyException(Notice.SET_LENGTH_MORE_LEFT_LENGTH.getMessage() + ":" + ResultDataConstant.MESSAGE_LEFT_LENGTH + backMiningFace.getLeftLength());
            }
            /**
             * 添加日报信息到批量数组
             */
            BackMiningDailyEntity info = new BackMiningDailyEntity();
            // 日期
            info.setDailyTime(req.getDailyTime());
            // 工作面信息
            info.setBackMiningFace(backMiningFace);
            // 班次
            info.setShifts(item.getShifts());
            // 掘进队伍
            info.setTeam(team);
            // 人数
            info.setPeopleNumber(TransferValueUtil.get(item.getPeopleNumber()));
            // 进尺(工作长度)
            info.setDoneLength(item.getDoneLength());
            // 产量
            info.setOutput(TransferValueUtil.get(item.getOutput()));
            // 其他工作内容
            info.setWorkContent(item.getWorkContent());
            // 备注信息
            info.setRemarks(req.getRemarks());
            dailies.add(info);
        }
        /**
         * 数据提交：
         * 1. 批量添加日报信息
         * 2. 修改工作面长度、剩余长度以及进度信息
         */
        if(dailies.size() > 0){
            backMiningDailyRepository.saveAll(dailies);
            // 已掘长度：oldDoneLength + doneLength
            backMiningFace.setDoneLength(backMiningFace.getDoneLength().add(totalLength));
            // 剩余长度：total - doneLength
            backMiningFace.setLeftLength(backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength()));
            backMiningFace.setProgress(RateUtil.compute(backMiningFace.getDoneLength(), backMiningFace.getSlopeLength(), false));
        }
        backMiningFaceRepository.save(backMiningFace);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_ADD_SUCCESS);
    }


    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        // 获取日报信息
        BackMiningDailyEntity info = backMiningDailyRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // == 修改工作面信息
        BackMiningFaceEntity backMiningFace = info.getBackMiningFace();
        // 修改已掘长度
        backMiningFace.setDoneLength(backMiningFace.getDoneLength().subtract(info.getDoneLength()));
        // 剩余长度
        backMiningFace.setLeftLength(backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength()));
        // 修改进度
        backMiningFace.setProgress(RateUtil.compute(backMiningFace.getDoneLength(),backMiningFace.getSlopeLength(),false));
        // 修改时间
        backMiningFace.setUpdateTime(new Date());

        // 设置删除状态
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        // save.
        backMiningDailyRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(backMiningDailyRepository.findAllById(id));
    }

    /**
     * 改
     */
    public JoyResult update(BackMiningDailyUpdateReq req) {
        // 获取日报信息
        BackMiningDailyEntity info = backMiningDailyRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getTeamId());
        if(team == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同工作面、日期、同队伍、同班次)
        BackMiningDailyEntity repeat = backMiningDailyRepository.findFirstByBackMiningFaceAndDailyTimeAndTeamAndShifts(info.getBackMiningFace(), info.getDailyTime(), team, req.getShifts());
        if(repeat != null && !repeat.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST,  ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
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

        // 若差值大于剩余长度，不合法
        BackMiningFaceEntity backMiningFace = info.getBackMiningFace();
        if(more(offsetDoneLength, backMiningFace.getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + backMiningFace.getLeftLength());
        }

        // 装配信息
        // 班次
        info.setShifts(req.getShifts());
        // 掘进队伍
        info.setTeam(team);
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
        backMiningFace.setDoneLength(backMiningFace.getDoneLength().add(offsetDoneLength));
        // 剩余长度
        backMiningFace.setLeftLength(backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength()));
        // 修改进度
        backMiningFace.setProgress(RateUtil.compute(backMiningFace.getDoneLength(),backMiningFace.getSlopeLength(),false));
        // 修改时间
        backMiningFace.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(backMiningDailyRepository.save(info));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BackMiningDailyGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(backMiningDailyRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BackMiningDailyGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(backMiningDailyRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<BackMiningDailyEntity> getPredicates(BackMiningDailyGetListReq req, UserEntity loginUser){
        return (Specification<BackMiningDailyEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // belong
            predicates.add(builder.equal(root.get("backMiningFace").get("belongCompany"),loginUser.getCompany()));

            if(req.getBackMiningFaceId() != null){
                predicates.add(builder.equal(root.get("backMiningFace").get("id"), req.getBackMiningFaceId()));
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
            if(req.getTeamId() != null){
                predicates.add(builder.equal(root.get("team").get("id"), req.getTeamId()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    public JoyResult getMonthFillStatus(TimeReq req, UserEntity loginUser) {
        Date start = DateUtil.getMonthFirstDay(req.getTime());
        Date end = DateUtil.getMonthLastDay(req.getTime());
        Set<DateVo> allFillDate = backMiningDailyRepository.findAllFillDate(start, end, loginUser.getCompany());
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
