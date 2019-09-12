package com.joy.xxfy.informationaldxn.module.system.domain.enums;

public enum SystemConfigEnum {

   USER_DEFAULT_PASSWORD("用户的默认密码"),
    RESOURCE_TREE_SWITCH("资源树加载开关");





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
