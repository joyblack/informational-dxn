package com.joy.xxfy.informationaldxn.module.driving.service;

import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingDailyDetailAddReq;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingDailyDetailGetListReq;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingDailyDetailUpdateReq;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
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

import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.less;
import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.more;

@Transactional
@Service
public class DrivingDailyDetailService {

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private DrivingDailyDetailRepository drivingDailyDetailRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    /**
     * 添加
     */
    public JoyResult add(DrivingDailyDetailAddReq req, UserEntity loginUser) {
        // == 验证
        // 验证日报信息是否存在：该日期的掘进日报是否已经填写(不会有多个煤矿平台操作的，因此这个变量无需考虑)
        DrivingDailyEntity drivingDaily = drivingDailyRepository.findAllById(req.getDrivingDailyId());
        if(drivingDaily == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getDrivingTeamId());
        if(team == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同日报、同队伍、同班次)
        DrivingDailyDetailEntity detailRepeat = drivingDailyDetailRepository.findAllByDrivingDailyAndDrivingTeamAndShifts(drivingDaily, team, req.getShifts());
        if(detailRepeat != null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
        }
        // 若提交的长度需要小0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 若当前提交的长度大于工作面的剩余长度，不合法
        if(more(req.getDoneLength(),drivingDaily.getDrivingFace().getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + drivingDaily.getDrivingFace().getLeftLength());
        }

        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L: req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO: req.getOutput());

        // == 修改日报信息（汇总）
        // 总人数
        drivingDaily.setTotalPeopleNumber(drivingDaily.getTotalPeopleNumber() + req.getPeopleNumber());
        // 总进尺
        drivingDaily.setTotalDoneLength(drivingDaily.getTotalDoneLength().add(req.getDoneLength()));
        // 总产量
        drivingDaily.setTotalOutput(drivingDaily.getTotalOutput().add(req.getOutput()));
        // 修改时间
        drivingDaily.setUpdateTime(new Date());

        // == 修改工作面信息
        DrivingFaceEntity drivingFace = drivingDaily.getDrivingFace();
        // 已掘长度：oldDoneLength + doneLength
        drivingFace.setDoneLength(drivingFace.getDoneLength().add(req.getDoneLength()));
        // 剩余长度：total - doneLength
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        // 修改时间
        drivingFace.setUpdateTime(new Date());

