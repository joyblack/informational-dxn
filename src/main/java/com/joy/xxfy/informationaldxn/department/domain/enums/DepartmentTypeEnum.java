package com.joy.xxfy.informationaldxn.department.domain.enums;

public enum DepartmentTypeEnum {
    // CP = company
    // CM = coal mine
    CP_GROUP("集团公司所属部门"),

    CM_PLATFORM("煤矿平台所属部门");

    private String describe;

    DepartmentTypeEnum() {

    }

    DepartmentTypeEnum(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }


}
