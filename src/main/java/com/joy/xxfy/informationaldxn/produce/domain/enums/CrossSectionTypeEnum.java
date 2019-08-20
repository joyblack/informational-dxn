package com.joy.xxfy.informationaldxn.produce.domain.enums;

/**
 * 断面形式
 */
public enum CrossSectionTypeEnum {
    RECTANGLE("矩形"),
    TRAPEZIUM("梯形"),
    HALF_ROUND("半圆形"),
    THREE_ARCH("三星拱");

    private String describes;

    CrossSectionTypeEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
