package com.joy.xxfy.informationaldxn.staff.domain.enums;

public enum ReviewStatusEnum {

    WAIT("待审核"),

    PASS("通过"),

    NOT_PASS("未通过");

    private String name;

    ReviewStatusEnum(String name) {
        this.name = name;
    }

    ReviewStatusEnum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
