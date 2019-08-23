package com.joy.xxfy.informationaldxn.module.driving.domain.enums;

/**
 * 岩性
 */
public enum RockCharacterEnum {
    ALL_COAL("全煤"),
    ALL_ROCK("全岩"),
    /* 当岩层占掘进工作面面积1/5～4/5时，即称为半煤岩巷道 */
    HALF_COAL("半煤巷");

    private String describes;

    RockCharacterEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
