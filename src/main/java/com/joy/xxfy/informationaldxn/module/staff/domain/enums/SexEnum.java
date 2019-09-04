package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

/**
 * 性别
 */
public enum SexEnum {

    MAN("男"),

    WOM("女");


    private String sex;

    SexEnum(String sex) {
        this.sex = sex;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }}
