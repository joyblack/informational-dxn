package com.joy.xxfy.informationaldxn.module.device.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class WhetherExistSameNameDeviceReq {
    // 设备名称
    @NotBlank(message = "设备名称不能为空")
    private String deviceName;
}
