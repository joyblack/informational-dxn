package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

/**
 * @author 13562
 */

public enum EducationEnum {
    /**
     * 博士
     */
    DOCTOR("博士"),
    /**
     * 硕士
     */
    MASTER("硕士"),
    /**
     * 本科
     */
    BACHELOR("本科"),
    /**
     * 大专
     */
    COLLEGE("大专"),
    /**
     * 中专
     */
    SECONDARY("中专"),
    /**
     * 中技
     */
    POLYTECHNIC("中技"),
    /**
     * 高中
     */
    SENIOR_MIDDLE_SCHOOL("高中"),
    /**
     * 初中
     */
    JUNIOR_MIDDLE_SCHOOL("初中"),
    /**
     * 小学
     */
    PRIMARY_SCHOOL("小学"),
    /**
     * 文盲
     */
    ILLITERACY("文盲");
    private String education;

    EducationEnum() {
    }

    EducationEnum(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
