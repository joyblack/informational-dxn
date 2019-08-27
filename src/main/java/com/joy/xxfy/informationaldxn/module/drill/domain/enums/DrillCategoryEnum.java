package com.joy.xxfy.informationaldxn.module.drill.domain.enums;

/**
 *  钻孔类别
 */
public enum DrillCategoryEnum {
    THROUGH_LAYER_CATEGORY("穿层钻孔"),
    BEDDING_CATEGORY("穿层钻孔"),
    HIGH_POSITION_CATEGORY("高位钻孔"),
    GEOLOGY_CATEGORY("地质钻孔"),
    OTHER_CATEGORY("其他钻孔"),
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
