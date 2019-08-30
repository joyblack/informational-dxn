package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

public enum ReviewStatusEnum {

    WAIT("待审核"),

    PASS("通过"),

    NOT_PASS("未通过");

    private String describes;

    ReviewStatusEnum(String describes) {
        this.describes = describes;
    }

    ReviewStatusEnum() {
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
