package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

public enum PersonalStatusEnum {

    NEVER("不存在系统之中"),

    INCUMBENCY("在职"),

    REVIEW_WAIT("未审核"),

    REVIEW_NOT_PASS("审核未通过"),

    LEAVE("离职"),

    BLACKLIST("黑名单");


    private String name;

    PersonalStatusEnum(String name) {
        this.name = name;
    }

    PersonalStatusEnum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
