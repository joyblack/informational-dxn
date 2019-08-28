package com.joy.xxfy.informationaldxn.module.device.domain.defaults;

import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;

public class DeviceInfoDefault {
    // 保养提示天数默认值：3天
    private static final Long tipDays = 3L;

    // 设备状态默认值：报废
    private static final DeviceStatusEnum deviceStatus = DeviceStatusEnum.DEVICE_STATUS_SCRAP;
}
