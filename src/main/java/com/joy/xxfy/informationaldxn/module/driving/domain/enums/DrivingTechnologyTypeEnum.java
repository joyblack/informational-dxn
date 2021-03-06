package com.joy.xxfy.informationaldxn.module.driving.domain.enums;

/**
 * 掘进工艺
 */
public enum DrivingTechnologyTypeEnum {
    BLASTING_DRIVING("炮掘"),
    FULLY("综掘");

    private String describes;

    DrivingTechnologyTypeEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
