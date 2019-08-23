package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

public enum LeaveTypeEnum {

    NORMAL("正常离职"),

    UN_AUTHORIZED("擅自离职"),

    ILLNESS("因病离职");

    private String name;

    LeaveTypeEnum(String name) {
        this.name = name;
    }

    LeaveTypeEnum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
