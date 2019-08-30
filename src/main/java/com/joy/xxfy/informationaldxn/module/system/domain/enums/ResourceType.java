package com.joy.xxfy.informationaldxn.module.system.domain.enums;

public enum ResourceType {
    RESOURCE_TYPE_MENU("菜单资源"),

    RESOURCE_TYPE_BUTTON("按钮资源");

    private String describes;

    ResourceType() {

    }

    ResourceType(String describes) {
        this.describes = describes;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribe(String describes) {
        this.describes = describes;
    }


}
