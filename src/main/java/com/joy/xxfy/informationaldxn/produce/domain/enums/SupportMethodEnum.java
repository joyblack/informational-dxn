package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 * 支护方式
 */
public enum SupportMethodEnum {
    ANCHOR_SPRAY("锚喷"),
    ANCHOR_NET("锚网"),
    MASONRY("砌碹"),
    CER_MINUS("架棚"),
    COMBINED_SUPPORT("联合支护"),
    U_SHAPED_SHED("U型棚")
    ;

    private String describes;

    SupportMethodEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
