package com.joy.xxfy.informationaldxn.file.enums;

/**
 * @author 13562
 */

public enum UploadModuleEnums {
    STAFF_ID_NUMBER_PHOTO("员工身份证照片"),
    STAFF_ONE_INCH_PHOTO("员工一寸照片"),
    ;

    private String name;

    UploadModuleEnums() {
    }

    UploadModuleEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String insured) {
        this.name = name;
    }
}
