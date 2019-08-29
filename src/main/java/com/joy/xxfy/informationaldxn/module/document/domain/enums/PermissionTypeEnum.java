package com.joy.xxfy.informationaldxn.module.document.domain.enums;

public enum PermissionTypeEnum {
    PER_PUBLIC("公开"),
    PER_PRIVATE("私有"),
    PER_SEPARATE("部分"),
    PER_DEPARTMENT("部门");

    private String describes;

    PermissionTypeEnum() {
    }

    PermissionTypeEnum(String describes) {
        this.describes = describes;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
