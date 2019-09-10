package com.joy.xxfy.informationaldxn.module.safe.service;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.safe.domain.entity.SafeInspectionEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.repository.SafeInspectionRepository;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.PerMonthTotalCountVo;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.RectificationStatusCountVo;
import com.joy.xxfy.informationaldxn.module.safe.web.req.*;
import com.joy.xxfy.informationaldxn.module.safe.web.res.ThisMonthStatusCountRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.RateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.format.AntVFormatUtil;
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
public class SafeInspectionService {
    @Autowired
    private SafeInspectionRepository safeInspectionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 添加
     */
    public JoyResult add(SafeInspectionAddReq req) {
        // 验证巡检煤矿是否存在
        DepartmentEntity inspectCompany = departmentRepository.findAllById(req.getInspectCompanyId());
        if(inspectCompany == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 验证巡检部门是否存在
        DepartmentEntity inspectDepartment = departmentRepository.findAllById(req.getInspectDepartmentId());
        if(inspectDepartment == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        Date now = DateUtil.getDateJustYMD();
        SafeInspectionEntity info = new SafeInspectionEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setInspectCompany(inspectCompany);
        info.setInspectDepartment(inspectDepartment);
        // 整改状态为未整改
        info.setRectificationStatus(RectificationStatusEnum.RECTIFICATION_NO);
        // 是否超时
        if(DateUtil.compare(now,info.getDeadTime()) > 0){
            info.setIsOverTime(CommonYesEnum.YES);
        }else{
            info.setIsOverTime(CommonYesEnum.NO);
        }
        // save.
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.save(info));
    }

    /**
     * 批量添加
     */
    public JoyResult add(SafeInspectionBatchAddReq req) {
        // 验证巡检煤矿是否存在
        DepartmentEntity inspectCompany = departmentRepository.findAllById(req.getInspectCompanyId());
        if(inspectCompany == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 验证巡检部门是否存在
        DepartmentEntity inspectDepartment = departmentRepository.findAllById(req.getInspectDepartmentId());
        if(inspectDepartment == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }

        List<SafeInspectionEntity> infos = new ArrayList<>();
        for (SafeInspectionProblemItem problemItem : req.getProblemItems()) {
            // 装配数据
            SafeInspectionEntity info = new SafeInspectionEntity();
            JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
            JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(problemItem, info);

            System.out.println(info);
            info.setInspectCompany(inspectCompany);
            info.setInspectDepartment(inspectDepartment);
            // 整改状态为未整改
            info.setRectificationStatus(RectificationStatusEnum.RECTIFICATION_NO);
            // 是否超时
            Date now = new Date();
            if(DateUtil.compare(now, info.getDeadTime()) > 0){
                info.setIsOverTime(CommonYesEnum.YES);
            }else{
                info.setIsOverTime(CommonYesEnum.NO);
            }
            // 开始提示的时间
            info.setTipStartTime(DateUtil.addDay(now, - info.getTipDays().intValue()));
            infos.add(info);
        }
        // save.
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.saveAll(infos));
    }

    /**
     * 改
     */
    public JoyResult update(SafeInspectionUpdateReq req) {
        // 巡检信息是否存在
        SafeInspectionEntity safeInspectionEntity = safeInspectionRepository.findAllById(req.getId());
        if(safeInspectionEntity == null){
            return JoyResult.buildFailedResult(Notice.SAFE_INSPECTION_NOT_EXIST);
        }
        // 验证巡检煤矿是否存在
        DepartmentEntity inspectCompany = departmentRepository.findAllById(req.getInspectCompanyId());
        if(inspectCompany == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 验证巡检部门是否存在
        DepartmentEntity inspectDepartment = departmentRepository.findAllById(req.getInspectDepartmentId());
        if(inspectDepartment == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, safeInspectionEntity);
        safeInspectionEntity.setInspectDepartment(inspectDepartment);
        safeInspectionEntity.setInspectCompany(inspectCompany);
        // 若当前处在未整改状态，修改超时状态以及提示时间
        Date now = new Date();
        if(safeInspectionEntity.getRectificationStatus().equals(RectificationStatusEnum.RECTIFICATION_NO)){
            if(DateUtil.compare(now, safeInspectionEntity.getDeadTime()) > 0){
                safeInspectionEntity.setIsOverTime(CommonYesEnum.YES);
            }else{
                safeInspectionEntity.setIsOverTime(CommonYesEnum.NO);
            }
            // 开始提示的时间
            safeInspectionEntity.setTipStartTime(DateUtil.addDay(now, - safeInspectionEntity.getTipDays().intValue()));
        }else{

        }
        // save.
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.save(safeInspectionEntity));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        // 巡检信息是否存在
        SafeInspectionEntity safeInspectionEntity = safeInspectionRepository.findAllById(id);
        if(safeInspectionEntity == null){
            return JoyResult.buildFailedResult(Notice.SAFE_INSPECTION_NOT_EXIST);
        }
        safeInspectionEntity.setIsDelete(true);
        safeInspectionRepository.save(safeInspectionEntity);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(SafeInspectionGetListReq req) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(SafeInspectionGetListReq req) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<SafeInspectionEntity> getPredicates(SafeInspectionGetListReq req){
        return (Specification<SafeInspectionEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // == time_between
            // inspectTime
            if(req.getInspectTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("inspectTime"), req.getInspectTimeStart()));
            }
            if(req.getInspectTimeEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("inspectTime"), req.getInspectTimeEnd()));
            }
            // deadTime
            if(req.getDeadTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("deadTime"), req.getDeadTimeStart()));
            }
            if(req.getInspectTimeEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("deadTime"), req.getDeadTimeEnd()));
            }
            // inspectCompany
            if(req.getInspectCompanyId() != null){
                predicates.add(builder.equal(root.get("inspectCompany").get("id"), req.getInspectCompanyId()));
            }
            // inspectDepartment
            if(req.getInspectDepartmentId() != null){
                predicates.add(builder.equal(root.get("inspectDepartment").get("id"), req.getInspectDepartmentId()));
            }
            // inspectType
            if(req.getInspectType() != null){
                predicates.add(builder.equal(root.get("inspectType"), req.getInspectType()));
            }
            // rectificationStatus
            if(req.getInspectType() != null){
                predicates.add(builder.equal(root.get("rectificationStatus"), req.getRectificationStatus()));
            }

            // overTime
            if(req.getIsOverTime() != null){
                predicates.add(builder.equal(root.get("isOverTime"), req.getIsOverTime()));
            }

            // rectificationPeople
            if(req.getRectificationPeople() != null){
                predicates.add(builder.equal(root.get("rectificationPeople"), req.getRectificationPeople()));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    /**
     * 设置整改状态
     */
    public JoyResult changeRectificationStatus(ChangeRectificationStatusReq req) {
        List<SafeInspectionEntity> safeInspectionEntities = new ArrayList<>();
        // 获取每一条信息
        for (Long id : req.getIds()) {
            SafeInspectionEntity info = safeInspectionRepository.findAllById(id);
            if(info != null){
                safeInspectionEntities.add(info);
            }
        }

        if(safeInspectionEntities.size() == 0){
            return JoyResult.buildFailedResult(Notice.SAFE_INSPECTION_NOT_EXIST);
        }
        // 修改为已整改
        if(req.getRectificationStatus().equals(RectificationStatusEnum.RECTIFICATION_YES)){
            // 当前时间
            for (SafeInspectionEntity safeInspectionEntity : safeInspectionEntities) {
                Date now = new Date();
                // 若原来的状态就是处理状态，则无需处理
                if(safeInspectionEntity.getRectificationStatus().equals(RectificationStatusEnum.RECTIFICATION_NO)){
                    // 设置为已整改
                    safeInspectionEntity.setRectificationStatus(req.getRectificationStatus());
                    // 是否超时
                    if(DateUtil.compare(now,safeInspectionEntity.getDeadTime()) > 0){
                        safeInspectionEntity.setIsOverTime(CommonYesEnum.YES);
                    }else{
                        safeInspectionEntity.setIsOverTime(CommonYesEnum.NO);
                    }
                    // 整改人信息
                    safeInspectionEntity.setRectificationPeople(req.getRectificationPeople());
                    // 备注信息
                    safeInspectionEntity.setRemarks(req.getRemarks());
                    // 修改时间
                    safeInspectionEntity.setUpdateTime(now);
                }
            }
        }else{// 修改为未整改

        }
        // 保存所有信息
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.saveAll(safeInspectionEntities));
    }

    public JoyResult getApproachRectificationNum(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.getAllApproach(RectificationStatusEnum.RECTIFICATION_NO, new Date(),loginUser.getCompany()).size());
    }

    public JoyResult getApproachRectification(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.getAllApproach(RectificationStatusEnum.RECTIFICATION_NO, new Date(),loginUser.getCompany()));
    }


    public JoyResult getThisMonthStatusCount(UserEntity loginUser) {
        // 不想自动补0，为了个数据库进行搭配
        String ym = DateUtil.getYMString(new Date(),false);
        List<RectificationStatusCountVo> rectificationStatusCountVos = safeInspectionRepository.rectificationStatusCountByMonth(loginUser.getCompany(), ym);
        // 组装返回数据：总数、已整改、未整改、整改率
        ThisMonthStatusCountRes res = new ThisMonthStatusCountRes();
        for (RectificationStatusCountVo vo : rectificationStatusCountVos) {
            if(vo.getRectificationStatusEnum().equals(RectificationStatusEnum.RECTIFICATION_NO)){
                res.setNoNumber(vo.getCt());
            }
            if(vo.getRectificationStatusEnum().equals(RectificationStatusEnum.RECTIFICATION_YES)){
                res.setYesNumber(vo.getCt());
            }
        }
        // 若没有数据的设置
        if(res.getNoNumber() == null){
            res.setNoNumber(0L);
        }
        if(res.getYesNumber() == null){
            res.setYesNumber(0L);
        }
        // 总数统计
        res.setTotalNumber(res.getNoNumber() + res.getYesNumber());
        // 完成率统计
        res.setRate(RateUtil.compute(res.getYesNumber(), res.getTotalNumber(),true));
        return JoyResult.buildSuccessResultWithData(res);
    }


    public JoyResult getPerMonthTotalCount(UserEntity loginUser) {
        // 不想自动补0，为了个数据库进行搭配
        String year = DateUtil.getYearString(new Date());
        List<PerMonthTotalCountVo> perMonthTotalCount = safeInspectionRepository.getPerMonthTotalCount(loginUser.getCompany(), Integer.valueOf(year));
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatWithKeyString(perMonthTotalCount));
    }
}
