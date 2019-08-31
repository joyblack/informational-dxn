package com.joy.xxfy.informationaldxn.module.staff.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationaldxn.module.common.enums.LimitUserTypeEnum;
import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.PositionEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ExportConstant;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.PhoneUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.excel.ExportUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.*;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.*;
import com.joy.xxfy.informationaldxn.module.staff.domain.template.StaffTemplate;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryAddReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryGetListReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class StaffEntryService extends BaseService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private StaffPersonalIdentityPhotoRepository staffPersonalIdentityPhotoRepository;

    @Autowired
    private StaffPersonalOneInchPhotoRepository staffPersonalOneInchPhotoRepository;


    @Autowired
    private StaffBlacklistRepository staffBlacklistRepository;

    /**
     * 添加员工入职信息
     */
    public JoyResult add(StaffEntryAddReq req) {
        // 创建员工基本信息对象
        StaffPersonalEntity personalInfo = new StaffPersonalEntity();
        // 员工入职信息对象
        StaffEntryEntity entryInfo = new StaffEntryEntity();
        // 联系电话
        if(!PhoneUtil.isMobile(req.getPhone())){
            return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
        }else{
            personalInfo.setPhone(req.getPhone());
        }
        // 身份证号
        if(!IdNumberUtil.isIDNumber((req.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }else{
            personalInfo.setIdNumber(req.getIdNumber());
        }
        // 姓名
        personalInfo.setUsername(req.getUsername());
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
        // 专业
        personalInfo.setProfession(req.getProfession());

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
            StaffPersonalIdentityPhotoEntity file = staffPersonalIdentityPhotoRepository.findAllById(req.getIdNumberPhotoId());
            if(file == null){
                return JoyResult.buildFailedResult(Notice.ID_NUMBER_PHOTO_IS_ILLEGAL);
            }else{
                personalInfo.setIdNumberPhoto(file);
            }
        }
        // 一寸相
        if(req.getOneInchPhotoId() != null){
            StaffPersonalOneInchPhotoEntity file = staffPersonalOneInchPhotoRepository.findAllById(req.getOneInchPhotoId());
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


        // 查看用户是否在系统中
        StaffPersonalEntity personalCheck= staffPersonalRepository.findAllByIdNumber(personalInfo.getIdNumber());
        if(personalCheck == null){
            LogUtil.info("个人信息第一次录入.");
            // 新增个人信息
            personalInfo = staffPersonalRepository.save(personalInfo);
            // 审核状态为通过，可能以后需要查找黑名单
            entryInfo.setReviewStatus(ReviewStatusEnum.PASS);
        }else{
            LogUtil.info("System already have this personal information: {}", personalCheck);
            // 检测该用户是否同部门同职位出现，这是不合法的
            StaffEntryEntity checkStaff = staffEntryRepository.findAllByStaffPersonalAndDepartmentAndPosition(personalCheck,
                    entryInfo.getDepartment(),
                    entryInfo.getPosition());
            if(checkStaff != null){
                return JoyResult.buildFailedResult(Notice.STAFF_ALREADY_IN_DEPARTMENT);
            }
            // 记录审核的原因

            // 黑名单校验
            StaffBlacklistEntity blacklist = staffBlacklistRepository.findFirstByStaffPersonal(personalCheck);
            if(blacklist != null){
                LogUtil.info("This is a blacklist staff: {}", blacklist);
                // 设置审核状态（等待审核）、原因等信息
                entryInfo.setReviewReasons(StaffTemplate.ALREADY_IN_BLACKLIST);
                entryInfo.setReviewStatus(ReviewStatusEnum.WAIT);
            }else{
                StringBuilder reviewReasonBuilder = new StringBuilder();
                // 检测该员工是否有重复的职位出现
                List<StaffEntryEntity> entryList = staffEntryRepository.findAllByStaffPersonal(personalCheck);
                if(entryList.size() > 0){
                    // 重复部门校验
                    for (StaffEntryEntity entry : entryList) {
                        reviewReasonBuilder.append(StaffTemplate.ALREADY_ENTRY
                                .replaceAll(StaffTemplate.TMP_DEPARTMENT_NAME, entry.getDepartment().getDepartmentName())
                                .replaceAll(StaffTemplate.TMP_POSITION_NAME, entry.getPosition().getPositionName()));
                    }
                    // 设置审核状态（等待审核）、原因等信息
                    entryInfo.setReviewReasons(reviewReasonBuilder.toString());
                    entryInfo.setReviewStatus(ReviewStatusEnum.WAIT);

                }else{
                    // 说明已经没有任何职位了，不必审核
                    entryInfo.setReviewStatus(ReviewStatusEnum.PASS);
                }
            }
            // 更新旧个人信息，以新的替换
            personalInfo.setId(personalCheck.getId());
            personalInfo = staffPersonalRepository.save(personalInfo);
        }
        // 设置个人信息
        entryInfo.setStaffPersonal(personalInfo);
        // 最后保存
        LogUtil.info("Last compute information is: {}", entryInfo);
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.save(entryInfo));
    }

    public JoyResult update(StaffEntryUpdateReq req) {
        // 获取原来的信息
        StaffEntryEntity entryInfo = staffEntryRepository.findAllById(req.getId());
        if(entryInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_ENTRY_NOT_EXIST);
        }

        // 姓名
        entryInfo.getStaffPersonal().setUsername(req.getUsername());
        // 身份证号:暂时不允许修改,若允许修改，要验证唯一性。
        // 出生日期
        entryInfo.getStaffPersonal().setBirthDate(req.getBirthDate());
        // 性别
        entryInfo.getStaffPersonal().setSex(req.getSex());
        // 民族
        entryInfo.getStaffPersonal().setNationality(req.getNationality());
        // 家庭住址
        entryInfo.getStaffPersonal().setHomeAddress(req.getHomeAddress());
        // 户口性质
        entryInfo.getStaffPersonal().setAccountCharacter(req.getAccountCharacter());
        // 文化程度
        entryInfo.getStaffPersonal().setEducation(req.getEducation());
        // 专业
        entryInfo.getStaffPersonal().setProfession(req.getProfession());
        // 联系电话
        if(!PhoneUtil.isMobile(req.getPhone())){
            return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
        }else{
            entryInfo.getStaffPersonal().setPhone(req.getPhone());
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
        entryInfo.getStaffPersonal().setInsured(req.getInsured());
        // 体检时间
        entryInfo.setPhysicalExaminationTime(req.getPhysicalExaminationTime());
        // 体检医院
        entryInfo.setPhysicalExaminationHospital(req.getPhysicalExaminationHospital());
        // 身份证照片
        if(req.getIdNumberPhotoId() != null){
            StaffPersonalIdentityPhotoEntity file = staffPersonalIdentityPhotoRepository.findAllById(req.getIdNumberPhotoId());
            if(file == null){
                return JoyResult.buildFailedResult(Notice.ID_NUMBER_PHOTO_IS_ILLEGAL);
            }else{
                entryInfo.getStaffPersonal().setIdNumberPhoto(file);
            }
        }
        // 一寸相
        if(req.getOneInchPhotoId() != null){
            StaffPersonalOneInchPhotoEntity file = staffPersonalOneInchPhotoRepository.findAllById(req.getOneInchPhotoId());
            if(file == null){
                return JoyResult.buildFailedResult(Notice.ONE_INCH_PHOTO_IS_ILLEGAL);
            }else{
                entryInfo.getStaffPersonal().setOneInchPhoto(file);
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
        // 最后保存
        LogUtil.info("Last compute information is: {}", entryInfo);
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.save(entryInfo));
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
     * 获取数据（通过）
     */
    public JoyResult getByIdNumber(String idNumber) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAllByIdNumber(idNumber));
    }

    /**
     * 获取审核通过的数据
     */
    public JoyResult getPassListByIdNumber(String idNumber) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.getByIdNumberAndReviewStatus(idNumber, ReviewStatusEnum.PASS));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(StaffEntryGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAll(getPredicates(req, loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(StaffEntryGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAll(getPredicates(req, loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffEntryEntity> getPredicates(StaffEntryGetListReq req,UserEntity loginUser){
        return (Specification<StaffEntryEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(req.getCompanyId() != null){
                predicates.add(builder.equal(root.get("company").get("id"), req.getCompanyId()));
            }else{
                // 集团，所有；非集团，只返回本煤矿
                predicates.add(builder.equal(root.get("company"), loginUser.getCompany()));
            }
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
            if(req.getDepartmentId() != null){
                predicates.add(builder.equal(root.get("department").get("id"), req.getDepartmentId()));
            }
            // education =
            if(req.getEducation() != null){
                predicates.add(builder.equal(root.get("staffPersonal").get("education"), req.getEducation()));
            }
            // all or STOP AND PASS?
            if(req.getReviewStatus() != null){
                predicates.add(builder.equal(root.get("reviewStatus"), req.getReviewStatus()));
            }
            // sex =
            if(req.getSex() != null){
                predicates.add(builder.equal(root.get("staffPersonal").get("sex"), req.getSex()));
            }
            // position =
            if(req.getPositionId() != null){
                predicates.add(builder.equal(root.get("position").get("id"), req.getPositionId()));
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

    public void exportData(StaffEntryGetListReq req, UserEntity loginUser, HttpServletRequest request, HttpServletResponse response) {
        // 权限验证
        if(!hasPermission(loginUser, 0L, LimitUserTypeEnum.ALL)){
            throw new JoyException(Notice.PERMISSION_FORBIDDEN);
        }
        // 获取查询结果
        List<StaffEntryEntity> staffEntryEntities = (List<StaffEntryEntity>)getAllList(req, loginUser).getData();

        // 日期导出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(ExportConstant.DATE_FORMAT);
        List<List<String>> rows = new ArrayList<List<String>>();
        rows.add(CollUtil.newArrayList(ExportConstant.FIELD_STAFF_ENTRY));

        for (StaffEntryEntity entryInfo : staffEntryEntities) {
            List<String> row = CollUtil.newArrayList(
                    // 姓名
                    entryInfo.getStaffPersonal().getUsername(),
                    // 出生日期
                    dateFormat.format(entryInfo.getStaffPersonal().getBirthDate()),
                    // 性别
                    entryInfo.getStaffPersonal().getSex().getDescribe(),
                    // 民族
                    entryInfo.getStaffPersonal().getNationality(),
                    // 入职时间
                    dateFormat.format(entryInfo.getEntryTime()),
                    // 入职公司/煤矿
                    entryInfo.getCompany().getDepartmentName(),
                    // 入职部门
                    entryInfo.getDepartment().getDepartmentName(),
                    // 入职职务/工种
                    entryInfo.getPosition().getPositionName(),
                    // 联系方式
                    entryInfo.getStaffPersonal().getPhone(),
                    // 审核状态
                    entryInfo.getReviewStatus().getDescribes(),
                    // 备注
                    entryInfo.getRemarks()
            );
            rows.add(row);
        }
        // rows.add(CollUtil.newArrayList("合计", "", "", "",regularAttendanceEntity.getTotal().toString(),"" ));
        ExcelWriter writer = ExcelUtil.getWriter();
        // 合并列
        //writer.merge(ExportConstant.FIELD_STAFF_ENTRY.length - 1, regularAttendanceEntity.getDepartment().getName() + simpleDateFormat.format(regularAttendanceEntity.getDateTime()) + "考勤记录");
        writer.write(rows, true);
        String fileName = "员工入职信息表";
        ExportUtil.exportData(request, response, fileName, writer);
    }
}
