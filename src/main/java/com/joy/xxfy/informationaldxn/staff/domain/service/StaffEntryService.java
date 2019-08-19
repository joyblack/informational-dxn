package com.joy.xxfy.informationaldxn.staff.domain.service;

import com.joy.xxfy.informationaldxn.department.domain.repository.DepartmentRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 添加员工入职信息
     */
    public JoyResult add(StaffEntryEntity reqEntry) {
        // 校验身份证号
        StaffPersonalEntity reqPersonal = reqEntry.getStaffPersonal();
        if(!IdNumberUtil.isIDNumber((reqPersonal.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        // 检验电话号码
        if(!PhoneUtil.isMobile(reqEntry.getStaffPersonal().getPhone())){
            return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
        }
        StaffPersonalEntity personalEntity = staffPersonalRepository.findAllByIdNumber(reqPersonal.getIdNumber());
        if(personalEntity == null){
            LogUtil.info("This staff never appeared in system.");
            // 新增Personal并设置entry实体
            reqEntry.setStaffPersonal(staffPersonalRepository.save(reqEntry.getStaffPersonal()));
            reqEntry.setReviewStatus(ReviewStatusEnum.PASS);
        }else{
            LogUtil.info("This staff has personal information in system: {}", personalEntity);
            // 接下来检测该用户是否同部门同职位出现，这是不合法的。
            StaffEntryEntity checkStaff = staffEntryRepository.findAllByStaffPersonalAndDepartmentAndPosition(personalEntity,
                    reqEntry.getDepartment(),
                    reqEntry.getPosition());
            if(checkStaff != null){
                return JoyResult.buildFailedResult(Notice.STAFF_ALREADY_IN_DEPARTMENT);
            }
            List<StaffEntryEntity> entryList = staffEntryRepository.findAllByStaffPersonal(personalEntity);
            if(entryList.size() > 0){
                LogUtil.info("This is a special staff, need change review status from `PASS`to `WAIT`.");
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
                reqEntry.setReviewReasons(reviewReasonBuilder.toString());
                reqEntry.setReviewStatus(ReviewStatusEnum.WAIT);
            }else{
                // 说明已经没有任何职位了，不必审核
                reqEntry.setReviewStatus(ReviewStatusEnum.PASS);
            }
            // 更新个人信息,暂时不允许修改个人信息，后期再此处进行修改。你要考虑审核问题
            reqEntry.setStaffPersonal(personalEntity);
        }
        // 最后保存
        LogUtil.info("Last compute information is: {}", reqEntry);
        StaffEntryEntity save = staffEntryRepository.save(reqEntry);
        return JoyResult.buildSuccessResultWithData(save);
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
        staffEntryRepository.deleteById(id);
        return JoyResult.buildSuccessResult("删除成功");
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
