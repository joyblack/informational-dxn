package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.module.common.constant.ExcelImportConstant;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.vo.StaffExcelVo;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {
    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    /**
     * 导入职工信息
     */
    public JoyResult importData(List<StaffExcelVo> vos) {
//        List<String> errorMessages = new ArrayList<String>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ExcelImportConstant.DATE_FORMAT);
//        for (int i = 0; i < vos.size(); i++) {
//            int line = i + 1;
//            StaffExcelVo vo = vos.get(i);
//            StaffEntryEntity staffEntity = new StaffEntryEntity();
//            if (StringUtil.isEmpty(vo.getIdNumber())) {
//                errorMessages.add("第" + line + "行导入失败，职工身份证号为空");
//                continue;
//            }
//            // 获取个人信息是否存在
//            StaffPersonalEntity personalEntity = staffPersonalRepository.findAllByIdNumber(vo.getIdNumber());
//
//            // 若存在，则无需检测其他信息，否则需要检测其他信息
//            if()
//
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
//            DepartmentEntity departmentEntity = departmentRepository.findByNameAndDeleteIsFalse(StaffExcelVo.getDepartment());
//            if (null == departmentEntity) {
//                msg.add("第" + line + "行导入失败，职工部门/队组信息不存在");
//                continue;
//            }
//            staffEntity.setDepartment(departmentEntity);
//            if (StringUtil.isEmpty(StaffExcelVo.getPosition())) {
//                msg.add("第" + line + "行导入失败，职工职务/工种为空");
//                continue;
//            }
//            PositionEntity positionEntity = positionRepository.findByNameAndDeleteIsFalse(StaffExcelVo.getPosition());
//            if (null == positionEntity) {
//                msg.add("第" + line + "行导入失败，职工职务/工种信息不存在");
//                continue;
//            }
//            staffEntity.setPosition(positionEntity);
//            if (StringUtil.isNotEmpty(StaffExcelVo.getSex())) {
//                SexEnum sexEnum = null;
//                for (SexEnum sexEnum1 : SexEnum.values()) {
//                    if (StaffExcelVo.getSex().equals(sexEnum1.getSex())) {
//                        sexEnum = sexEnum1;
//                        continue;
//                    }
//                }
//                staffEntity.setSex(sexEnum);
//            }
//            if (StringUtil.isNotEmpty(StaffExcelVo.getBirthDate())) {
//                try {
//                    staffEntity.setBirthDate(simpleDateFormat.parse(StaffExcelVo.getBirthDate()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (StringUtil.isNotEmpty(StaffExcelVo.getEducation())) {
//                EducationEnum educationEnum = null;
//                for (EducationEnum educationEnum1 : EducationEnum.values()) {
//                    if (StaffExcelVo.getEducation().equals(educationEnum1.getEducation())) {
//                        educationEnum = educationEnum1;
//                        continue;
//                    }
//                }
//                staffEntity.setEducation(educationEnum);
//            }
//            if (StringUtil.isNotEmpty(StaffExcelVo.getAccountCharacter())) {
//                AccountCharacterEnum accountCharacterEnum = null;
//                for (AccountCharacterEnum accountCharacterEnum1 : AccountCharacterEnum.values()) {
//                    if (StaffExcelVo.getAccountCharacter().equals(accountCharacterEnum1.getAccountCharacter())) {
//                        accountCharacterEnum = accountCharacterEnum1;
//                        continue;
//                    }
//                }
//                staffEntity.setAccountCharacter(accountCharacterEnum);
//            }
//            if (StringUtil.isNotEmpty(StaffExcelVo.getEntryTime())) {
//                try {
//                    staffEntity.setEntryTime(simpleDateFormat.parse(StaffExcelVo.getEntryTime()));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
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
//        }
//        if (msg.size() > 0) {
//            return ResultUtil.error(msg);
//        } else {
//            return ResultUtil.success();
//        }

        return JoyResult.buildSuccessResultWithData(null);
    }
}
