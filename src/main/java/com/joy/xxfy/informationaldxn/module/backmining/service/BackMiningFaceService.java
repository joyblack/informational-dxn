package com.joy.xxfy.informationaldxn.module.backmining.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningFaceAddReq;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningFaceGetListReq;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningFaceUpdateReq;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo;
import com.joy.xxfy.informationaldxn.module.common.web.res.WorkProgressRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.*;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class BackMiningFaceService {

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    /**
     * 添加
     */
    public JoyResult add(BackMiningFaceAddReq req, UserEntity loginUser) {
        // 验证名称是否重复
        BackMiningFaceEntity backMiningFaceName = backMiningFaceRepository.findFirstByBackMiningFaceName(req.getBackMiningFaceName());
        if(backMiningFaceName != null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NAME_ALREADY_EXIST);
        }
        BackMiningFaceEntity info = new BackMiningFaceEntity();
        JoyBeanUtil.copyPropertiesIgnoreTargetNotNullProperties(req, info);

        BigDecimal doneLength = info.getReturnAirChute().add(info.getTransportChute()).divide(BigDecimalValueConstant.TWO,2, RoundingMode.HALF_UP);
        if(ComputeUtils.compare(doneLength, info.getSlopeLength()) > 0){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH);
        }

        // 已采长度：(回风顺槽 + 运输顺槽)/2
        info.setDoneLength(info.getReturnAirChute().add(info.getTransportChute()).divide(BigDecimalValueConstant.TWO, 2, RoundingMode.HALF_UP));
        // 剩余长度
        info.setLeftLength(info.getSlopeLength().subtract(info.getDoneLength()));
        // 进度
        info.setProgress(RateUtil.compute(info.getDoneLength(), info.getSlopeLength(), false));
        LogUtil.info("Last back mining face info is: {}", info);
        // save.
        info.setBelongCompany(loginUser.getCompany());
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.save(info));
    }

    /**
     * 改
     */
    public JoyResult update(BackMiningFaceUpdateReq req) {
        BackMiningFaceEntity info = backMiningFaceRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NOT_EXIST);
        }
        // 名称合法
        BackMiningFaceEntity checkRepeat = backMiningFaceRepository.findFirstByBackMiningFaceNameAndIdNot(req.getBackMiningFaceName(), req.getId());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NAME_ALREADY_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        // 计算已采长度
        info.setDoneLength(info.getReturnAirChute().add(info.getTransportChute()).divide(BigDecimalValueConstant.TWO,2,RoundingMode.HALF_UP));
        // 剩余长度
        info.setLeftLength(info.getSlopeLength().subtract(info.getDoneLength()));
        // 进度
        info.setProgress(RateUtil.compute(info.getDoneLength(), info.getSlopeLength(), false));
        // 修改时间
        info.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        BackMiningFaceEntity info = backMiningFaceRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NOT_EXIST);
        }
        // == 已添加日报的不能删除
        BackMiningDailyEntity daily = backMiningDailyRepository.findFirstByBackMiningFace(info);
        if(daily != null){
            return JoyResult.buildFailedResult(Notice.DAILY_EXIST_CANT_DELETE);
        }
        // 修改时间
        info.setUpdateTime(new Date());
        // 设置删除状态
        info.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.save(info));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findAllById(id));
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name) {
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findFirstByBackMiningFaceName(name));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BackMiningFaceGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findAll(getPredicates(req, loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BackMiningFaceGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findAll(getPredicates(req, loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<BackMiningFaceEntity> getPredicates(BackMiningFaceGetListReq req, UserEntity loginUser){
        return (Specification<BackMiningFaceEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("belongCompany"), loginUser.getCompany()));
            // name like
            if(!StringUtil.isEmpty(req.getBackMiningFaceName())){
                predicates.add(builder.like(root.get("backMiningFaceName"), "%" + req.getBackMiningFaceName() +"%"));
            }
            // start_time between
            if(req.getStartTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("startTime"), req.getStartTimeStart()));
            }
            if(req.getStartTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("startTime"), req.getStartTimeEnd()));
            }
            if(req.getVentilationMode() != null){
                predicates.add(builder.equal(root.get("ventilationMode"), req.getVentilationMode()));
            }
            if(req.getBackMiningMode() != null){
                predicates.add(builder.equal(root.get("backMiningMode"), req.getBackMiningMode()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 工作完成进度，目前全统计
     */
    public JoyResult getWorkProgress(UserEntity loginUser) {
        List<WorkProgressVo> workProgress = backMiningFaceRepository.getWorkProgress(loginUser.getCompany());
        // 装配返回数据
        List<WorkProgressRes> result = new ArrayList<>();
        for (WorkProgressVo progress : workProgress) {
            WorkProgressRes res = new WorkProgressRes();
            res.setDoneLength(progress.getDoneLength());
            res.setTotalLength(progress.getTotalLength());
            res.setWorkName(progress.getWorkName());
            // 计算剩余、百分比
            res.setLeftLength(res.getTotalLength().subtract(res.getDoneLength()));
            res.setProgress(RateUtil.compute(res.getDoneLength(), res.getTotalLength(),false));
            res.setProgressUsePercent(RateUtil.compute(res.getDoneLength(), res.getTotalLength(),true));
            result.add(res);
        }
        return JoyResult.buildSuccessResultWithData(result);
    }


}
