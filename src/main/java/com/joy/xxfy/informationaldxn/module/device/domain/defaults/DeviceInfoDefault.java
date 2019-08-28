package com.joy.xxfy.informationaldxn.module.device.domain.defaults;

import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;

public class DeviceInfoDefault {
    // 默认序号
    public static final Long orderNumber = 0L;

    // 保养提示天数默认值：3天
    public static final Long tipDays = 3L;

    // 设备状态默认值：在用
    public static final DeviceStatusEnum deviceStatus = DeviceStatusEnum.DEVICE_STATUS_USING;

    // 名称的分隔符
    public static final String DEVICE_NAME_SEPARATE = "-";

    // 维保次数
    public static final Long maintainNumber = 0L;
}
