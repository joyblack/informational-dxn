package com.joy.xxfy.informationaldxn.module.safe.domain.enums;

public enum RectificationStatusEnum {
    RECTIFICATION_YES("已整改"),RECTIFICATION_NO("未整改");
    private String describe;

    RectificationStatusEnum(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