        // 添加日报详情信息
        DrivingDailyDetailEntity detail = new DrivingDailyDetailEntity();
        // 关联的日报信息
        detail.setDrivingDaily(drivingDaily);
        // 班次
        detail.setShifts(req.getShifts());
        // 掘进队伍
        detail.setDrivingTeam(team);
        // 人数
        detail.setPeopleNumber(req.getPeopleNumber());
        // 进尺(工作长度)
        detail.setDoneLength(req.getDoneLength());
        // 产量
        detail.setOutput(req.getOutput());
        // 其他工作内容
        detail.setWorkContent(req.getWorkContent());
        // 备注信息
        detail.setRemarks(req.getRemarks());
        // 更新：日报（当前日报填写的几个数据的综合）
        // 更新：工作面信息（已掘长度，剩余长度）
        // 添加：日报详情
        return JoyResult.buildSuccessResultWithData(drivingDailyDetailRepository.save(detail));
    }


    /**
     * 改
     */
    public JoyResult update(DrivingDailyDetailUpdateReq req, UserEntity loginUser) {
        // 获取打钻详情信息
        DrivingDailyDetailEntity detail = drivingDailyDetailRepository.findAllById(req.getId());
        if(detail == null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_NOT_EXIST);
        }
        // 验证该记录是否重复(同日报、同队伍、同班次)
        DrivingDailyDetailEntity detailRepeat = drivingDailyDetailRepository.findAllByDrivingDailyAndDrivingTeamAndShifts(detail.getDrivingDaily(),
                detail.getDrivingTeam(), detail.getShifts());
        if(detailRepeat != null && !detail.getId().equals(detailRepeat.getId())){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
        }
        // 若提交的长度需要大于0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L: req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO: req.getOutput());

        // 变化值
        Long offsetPeopleNumber = req.getPeopleNumber() - detail.getPeopleNumber();
        BigDecimal offsetDoneLength = req.getDoneLength().subtract(detail.getDoneLength());
        BigDecimal offsetOutput = req.getOutput().subtract(detail.getOutput());

        // 若差值大于工作面的剩余长度，不合法
        if(more(offsetDoneLength,detail.getDrivingDaily().getDrivingFace().getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + detail.getDrivingDaily().getDrivingFace().getLeftLength());
        }

        // 班次
        detail.setShifts(req.getShifts());
        // 掘进队伍
        detail.setDrivingTeam(departmentRepository.findAllById(req.getDrivingTeamId()));
        // 人数
        detail.setPeopleNumber(req.getPeopleNumber());
        // 进尺(工作长度)
        detail.setDoneLength(req.getDoneLength());
        // 产量
        detail.setOutput(req.getOutput());
        // 其他工作内容
        detail.setWorkContent(req.getWorkContent());
        // 备注信息
        detail.setRemarks(req.getRemarks());

        // == 修改日报信息
        // 总人数
        DrivingDailyEntity drivingDaily = detail.getDrivingDaily();
        drivingDaily.setUpdateTime(new Date());
        drivingDaily.setTotalPeopleNumber(drivingDaily.getTotalPeopleNumber() + offsetPeopleNumber);
        // 总进尺
        drivingDaily.setTotalDoneLength(drivingDaily.getTotalDoneLength().add(offsetDoneLength));
        // 总产量
        drivingDaily.setTotalOutput(drivingDaily.getTotalOutput().add(offsetOutput));

        // == 修改工作面信息
        DrivingFaceEntity drivingFace = detail.getDrivingDaily().getDrivingFace();
        // 修改已掘长度
        drivingFace.setDoneLength(drivingFace.getDoneLength().add(offsetDoneLength));
        // 修改剩余长度
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        // 修改时间
        drivingFace.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(drivingDailyDetailRepository.save(detail));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        DrivingDailyDetailEntity detail = drivingDailyDetailRepository.findAllById(id);
        if(detail == null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_NOT_EXIST);
        }
        // == 修改日报信息
        DrivingDailyEntity drivingDaily = detail.getDrivingDaily();
        // 总人数
        drivingDaily.setTotalPeopleNumber(drivingDaily.getTotalPeopleNumber() - detail.getPeopleNumber());
        // 总进尺
        drivingDaily.setTotalDoneLength(drivingDaily.getTotalDoneLength().subtract(detail.getDoneLength()));
        // 总产量
        drivingDaily.setTotalOutput(drivingDaily.getTotalOutput().subtract(detail.getOutput()));
        // 修改时间
        drivingDaily.setUpdateTime(new Date());

        // == 修改工作面信息
        DrivingFaceEntity drivingFace = detail.getDrivingDaily().getDrivingFace();
        // 修改已掘长度
        drivingFace.setDoneLength(drivingFace.getDoneLength().subtract(detail.getDoneLength()));
        // 修改剩余长度
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        // 修改时间
        drivingFace.setUpdateTime(new Date());

        // 设置删除状态
        detail.setIsDelete(true);
        detail.setUpdateTime(new Date());
        // save.
        drivingDailyDetailRepository.save(detail);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingDailyDetailRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrivingDailyDetailGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingDailyDetailRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrivingDailyDetailGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingDailyDetailRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrivingDailyDetailEntity> getPredicates(DrivingDailyDetailGetListReq req, UserEntity loginUser){
        return (Specification<DrivingDailyDetailEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("drivingDaily").get("drivingFace").get("belongCompany"), loginUser.getCompany()));
            // belong
            predicates.add(builder.equal(root.get("belongCompany"),loginUser.getCompany()));

            if(req.getDrivingFaceId() != null){
                predicates.add(builder.equal(root.get("drivingDaily").get("drivingFace").get("id"), req.getDrivingFaceId()));
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
}
