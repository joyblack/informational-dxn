package com.joy.xxfy.informationaldxn.module.device.domain.enums;

/**
 * 维保类型
 */
public enum MaintainTypeEnum {
    MAINTAIN_DAILY("日常维保"),
    MAINTAIN_REGULAR("定期保养"),
    ABNORMAL_FAILURE("异常故障"),
    ARTIFICIAL_DAMAGE("人为损坏"),
    MAINTAIN_OTHER("其他");
    private String describes;

    MaintainTypeEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
