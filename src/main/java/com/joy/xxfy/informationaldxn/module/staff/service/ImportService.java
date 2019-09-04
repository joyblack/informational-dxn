package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.module.common.constant.ExcelImportConstant;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.PositionEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.AccountCharacterEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.EducationEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.SexEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.PositionRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.vo.StaffExcelVo;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
    public JoyResult importData(List<StaffExcelVo> vos, UserEntity loginUser) {
        DepartmentEntity company = loginUser.getCompany();

        LogUtil.info("******************: Now import staff data coal mine platform name is: {}", company.getDepartmentName());

        List<String> errorMessages = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ExcelImportConstant.DATE_FORMAT);
        for (int i = 0; i < vos.size(); i++) {
            int line = i + 1;
            StaffExcelVo vo = vos.get(i);
            StaffEntryEntity staffEntity = new StaffEntryEntity();
            if (StringUtil.isEmpty(vo.getIdNumber())) {
                errorMessages.add("第" + line + "行导入失败，职工身份证号为空");
                continue;
            }
            // 获取个人信息是否存在
            StaffPersonalEntity personalEntity = staffPersonalRepository.findAllByIdNumber(vo.getIdNumber());

            // 若存在，则无需检测其他信息，否则需要检测其他信息
            StaffEntryEntity staff = new StaffEntryEntity();
            StaffPersonalEntity personal = staff.getStaffPersonal();
            // 姓名
            personal.setUsername(vo.getName());
            // 性别:非男即女
            if(vo.getSex().equals(SexEnum.MAN.getSex())){
                personal.setSex(SexEnum.MAN);
            }else{
                personal.setSex(SexEnum.WOM);
            }
            // 民族
            personal.setNationality(vo.getNationality());
            // 出生年月：默认设置为当前时间
            personal.setBirthDate(DateUtil.getDateJustYMD(new Date()));
            if (StringUtil.isNotEmpty(vo.getBirthDate())) {
                try {
                    personal.setBirthDate(simpleDateFormat.parse(vo.getBirthDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            // 身份证号
            personal.setIdNumber(vo.getIdNumber());
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
            personal.setPhone(vo.getPhone());
            // 家庭住址
            personal.setHomeAddress(vo.getHomeAddress());
            // 队组/部门(以及所属平台/公司)：目前假设部门都位于顶级公司层级之下，之后这里可能要一级一级的查询。
            DepartmentEntity departmentEntity = departmentRepository.findAllByParentIdAndDepartmentName(company.getId(),vo.getDepartment());
            if (null == departmentEntity) {
                errorMessages.add("第" + line + "行导入失败，职工部门/队组信息不存在");
                continue;
            }
            // 所属公司
            staffEntity.setCompany(company);
            staffEntity.setDepartment(departmentEntity);
            // 职务/工种
            PositionEntity positionEntity = positionRepository.findFirstByPositionName(vo.getPosition());
            if (null == positionEntity) {
                errorMessages.add("第" + line + "行导入失败，职工职务/工种信息不存在");
                continue;
            }
            staffEntity.setPosition(positionEntity);
            // 入职时间
            if (StringUtil.isNotEmpty(vo.getEntryTime())) {
                try {
                    staffEntity.setEntryTime(simpleDateFormat.parse(vo.getEntryTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
//            vo.getEntryTime();
//            // 参保状态
//            vo.getInsured();
//            // 备注
//            vo.getRemarks();
//            // 体检时间
//            vo.getPhysicalExaminationTime();
//            // 体检医院
//            vo.getPhysicalExaminationHospital();
//
//            StaffEntity staffEntity1 = staffRepository.findByIdNumberAndDeleteIsFalse(StaffExcelVo.getIdNumber());
//            if (staffEntity1 != null) {
//                staffEntity = staffEntity1;
//            } else {
//                staffEntity.setIdNumber(StaffExcelVo.getIdNumber());
//            }
//            if (StringUtil.isEmpty(StaffExcelVo.getName())) {
//                msg.add("第" + line + "行导入失败，职工姓名为空");
//                continue;
//            }
//            staffEntity.setName(StaffExcelVo.getName());
//            if (StringUtil.isEmpty(StaffExcelVo.getDepartment())) {
//                msg.add("第" + line + "行导入失败，职工部门/队组为空");
//                continue;
//            }
//
//            staffEntity.setDepartment(departmentEntity);
//
//
//
//
//            if (StringUtil.isNotEmpty(StaffExcelVo.getInsured())) {
//                InsuredEnum insuredEnum = null;
//                for (InsuredEnum insuredEnum1 : InsuredEnum.values()) {
//                    if (StaffExcelVo.getInsured().equals(insuredEnum1.getInsured())) {
//                        insuredEnum = insuredEnum1;
//                        continue;
//                    }
//
//                }
//                staffEntity.setInsured(insuredEnum);
//            }
//            if (StringUtil.isNotEmpty(StaffExcelVo.getInsuredTime())) {
//                try {
//                    staffEntity.setInsuredTime(simpleDateFormat.parse(StaffExcelVo.getInsuredTime()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (StringUtil.isNotEmpty(StaffExcelVo.getPhysicalExaminationTime())) {
//                try {
//                    staffEntity.setPhysicalExaminationTime(simpleDateFormat.parse(StaffExcelVo.getPhysicalExaminationTime()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            BeanUtils.copyProperties(StaffExcelVo, staffEntity,
//                    new String[]{"idNumber", "name", "department", "position", "sex", "birthDate", "education", "accountCharacter", "entryTime", "insured", "insuredTime", "physicalExaminationTime"});
//            staffRepository.save(staffEntity);
        }
        return JoyResult.buildSuccessResultWithData(errorMessages);
    }
}
