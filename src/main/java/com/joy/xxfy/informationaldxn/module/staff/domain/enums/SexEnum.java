package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

/**
 * 性别
 */
public enum SexEnum {

    MAN("MAN","男"),

    WOM("WOM","女");

    private String name;

    private String describe;

    SexEnum(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }}
