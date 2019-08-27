package com.joy.xxfy.informationaldxn.module.backmining.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningDailyDetailAddReq;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningDailyDetailGetListReq;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningDailyDetailUpdateReq;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
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
public class BackMiningDailyDetailService {

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    @Autowired
    private BackMiningDailyDetailRepository backMiningDailyDetailRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    /**
     * 添加
     */
    public JoyResult add(BackMiningDailyDetailAddReq req) {
        // == 验证
        // 验证日报信息是否存在：该日期的掘进日报是否已经填写(不会有多个煤矿平台操作的，因此这个变量无需考虑)
        BackMiningDailyEntity backMiningDaily = backMiningDailyRepository.findAllById(req.getBackMiningDailyId());
        if(backMiningDaily == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 验证队伍是否存在
        DepartmentEntity team = departmentRepository.findAllById(req.getTeamId());
        if(team == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证该记录是否重复(同日报、同队伍、同班次)
        BackMiningDailyDetailEntity detailRepeat = backMiningDailyDetailRepository.findAllByBackMiningDailyAndTeamAndShifts(backMiningDaily, team, req.getShifts());
        if(detailRepeat != null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_ALREADY_EXIST, "同队伍、同班次的信息已经存在");
        }
        // 若提交的长度需要小0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 若当前提交的长度大于剩余长度，不合法
        BackMiningFaceEntity backMiningFace = backMiningDaily.getBackMiningFace();
        BigDecimal leftLength = backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength());
        if(more(req.getDoneLength(), leftLength)){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + leftLength);
        }
        // 初始化某些值
        req.setPeopleNumber(req.getPeopleNumber() == null ? 0L: req.getPeopleNumber());
        req.setOutput(req.getOutput() == null? BigDecimal.ZERO: req.getOutput());

        // == 修改日报信息（汇总）
        // 总人数
        backMiningDaily.setTotalPeopleNumber(backMiningDaily.getTotalPeopleNumber() + req.getPeopleNumber());
        // 总进尺
        backMiningDaily.setTotalDoneLength(backMiningDaily.getTotalDoneLength().add(req.getDoneLength()));
        // 总产量
        backMiningDaily.setTotalOutput(backMiningDaily.getTotalOutput().add(req.getOutput()));
        // 修改时间
        backMiningDaily.setUpdateTime(new Date());

        // == 修改工作面信息
        // 已掘长度：oldDoneLength + doneLength
        backMiningFace.setDoneLength(backMiningDaily.getBackMiningFace().getDoneLength().add(req.getDoneLength()));
        // 更新时间
        backMiningFace.setUpdateTime(new Date());
//        // 剩余长度：total - doneLength
//        backMiningDaily.getbackMiningFace().setLeftLength(backMiningDaily.getbackMiningFace().getTotalLength().subtract(backMiningDaily.getbackMiningFace().getDoneLength()));

        // 添加日报详情信息
        BackMiningDailyDetailEntity detail = new BackMiningDailyDetailEntity();
        // 关联的日报信息
        detail.setBackMiningDaily(backMiningDaily);
        // 班次
        detail.setShifts(req.getShifts());
        // 掘进队伍
        detail.setTeam(team);
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
        return JoyResult.buildSuccessResultWithData(backMiningDailyDetailRepository.save(detail));
    }


    /**
     * 改
     */
    public JoyResult update(BackMiningDailyDetailUpdateReq req) {
        // 获取打钻详情信息
        BackMiningDailyDetailEntity detail = backMiningDailyDetailRepository.findAllById(req.getId());
        if(detail == null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_NOT_EXIST);
        }
        // 验证该记录是否重复(同日报、同队伍、同班次)
        BackMiningDailyDetailEntity detailRepeat = backMiningDailyDetailRepository.findAllByBackMiningDailyAndTeamAndShifts(detail.getBackMiningDaily(),
                detail.getTeam(), detail.getShifts());
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

        // 若差值大于剩余长度，不合法
        BackMiningFaceEntity backMiningFace = detail.getBackMiningDaily().getBackMiningFace();
        BigDecimal leftLength = backMiningFace.getSlopeLength().subtract(backMiningFace.getDoneLength());
        if(more(offsetDoneLength, leftLength)){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + leftLength);
        }
        // 班次
        detail.setShifts(req.getShifts());
        // 掘进队伍
        detail.setTeam(departmentRepository.findAllById(req.getTeamId()));
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
        BackMiningDailyEntity backMiningDaily = detail.getBackMiningDaily();
        backMiningDaily.setTotalPeopleNumber(backMiningDaily.getTotalPeopleNumber() + offsetPeopleNumber);
        // 总进尺
        backMiningDaily.setTotalDoneLength(backMiningDaily.getTotalDoneLength().add(offsetDoneLength));
        // 总产量
        backMiningDaily.setTotalOutput(backMiningDaily.getTotalOutput().add(offsetOutput));
        // 修改时间
        backMiningDaily.setUpdateTime(new Date());

        // == 修改工作面信息
        // 修改已掘长度
        backMiningFace.setDoneLength(backMiningFace.getDoneLength().add(offsetDoneLength));
        // 修改时间
        backMiningFace.setUpdateTime(new Date());
        // 修改剩余长度
        //backMiningFace.setLeftLength(backMiningFace.getTotalLength().subtract(backMiningFace.getDoneLength()));
        // save.
        return JoyResult.buildSuccessResultWithData(backMiningDailyDetailRepository.save(detail));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        BackMiningDailyDetailEntity detail = backMiningDailyDetailRepository.findAllById(id);
        if(detail == null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_NOT_EXIST);
        }
        // == 修改日报信息
        BackMiningDailyEntity backMiningDaily = detail.getBackMiningDaily();
        // 总人数
        backMiningDaily.setTotalPeopleNumber(backMiningDaily.getTotalPeopleNumber() - detail.getPeopleNumber());
        // 总进尺
        backMiningDaily.setTotalDoneLength(backMiningDaily.getTotalDoneLength().subtract(detail.getDoneLength()));
        // 总产量
        backMiningDaily.setTotalOutput(backMiningDaily.getTotalOutput().subtract(detail.getOutput()));
        // 修改时间
        backMiningDaily.setUpdateTime(new Date());

        // == 修改工作面信息
        BackMiningFaceEntity backMiningFace = detail.getBackMiningDaily().getBackMiningFace();
        // 修改已掘长度
        backMiningFace.setDoneLength(backMiningFace.getDoneLength().subtract(detail.getDoneLength()));
        // 修改时间
        backMiningFace.setUpdateTime(new Date());
        // 修改剩余长度
        // backMiningFace.setLeftLength(backMiningFace.getTotalLength().subtract(backMiningFace.getDoneLength()));

        // 设置删除状态
        detail.setIsDelete(true);
        // save.
        backMiningDailyDetailRepository.save(detail);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(backMiningDailyDetailRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BackMiningDailyDetailGetListReq req) {
        return JoyResult.buildSuccessResultWithData(backMiningDailyDetailRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BackMiningDailyDetailGetListReq req) {
        return JoyResult.buildSuccessResultWithData(backMiningDailyDetailRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<BackMiningDailyDetailEntity> getPredicates(BackMiningDailyDetailGetListReq req){
        return (Specification<BackMiningDailyDetailEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(req.getBackMiningFaceId() != null){
                predicates.add(builder.equal(root.get("backMiningDaily").get("backMiningFace").get("id"), req.getBackMiningFaceId()));
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
}
