package com.joy.xxfy.informationaldxn.module.common.enums;

/**
 *  钻孔类型
 */
public enum DailyShiftEnum {
    MORNING("早班"),
    NOON("中班"),
    EVENING("晚班")
    ;
    private String describes;

    DailyShiftEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
