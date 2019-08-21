package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 *  钻孔类别
 */
public enum DrillCategoryEnum {
    THROUGH_LAYER("穿层钻孔"),
    BEDDING("穿层钻孔"),
    HIGH_POSITION("高位钻孔"),
    GEOLOGY("地质钻孔"),
    OTHER("其他钻孔"),
    ;
    private String describes;

    DrillCategoryEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
