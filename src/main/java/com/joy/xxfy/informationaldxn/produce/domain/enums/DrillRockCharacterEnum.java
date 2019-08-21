package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 *  钻孔岩性
 */
public enum DrillRockCharacterEnum {
    COAL_LAYER("煤层"),
    ROCK_LAYER("岩层"),
    ;
    private String describes;

    DrillRockCharacterEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
