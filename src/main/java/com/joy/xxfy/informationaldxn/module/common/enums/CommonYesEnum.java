package com.joy.xxfy.informationaldxn.module.common.enums;

public enum CommonYesEnum {
    YES("是"),NO("否");
    private String describe;

    CommonYesEnum(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
