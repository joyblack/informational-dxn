package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 *  回采方式
 */
public enum BackMiningModeEnum {
    BLASTING("炮采"),
    HIGH_GRADE("高档普采"),
    INTEGRATION_MACHINE("综合化机械");
    private String describes;

    BackMiningModeEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
