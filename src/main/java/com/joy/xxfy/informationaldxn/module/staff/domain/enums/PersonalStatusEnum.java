package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

public enum PersonalStatusEnum {

    NEVER("不存在系统之中"),

    LEAVE("离职"),

    INCUMBENCY("在职"),

    HAVE_HOLIDAY("休假"),

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
