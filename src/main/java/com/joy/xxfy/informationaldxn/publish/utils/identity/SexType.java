package com.joy.xxfy.informationaldxn.publish.utils.identity;

/**
 * 性别
 */
public enum SexType {

    MAN("MAN","男"),

    WOM("WOM","女");

    private String name;

    private String describe;

    SexType(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }}
