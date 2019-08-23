package com.joy.xxfy.informationaldxn.module.staff.domain.enums;

/**
 * @author 13562
 */

public enum InsuredEnum {
    /**
     * 是
     */
    YES("是"),
    /**
     * 否
     */
    NO("否"),
    /**
     * 已停止
     */
    STOP("已停止");

    private String insured;

    InsuredEnum() {
    }

    InsuredEnum(String insured) {
        this.insured = insured;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }
}
