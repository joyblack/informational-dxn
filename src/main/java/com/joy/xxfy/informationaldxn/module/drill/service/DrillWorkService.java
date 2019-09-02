package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo;
import com.joy.xxfy.informationaldxn.module.common.web.res.WorkProgressRes;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkGetListReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkUpdateReq;
import com.joy.xxfy.informationaldxn.module.drill.web.res.DrillWorkRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.LongValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.RateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DrillWorkService {

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillHoleRepository drillHoleRepository;

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    /**
     * 添加
     */
    public JoyResult add(DrillWorkAddReq req, UserEntity loginUser) {
        // 验证名称是否重复
        DrillWorkEntity drillWorkInfo = drillWorkRepository.findAllByDrillWorkName(req.getDrillWorkName());
        if(drillWorkInfo != null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NAME_ALREADY_EXIST);
        }
        // copy properties
        DrillWorkEntity info = new DrillWorkEntity();
        JoyBeanUtil.copyPropertiesIgnoreTargetNotNullProperties(req, info);
        LogUtil.info("Last info is: {}", info);

        // 初始化统计参数
        // 钻孔总数
        info.setTotalDrillHoleNumber(LongValueConstant.ZERO);
        // 钻孔总量
        info.setTotalLength(BigDecimalValueConstant.ZERO);
        // 已施工钻孔总数
        info.setCompletedDrillHoleNumber(LongValueConstant.ZERO);
        // 未施工钻孔总数: 钻孔总数 - 已施工钻孔总数
        info.setNotCompletedDrillHoleNumber(info.getTotalDrillHoleNumber() - info.getCompletedDrillHoleNumber());
        // 已打总量
        info.setTotalDoneLength(BigDecimalValueConstant.ZERO);
        // 未打总量：钻孔总量 - 已打总量
        info.setTotalLeftLength(info.getTotalLength().subtract(info.getTotalDoneLength()));
        // 进度
        info.setProgress(RateUtil.compute(info.getTotalDoneLength(), info.getTotalLength(),false));
        // 所属平台
        info.setBelongCompany(loginUser.getCompany());
        DrillWorkEntity save = drillWorkRepository.save(info);
        // set return data.
        DrillWorkRes result = new DrillWorkRes();
        JoyBeanUtil.copyProperties(save, result);
        return JoyResult.buildSuccessResultWithData(result);
    }

    /**
     * 改
     */
    public JoyResult update(DrillWorkUpdateReq req) {
        DrillWorkEntity info = drillWorkRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // 名称合法
        DrillWorkEntity checkRepeat = drillWorkRepository.findAllByDrillWorkNameAndIdNot(req.getDrillWorkName(), req.getId());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NAME_ALREADY_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setUpdateTime(new Date());
        DrillWorkEntity save = drillWorkRepository.save(info);
        // 装配返回信息
        DrillWorkRes result = new DrillWorkRes();
        JoyBeanUtil.copyProperties(save, result);
        result.setDrillHole(drillHoleRepository.findAllByDrillWork(save));
        // save.
        return JoyResult.buildSuccessResultWithData(result);
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        DrillWorkEntity info = drillWorkRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // == 已添加日报的不能删除
        List<DrillDailyEntity> dailies = drillDailyRepository.findAllByDrillWork(info);
        if(dailies.size() > 0){
            return JoyResult.buildFailedResult(Notice.DAILY_EXIST_CANT_DELETE);
        }
        // 先删除关联的钻孔的信息
        drillHoleRepository.updateIsDeleteByDrillWork(info, true);
        // 再删除工作信息
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        drillWorkRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        DrillWorkRes res = new DrillWorkRes();
        DrillWorkEntity info = drillWorkRepository.findAllById(id);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 获取详细信息
            res.setDrillHole(drillHoleRepository.findAllByDrillWork(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name) {
        DrillWorkRes res = new DrillWorkRes();
        DrillWorkEntity info = drillWorkRepository.findAllByDrillWorkName(name);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 获取详细信息
            res.setDrillHole(drillHoleRepository.findAllByDrillWork(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrillWorkGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrillWorkGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrillWorkEntity> getPredicates(DrillWorkGetListReq req,UserEntity loginUser){
        return (Specification<DrillWorkEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("belongCompany"),loginUser.getCompany()));

            // name like
            if(!StringUtil.isEmpty(req.getDrillWorkName())){
                predicates.add(builder.like(root.get("drillWorkName"), "%" + req.getDrillWorkName() +"%"));
            }
            // drill_time between
            if(req.getDrillTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("drillTime"), req.getDrillTimeStart()));
            }
            if(req.getDrillTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("drillTime"), req.getDrillTimeEnd()));
            }
            if(req.getDrillCategory() != null){
                predicates.add(builder.equal(root.get("drillCategory"), req.getDrillCategory()));
            }
            if(req.getDrillRockCharacter() != null){
                predicates.add(builder.equal(root.get("drillRockCharacter"), req.getDrillRockCharacter()));
            }
            if(req.getDrillType() != null){
                predicates.add(builder.equal(root.get("drillType"), req.getDrillType()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 工作完成进度，目前全统计
     */
    public JoyResult getWorkProgress(UserEntity loginUser) {
        List<WorkProgressVo> workProgress = drillWorkRepository.getWorkProgress(loginUser.getCompany());
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
