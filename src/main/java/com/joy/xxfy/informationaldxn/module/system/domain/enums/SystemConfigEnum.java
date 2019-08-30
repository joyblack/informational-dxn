package com.joy.xxfy.informationaldxn.module.system.domain.enums;

public enum SystemConfigEnum {

   USER_DEFAULT_PASSWORD("用户的默认密码");






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
