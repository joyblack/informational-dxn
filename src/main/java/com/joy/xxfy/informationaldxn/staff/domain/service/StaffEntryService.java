package com.joy.xxfy.informationaldxn.staff.domain.service;

import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.file.domain.entity.SystemFileEntity;
import com.joy.xxfy.informationaldxn.file.domain.repository.SystemFileRepository;
import com.joy.xxfy.informationaldxn.file.enums.UploadModuleEnums;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.position.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.PhoneUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.StaffStatusEnum;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.staff.domain.template.StaffTemplate;
import com.joy.xxfy.informationaldxn.staff.web.req.GetStaffEntryListReq;
import com.joy.xxfy.informationaldxn.staff.web.req.ReviewReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffEntryAddReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffEntryService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private SystemFileRepository systemFileRepository;

    /**
     * 添加员工入职信息
     */
    public JoyResult add(StaffEntryAddReq req) {
        // 创建员工基本信息对象
        StaffPersonalEntity personalInfo = new StaffPersonalEntity();
        // 员工入职信息对象
        StaffEntryEntity entryInfo = new StaffEntryEntity();

        // 姓名
        personalInfo.setUsername(req.getUsername());
        // 身份证号
        if(!IdNumberUtil.isIDNumber((req.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }else{
            personalInfo.setIdNumber(req.getIdNumber());
        }
        // 出生日期
        personalInfo.setBirthDate(req.getBirthDate());
        // 性别
        personalInfo.setSex(req.getSex());
        // 民族
        personalInfo.setNationality(req.getNationality());
        // 家庭住址
        personalInfo.setHomeAddress(req.getHomeAddress());
        // 户口性质
        personalInfo.setAccountCharacter(req.getAccountCharacter());
        // 文化程度
        personalInfo.setEducation(req.getEducation());
        // 联系电话
        if(!PhoneUtil.isMobile(req.getPhone())){
            return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
        }else{
            personalInfo.setPhone(req.getPhone());
        }
        // 入职公司、煤矿
        DepartmentEntity company = departmentRepository.findAllById(req.getCompanyId());
        if(company == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }else{
            entryInfo.setCompany(company);
        }
        // 入职部门
        DepartmentEntity department = departmentRepository.findAllById(req.getDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }else{
            entryInfo.setDepartment(department);
        }
        // 入职职务、工种
        PositionEntity position = positionRepository.findAllById(req.getPositionId());
        if(position == null){
            return JoyResult.buildFailedResult(Notice.POSITION_NOT_EXIST);
        }else{
            entryInfo.setPosition(position);
        }
        // 入职时间
        entryInfo.setEntryTime(req.getEntryTime());
        // 参保状态
        personalInfo.setInsured(req.getInsured());
        // 体检时间
        entryInfo.setPhysicalExaminationTime(req.getPhysicalExaminationTime());
        // 体检医院
        entryInfo.setPhysicalExaminationHospital(req.getPhysicalExaminationHospital());
        // 身份证照片
        if(req.getIdNumberPhotoId() != null){
            SystemFileEntity file = systemFileRepository.findAllByIdAndUploadModule(req.getIdNumberPhotoId(), UploadModuleEnums.STAFF_ID_NUMBER_PHOTO);
            if(file == null){
                return JoyResult.buildFailedResult(Notice.ID_NUMBER_PHOTO_IS_ILLEGAL);
            }else{
                personalInfo.setIdNumberPhoto(file);
            }
        }
        // 一寸相
        if(req.getOneInchPhotoId() != null){
            SystemFileEntity file = systemFileRepository.findAllByIdAndUploadModule(req.getOneInchPhotoId(), UploadModuleEnums.STAFF_ONE_INCH_PHOTO);
            if(file == null){
                return JoyResult.buildFailedResult(Notice.ONE_INCH_PHOTO_IS_ILLEGAL);
            }else{
                personalInfo.setOneInchPhoto(file);
            }
        }
        // 银行卡号
        entryInfo.setBankNumber(req.getBankNumber());
        // 开户行
        entryInfo.setOpenBank(req.getOpenBank());
        // 工资待遇
        entryInfo.setRemuneration(req.getRemuneration());
        // 工资待遇：按量计算
        entryInfo.setRemunerationL(req.getRemunerationL());
        // 备注信息
        entryInfo.setRemarks(req.getRemarks());


        // 查看用户是否在系统中存在关联信息
        StaffPersonalEntity personalCheck= staffPersonalRepository.findAllByIdNumber(personalInfo.getIdNumber());
        if(personalCheck == null){
            LogUtil.info("个人信息第一次录入.");
            // 新增个人信息
            personalInfo = staffPersonalRepository.save(personalInfo);
            // 审核状态为通过，可能以后需要查找黑名单
            entryInfo.setReviewStatus(ReviewStatusEnum.PASS);
        }else{
            LogUtil.info("系统中存在该员工的信息: {}", personalCheck);
            // 检测该用户是否同部门同职位出现，这是不合法的
            StaffEntryEntity checkStaff = staffEntryRepository.findAllByStaffPersonalAndDepartmentAndPosition(personalCheck,
                    entryInfo.getDepartment(),
                    entryInfo.getPosition());
            if(checkStaff != null){
                return JoyResult.buildFailedResult(Notice.STAFF_ALREADY_IN_DEPARTMENT);
            }
            // 检测该员工是否有重复的职位出现
            List<StaffEntryEntity> entryList = staffEntryRepository.findAllByStaffPersonal(personalCheck);
            if(entryList.size() > 0){
                // 记录原因
                StringBuilder reviewReasonBuilder = new StringBuilder();
                // 黑名单校验、重复部门校验
                for (StaffEntryEntity entry : entryList) {
                    if(entry.getStaffStatus().equals(StaffStatusEnum.BLACKLIST)){
                        reviewReasonBuilder.append(StaffTemplate.ALREADY_IN_BLACKLIST);
                    }else{
                        reviewReasonBuilder.append(StaffTemplate.ALREADY_ENTRY
                                .replaceAll(StaffTemplate.TMP_DEPARTMENT_NAME, entry.getDepartment().getDepartmentName())
                                .replaceAll(StaffTemplate.TMP_POSITION_NAME, entry.getPosition().getPositionName()));
                    }
                }
                // 设置审核状态（等待审核）、原因等信息
                entryInfo.setReviewReasons(reviewReasonBuilder.toString());
                entryInfo.setReviewStatus(ReviewStatusEnum.WAIT);
            }else{
                // 说明已经没有任何职位了，不必审核
                entryInfo.setReviewStatus(ReviewStatusEnum.PASS);
            }
            // 更新旧个人信息，以新的替换
            personalInfo.setId(personalCheck.getId());
            personalInfo = staffPersonalRepository.save(personalInfo);

        }
        // 设置新职位的个人信息
        entryInfo.setStaffPersonal(personalInfo);
        // 最后保存
        LogUtil.info("Last compute information is: {}", entryInfo);
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.save(entryInfo));
    }

    public JoyResult update(StaffEntryEntity entry) {
        // 校验身份证号
        StaffPersonalEntity reqPersonal = entry.getStaffPersonal();
        if(!IdNumberUtil.isIDNumber((reqPersonal.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        // 检验电话号码
        if(!PhoneUtil.isMobile(entry.getStaffPersonal().getPhone())){
            return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
        }
        // 是否存在
        StaffEntryEntity dbEntry = staffEntryRepository.findAllById(entry.getId());
        if(dbEntry == null){
            return JoyResult.buildFailedResult(Notice.STAFF_ENTRY_NOT_EXIST);
        }
        // 重复职位
        StaffEntryEntity checkStaff = staffEntryRepository.findAllByStaffPersonalAndDepartmentAndPositionAndIdNot(dbEntry.getStaffPersonal(),
                entry.getDepartment(),
                entry.getPosition(), entry.getId());
        if(checkStaff != null){
            return JoyResult.buildFailedResult(Notice.STAFF_ALREADY_IN_DEPARTMENT);
        }
        // 拷贝信息
        LogUtil.info("before copy : {}", dbEntry);
//        // 拷贝基本信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entry.getStaffPersonal(), dbEntry.getStaffPersonal());
        // 拷贝入职信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entry, dbEntry);;

        return JoyResult.buildSuccessResultWithData(staffEntryRepository.save(dbEntry));
    }

    public JoyResult delete(Long id) {
        StaffEntryEntity dbEntry = staffEntryRepository.findAllById(id);
        if(dbEntry == null){
            return JoyResult.buildFailedResult(Notice.STAFF_ENTRY_NOT_EXIST);
        }
        dbEntry.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.save(dbEntry));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(GetStaffEntryListReq req) {
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(GetStaffEntryListReq req) {
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffEntryEntity> getPredicates(GetStaffEntryListReq req){
        return (Specification<StaffEntryEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // username like
            if(!StringUtil.isEmpty(req.getUsername())){
                predicates.add(builder.like(root.get("staffPersonal").get("username"), "%" + req.getUsername() +"%"));
            }
            // phone like
            if(!StringUtil.isEmpty(req.getPhone())){
                predicates.add(builder.like(root.get("staffPersonal").get("phone"), "%" + req.getPhone() +"%"));
            }
            // idNumber like
            if(!StringUtil.isEmpty(req.getIdNumber())){
                predicates.add(builder.like(root.get("staffPersonal").get("idNumber"), "%" + req.getIdNumber() +"%"));
            }
            // nationality =
            if(!StringUtil.isEmpty(req.getNationality())){
                predicates.add(builder.equal(root.get("staffPersonal").get("nationality"), req.getNationality()));
            }
            // department =
            if(req.getDepartment() != null){
                predicates.add(builder.equal(root.get("department"), req.getDepartment()));
            }
            // education =
            if(req.getEducation() != null){
                predicates.add(builder.equal(root.get("staffPersonal").get("education"), req.getEducation()));
            }
            // all or STOP AND PASS?
            if(req.getReviewStatus() != null){
                predicates.add(builder.equal(root.get("reviewStatus"), req.getReviewStatus()));
            }
            // position =
            if(req.getPosition() != null){
                predicates.add(builder.equal(root.get("position"), req.getPosition()));
            }
            // birth between
            if(req.getBirthDateStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("staffPersonal").get("birthDate"), req.getBirthDateStart()));
            }
            if(req.getBirthDateEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("staffPersonal").get("birthDate"), req.getBirthDateEnd()));
            }
            // entry time
            if(req.getEntryTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("entryTime"), req.getEntryTimeStart()));
            }
            if(req.getEntryTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("entryTime"), req.getEntryTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
