package com.joy.xxfy.informationaldxn.module.safe.domain.enums;

public enum ThreeViolationTypeEnum {
    TASK("违章作业"),
    COMMAND("违章指挥"),
    LABOR_DISCIPLINE("违反劳动纪律");
    private String describes;

    ThreeViolationTypeEnum() {
    }

    ThreeViolationTypeEnum(String describes) {
        this.describes = describes;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
