package com.joy.xxfy.informationaldxn.module.document.domain.enums;

public enum LicenceTypeEnum {
    LICENCE_SAFE_PRODUCE("安全生产许可证"),
    LICENCE_MINING("采矿许可证");

    private String describes;

    LicenceTypeEnum() {
    }

    LicenceTypeEnum(String describes) {
        this.describes = describes;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
