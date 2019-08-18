package com.joy.xxfy.informationaldxn.staff.domain.enums;

public enum StaffStatusEnum {

    DIMISSION("离职"),

    INCUMBENCY("在职"),

    HAVE_HOLIDAY("休假"),

    BLACKLIST("黑名单");


    private String name;

    StaffStatusEnum(String name) {
        this.name = name;
    }

    StaffStatusEnum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
