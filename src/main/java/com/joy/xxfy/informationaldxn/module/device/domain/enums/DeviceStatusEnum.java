package com.joy.xxfy.informationaldxn.module.device.domain.enums;

/**
 * 设备状态
 */
public enum DeviceStatusEnum {
    DEVICE_STATUS_USING("在用"),
    DEVICE_STATUS_MAINTAINING("维保中"),
    DEVICE_STATUS_TRANSFER("外调"),
    DEVICE_STATUS_BORROW("外借"),
    DEVICE_STATUS_SCRAP("报废");

    private String describes;

    DeviceStatusEnum(String describe) {
        this.describes = describe;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }
}
