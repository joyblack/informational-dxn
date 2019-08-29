package com.joy.xxfy.informationaldxn.module.user.domain.enums;


public enum UserTypeEnum {
    /* CP = company => 集团
     *  CM = coal mine => 煤矿平台
     * */
    CM_ADMIN("CM_ADMIN","煤矿平台管理员"),
    CP_ADMIN("CP_ADMIN","集团管理员");

    private String name;

    private String describe;

    UserTypeEnum(String name, String describe) {
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
