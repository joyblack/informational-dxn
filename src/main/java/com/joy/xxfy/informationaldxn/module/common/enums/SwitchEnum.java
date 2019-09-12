package com.joy.xxfy.informationaldxn.module.common.enums;

/**
 *  钻孔类型
 */
public enum SwitchEnum {
    ON("开"),
    OFF("关")
    ;
    private String switches;

    SwitchEnum(String switches) {
        this.switches = switches;
    }

    public String getSwitches() {
        return switches;
    }

    public void setSwitches(String switches) {
        this.switches = switches;
    }
}
