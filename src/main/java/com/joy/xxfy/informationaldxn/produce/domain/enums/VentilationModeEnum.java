package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 * 通风方式
 */
public enum VentilationModeEnum {
    U("U型通风"),
    Y("Y型通风"),
    Z("Z型通风");

    private String describes;

    VentilationModeEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
