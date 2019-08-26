package com.joy.xxfy.informationaldxn.module.safe.domain.enums;

public enum ThreeViolationTypeEnum {
    GROUND("地面"),
    MACHINE("机电"),
    ONE_THROUGH_AND_THREE_PREVENTION("一通三防"),
    ROOF("顶板"),
    WATER_PREVENTION("防治水"),
    OTHER("其他");
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
