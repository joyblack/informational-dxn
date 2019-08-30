package com.joy.xxfy.informationaldxn.module.common.enums;

/**
 * 限制角色类型
 */
public enum LimitUserTypeEnum {
    CM_ADMIN("煤矿平台用户"),
    CP_ADMIN("集团用户"),
    ALL("所有用户")
    ;
    private String describe;

    LimitUserTypeEnum(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
