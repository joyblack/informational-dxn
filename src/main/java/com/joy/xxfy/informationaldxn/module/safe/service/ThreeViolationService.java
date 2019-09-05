package com.joy.xxfy.informationaldxn.module.safe.service;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.safe.domain.entity.ThreeViolationEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.repository.ThreeViolationRepository;
import com.joy.xxfy.informationaldxn.module.safe.web.req.*;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ThreeViolationService {
    @Autowired
    private ThreeViolationRepository threeViolationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 添加
     */
    public JoyResult add(ThreeViolationAddReq req) {
        // 验证煤矿是否存在
        DepartmentEntity company = departmentRepository.findAllById(req.getViolationCompanyId());
        if(company == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 验证部门是否存在
        DepartmentEntity department = departmentRepository.findAllById(req.getViolationDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        ThreeViolationEntity info = new ThreeViolationEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setViolationCompany(company);
        info.setViolationDepartment(department);
        // save.
        return JoyResult.buildSuccessResultWithData(threeViolationRepository.save(info));
    }

    /**
     * 改
     */
    public JoyResult update(ThreeViolationUpdateReq req) {
        // 违章信息是否存在
        ThreeViolationEntity info = threeViolationRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.VIOLATION_NOT_EXIST);
        }
        // 验证煤矿是否存在
        DepartmentEntity company = departmentRepository.findAllById(req.getViolationCompanyId());
        if(company == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 验证部门是否存在
        DepartmentEntity department = departmentRepository.findAllById(req.getViolationDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setViolationCompany(company);
        info.setViolationDepartment(department);
        // save.
        return JoyResult.buildSuccessResultWithData(threeViolationRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        // 违章信息是否存在
        ThreeViolationEntity info = threeViolationRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.VIOLATION_NOT_EXIST);
        }
        info.setIsDelete(true);
        threeViolationRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(threeViolationRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(ThreeViolationGetListReq req) {
        return JoyResult.buildSuccessResultWithData(threeViolationRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(ThreeViolationGetListReq req) {
        return JoyResult.buildSuccessResultWithData(threeViolationRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<ThreeViolationEntity> getPredicates(ThreeViolationGetListReq req){
        return (Specification<ThreeViolationEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // company
            if(req.getViolationCompanyId() != null){
                predicates.add(builder.equal(root.get("violationCompany").get("id"), req.getViolationCompanyId()));
            }
            // department
            if(req.getViolationDepartmentId() != null){
                predicates.add(builder.equal(root.get("violationDepartment").get("id"), req.getViolationDepartmentId()));
            }
            // == time_between
            if(req.getViolationTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("violationTime"), req.getViolationTimeStart()));
            }
            if(req.getViolationTimeEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("violationTime"), req.getViolationTimeEnd()));
            }
            // people
            if(!StringUtil.isEmpty(req.getViolationPeople())){
                predicates.add(builder.like(root.get("violationPeople"), "%" + req.getViolationPeople() +"%"));
            }
            if(!StringUtil.isEmpty(req.getCheckPeople())){
                predicates.add(builder.like(root.get("checkPeople"), "%" + req.getCheckPeople() +"%"));
            }
            // place
            if(!StringUtil.isEmpty(req.getViolationPlace())){
                predicates.add(builder.like(root.get("violationPlace"), "%" + req.getViolationPlace() +"%"));
            }
            // shifts
            if(req.getDailyShift() != null){
                predicates.add(builder.equal(root.get("dailyShift"), req.getDailyShift()));
            }
            // type
            // rectificationStatus
            if(req.getThreeViolationType() != null){
                predicates.add(builder.equal(root.get("threeViolationType"), req.getThreeViolationType()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
