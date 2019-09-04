package com.joy.xxfy.informationaldxn.module.staff.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用于导入员工的excel映射的实体
 */
@Data
@ToString
public class StaffExcelVo implements Serializable {

    private static final long serialVersionUID = -1158991801967721164L;
    /**
     * 存档号
     */
    private String archive;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 民族
     */
    private String nationality;
    /**
     * 出生年月
     */
    private String birthDate;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 文化程度
     */
    private String education;
    /**
     * 户口性质
     */
    private String accountCharacter;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 家庭住址
     */
    private String homeAddress;
    /**
     * 部门/队组
     */
    private String department;
    /**
     * 职务/工种
     */
    private String position;
    /**
     * 入职时间
     */
    private String entryTime;
    /**
     * 是否参保
     */
    private String insured;
    /**
     * 参保时间
     */
    private String insuredTime;
    /**
     * 体检医院
     */
    private String physicalExaminationHospital;
    /**
     * 体检时间
     */
    private String physicalExaminationTime;
    /**
     * 备注
     */
    private String remarks;

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAccountCharacter() {
        return accountCharacter;
    }

    public void setAccountCharacter(String accountCharacter) {
        this.accountCharacter = accountCharacter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public String getInsuredTime() {
        return insuredTime;
    }

    public void setInsuredTime(String insuredTime) {
        this.insuredTime = insuredTime;
    }

    public String getPhysicalExaminationHospital() {
        return physicalExaminationHospital;
    }

    public void setPhysicalExaminationHospital(String physicalExaminationHospital) {
        this.physicalExaminationHospital = physicalExaminationHospital;
    }

    public String getPhysicalExaminationTime() {
        return physicalExaminationTime;
    }

    public void setPhysicalExaminationTime(String physicalExaminationTime) {
        this.physicalExaminationTime = physicalExaminationTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
