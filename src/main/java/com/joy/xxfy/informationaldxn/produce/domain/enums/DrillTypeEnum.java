package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 *  钻孔类型
 */
public enum DrillTypeEnum {
    GAS("瓦斯"),
    GEOLOGY("地质"),
    WATER_DETECTION("探水"),
    OTHER("其他"),
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
