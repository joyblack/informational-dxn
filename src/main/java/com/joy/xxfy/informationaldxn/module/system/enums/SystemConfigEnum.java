package com.joy.xxfy.informationaldxn.module.system.enums;

public enum SystemConfigEnum {

    CM_PLATFORM_USER_DEFAULT_PASSWORD("煤矿管理平台用户的默认密码");

    private String describes;

    SystemConfigEnum(String describes){
        this.describes = describes;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
