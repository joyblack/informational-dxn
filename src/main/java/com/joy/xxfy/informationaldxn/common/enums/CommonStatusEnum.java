package com.joy.xxfy.informationaldxn.common.enums;

public enum CommonStatusEnum {
    STOP("STOP","停用"),START("START","启用");
    private String name;
    private String describe;

    CommonStatusEnum(String name, String describe) {
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
    }
}
