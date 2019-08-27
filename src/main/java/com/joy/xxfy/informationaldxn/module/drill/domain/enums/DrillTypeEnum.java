package com.joy.xxfy.informationaldxn.module.drill.domain.enums;

/**
 *  钻孔类型
 */
public enum DrillTypeEnum {
    GAS_TYPE("瓦斯"),
    GEOLOGY_TYPE("地质"),
    WATER_DETECTION_TYPE("探水"),
    OTHER_TYPE("其他"),
    ;
    private String describes;

    DrillTypeEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
