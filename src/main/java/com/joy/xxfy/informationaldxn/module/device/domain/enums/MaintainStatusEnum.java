package com.joy.xxfy.informationaldxn.module.device.domain.enums;

/**
 * 维保情况
 */
public enum MaintainStatusEnum {
    COMPLETE("完成"),
    NOT_COMPLETE("未完成");
    private String describes;

    MaintainStatusEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
