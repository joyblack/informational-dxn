package com.joy.xxfy.informationaldxn.module.common.enums;

public enum FillDailyStatusEnum {
    FILL_YES("已填写"),
    FILL_NO("未填写");

    private String status;

    FillDailyStatusEnum() {
    }

    FillDailyStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
