package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.module.common.constant.ExcelImportConstant;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.*;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.vo.StaffExcelVo;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImportService {
    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    /**
     * 导入职工信息
     */
    public JoyResult importEntry(List<StaffExcelVo> vos, UserEntity loginUser) {
        DepartmentEntity company = loginUser.getCompany();
        LogUtil.info("Now import staff data coal mine platform name is: {}", company.getDepartmentName());
        List<String> errorMessages = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ExcelImportConstant.DATE_FORMAT);
        for (int i = 0; i < vos.size(); i++) {
            int line = i + 1;
            StaffExcelVo vo = vos.get(i);
            System.out.println(vo);
            StaffEntryEntity staffEntity = new StaffEntryEntity();
            if (StringUtil.isEmpty(vo.getIdNumber())) {
                errorMessages.add("第" + line + "行导入失败，职工身份证号为空");
                continue;
            }else if(!IdNumberUtil.isIDNumber(vo.getIdNumber())){
                errorMessages.add("第" + line + "行导入失败身份证号格式错误");
                continue;
            }
            /**
             * 获取个人信息是否存在,采取不同的处理策略
             * - 若存在，直接入入职信息，此时需要检测是否重复录入（相同部门、相同职位）；
             * - 若不存在，则先添加个人信息，再录入入职信息，此时无需检测重复。
             */
            StaffPersonalEntity personalEntity = staffPersonalRepository.findFirstByIdNumber(vo.getIdNumber());
            /**
             * 处理个人信息
             */
            if(personalEntity != null){
                staffEntity.setStaffPersonal(personalEntity);
            }else{
                StaffPersonalEntity personal = new StaffPersonalEntity();
                // 身份证号
                personal.setIdNumber(vo.getIdNumber());
                // 姓名
                if (StringUtil.isEmpty(vo.getName())) {
                    errorMessages.add("第" + line + "行导入失败，职工姓名为空");
                    continue;
                }
                personal.setUsername(vo.getName());
                // 性别: 非男即女
                if(vo.getSex().equals(SexEnum.MAN.getSex())){
                    personal.setSex(SexEnum.MAN);
                }else{
                    personal.setSex(SexEnum.WOM);
                }
                // 民族
                if (StringUtil.isEmpty(vo.getNationality())) {
                    errorMessages.add("第" + line + "行导入失败，民族不能为空");
                    continue;
                }
                personal.setNationality(vo.getNationality());
                // 出生年月：设置为身份证获取到的时间,而不管录入的时间
                try {
                    personal.setBirthDate(simpleDateFormat.parse(IdNumberUtil.getBirthday(vo.getIdNumber())));
                } catch (ParseException e) {
                    errorMessages.add("第" + line + "行出生日期解析错误，设置为当前时间");
                    personal.setBirthDate(new Date());
                }
                // 文化程度
                personal.setEducation(null);
                if (StringUtil.isNotEmpty(vo.getEducation())) {
                    EducationEnum education = null;
                    for (EducationEnum e : EducationEnum.values()) {
                        if (vo.getEducation().equals(e.getEducation())) {
                            education = e;
                            break;
                        }
                    }
                    personal.setEducation(education);
                }
                // 户口性质
                personal.setAccountCharacter(null);
                if (StringUtil.isNotEmpty(vo.getAccountCharacter())) {
                    AccountCharacterEnum accountCharacter = null;
                    for (AccountCharacterEnum a : AccountCharacterEnum.values()) {
                        if (vo.getAccountCharacter().equals(a.getAccountCharacter())) {
                            accountCharacter = a;
                            break;
                        }
                    }
                    personal.setAccountCharacter(accountCharacter);
                }
                // 联系电话
                if (StringUtil.isEmpty(vo.getPhone())) {
                    errorMessages.add("第" + line + "行导入失败，联系电话不能为空");
                    continue;
                }
                personal.setPhone(vo.getPhone());
                // 家庭住址
                personal.setHomeAddress(vo.getHomeAddress());
                // 参保状态： 是/否/已停止
                personal.setInsured(null);
                if (StringUtil.isNotEmpty(vo.getInsured())) {
                    InsuredEnum insured = null;
                    for (InsuredEnum e : InsuredEnum.values()) {
                        if (vo.getInsured().equals(e.getInsured())) {
                            insured = e;
                            break;
                        }
                    }
                    personal.setInsured(insured);
                }
                /**
                 * 直接添加，即便后续失败了，该信息的存在也是有价值的。
                 */
                staffEntity.setStaffPersonal(staffPersonalRepository.save(personal));
            }
            /**
             * 处理入职信息
             */

            // 队组/部门(以及所属平台/公司)：目前假设部门都位于顶级公司层级之下，之后这里可能要一级一级的查询。
            DepartmentEntity departmentEntity = departmentRepository.findFirstByParentIdAndDepartmentName(company.getId(),vo.getDepartment());
            if (null == departmentEntity) {
                errorMessages.add("第" + line + "行导入失败，职工部门/队组信息不存在");
                continue;
            }
            staffEntity.setCompany(company);
            staffEntity.setDepartment(departmentEntity);

            // 职务/工种
            PositionEntity positionEntity = positionRepository.findFirstByPositionName(vo.getPosition());
            if (null == positionEntity) {
                errorMessages.add("第" + line + "行导入失败，职工职务/工种信息不存在");
                continue;
            }
            staffEntity.setPosition(positionEntity);
            // 检测该用户是否同部门同职位出现，这是不合法的
            StaffEntryEntity checkStaff = staffEntryRepository.findFirstByStaffPersonalAndDepartmentAndPosition(personalEntity,
                    staffEntity.getDepartment(),
                    staffEntity.getPosition());
            if(checkStaff != null){
                errorMessages.add("第" + line + "行导入失败，员工在该部门下已拥有相同职位");
                continue;

            }
            // 入职时间: 默认设置为当前时间
            staffEntity.setEntryTime(DateUtil.getDateJustYMD(new Date()));
            if (StringUtil.isNotEmpty(vo.getEntryTime())) {
                try {
                    staffEntity.setEntryTime(simpleDateFormat.parse(vo.getEntryTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // 备注
            staffEntity.setRemarks(vo.getRemarks());
            // 体检时间
            staffEntity.setPhysicalExaminationTime(null);
            if (StringUtil.isNotEmpty(vo.getPhysicalExaminationTime())) {
                try {
                    staffEntity.setPhysicalExaminationTime(simpleDateFormat.parse(vo.getPhysicalExaminationTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            // 体检医院
            staffEntity.setPhysicalExaminationHospital(vo.getPhysicalExaminationHospital());
            // 审核状态设置为通过
            staffEntity.setReviewStatus(ReviewStatusEnum.PASS);
            staffEntryRepository.save(staffEntity);
        }
        return JoyResult.buildSuccessResultWithData(errorMessages);
    }
}
