package com.joy.xxfy.informationaldxn.module.document.domain.enums;

public enum ReturnStatusEnum {
    RETURN_STATUS_YES("已归还"),
    RETURN_STATUS_NO("未归还");

    private String describes;

    ReturnStatusEnum() {
    }

    ReturnStatusEnum(String describes) {
        this.describes = describes;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
