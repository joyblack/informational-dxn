package com.joy.xxfy.informationaldxn.staff.service;

import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.position.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.*;
import com.joy.xxfy.informationaldxn.staff.domain.repository.*;
import com.joy.xxfy.informationaldxn.staff.web.req.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaffShiftService {

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffLeaveRepository staffLeaveRepository;

    @Autowired
    private StaffShiftRepository staffShiftRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    /**
     * 添加
     */
    public JoyResult add(StaffShiftAddReq req) {
        StaffBlacklistEntity staffBlacklistInfo = new StaffBlacklistEntity();
         // 验证身份证
        if(!IdNumberUtil.isIDNumber((req.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        // 个人信息验证
        StaffPersonalEntity personalInfo = staffPersonalRepository.findAllByIdNumber(req.getIdNumber());
        if(personalInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_PERSONAL_INFO_NOT_EXIST);
        }
        // 检测调入职位信息的合法性
        DepartmentEntity targetCompany = departmentRepository.findAllById(req.getTargetCompanyId());
        if(targetCompany == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }
        DepartmentEntity targetDepartment = departmentRepository.findAllById(req.getTargetDepartmentId());
        if(targetDepartment == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        PositionEntity targetPosition = positionRepository.findAllById(req.getTargetPositionId());
        if(targetPosition == null){
            return JoyResult.buildFailedResult(Notice.POSITION_NOT_EXIST);
        }

        // 依次处理每个调动职位信息
        // 一个职位一个职位的处理，不要批处理
        List<StaffEntryEntity> entries = new ArrayList<>();
        for (Long entryId : req.getEntries()) {
            StaffEntryEntity entry = staffEntryRepository.findAllById(entryId);
            if(entry == null){
                return JoyResult.buildFailedResult(Notice.STAFF_ENTRY_NOT_EXIST,"id为【" + entryId +"】的入职信息不存在");
            }else{
                entries.add(entry);
            }
        }
        // 删除旧职位信息
        for (StaffEntryEntity entry : entries) {
            // delete
            entry.setIsDelete(true);
            // 这里可能需要记录日志
            staffEntryRepository.save(entry);
        }
        // 添加新职位信息
        // 新职位初始化信息选之前的一个职位信息作为信息填充[CHANGE]
        StaffEntryEntity newEntryInfo = new StaffEntryEntity();
        BeanUtils.copyProperties(entries.get(0), newEntryInfo);
        newEntryInfo.setIsDelete(false);
        newEntryInfo.setId(0L);
        newEntryInfo.setCompany(targetCompany);
        newEntryInfo.setDepartment(targetDepartment);
        newEntryInfo.setPosition(targetPosition);
        staffEntryRepository.save(newEntryInfo);

        // 添加调动日志信息
        StaffShiftEntity staffShiftInfo = new StaffShiftEntity();
        staffShiftInfo.setShiftTime(req.getShiftTime());
        staffShiftInfo.setStaffPersonal(newEntryInfo.getStaffPersonal());
        staffShiftInfo.setTargetCompany(targetCompany);
        staffShiftInfo.setTargetDepartment(targetDepartment);
        staffShiftInfo.setTargetPosition(targetPosition);
        staffShiftInfo.setRemarks(req.getRemarks());
        StaffShiftEntity result = staffShiftRepository.save(staffShiftInfo);
        LogUtil.info("The last shift info is: {}", result);

        return JoyResult.buildSuccessResultWithData(result);
    }


    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        StaffShiftEntity shiftInfo = staffShiftRepository.findAllById(id);
        if(shiftInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_SHIFT_INFO_NOT_EXIST);
        }
        shiftInfo.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(staffShiftRepository.save(shiftInfo));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffShiftRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(StaffShiftGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffShiftRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(StaffShiftGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffShiftRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffShiftEntity> getPredicates(StaffShiftGetListReq req){
        return (Specification<StaffShiftEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // username like
            if(!StringUtil.isEmpty(req.getUsername())){
                predicates.add(builder.like(root.get("staffPersonal").get("username"), "%" + req.getUsername() +"%"));
            }
            // idNumber like
            if(!StringUtil.isEmpty(req.getIdNumber())){
                predicates.add(builder.like(root.get("staffPersonal").get("idNumber"), "%" + req.getIdNumber() +"%"));
            }
            // target_company_id = ?
            if(req.getTargetCompanyId() != null){
                predicates.add(builder.equal(root.get("targetCompany").get("id"),  req.getTargetCompanyId()));
            }
            // target_department_id = ?
            if(req.getTargetDepartmentId() != null){
                predicates.add(builder.equal(root.get("targetDepartment").get("id"),  req.getTargetDepartmentId()));
            }
            // target_position_id = ?
            if(req.getTargetPositionId() != null){
                predicates.add(builder.equal(root.get("targetPosition").get("id"),  req.getTargetPositionId()));
            }
            // injury_time between
            if(req.getShiftTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("shiftTime"), req.getShiftTimeStart()));
            }
            if(req.getShiftTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("shiftTime"), req.getShiftTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
