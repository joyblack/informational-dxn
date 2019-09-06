package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
public class DeviceInfoChangeStatusReq {
    /**
     * 整改记录的ID列表
     */
    @NotEmpty(message = "设备记录不能为空")
    private List<Long> ids;

    /**
     * 设备状态
     */
    @NotNull(message = "设备状态不能为空")
    private DeviceStatusEnum deviceStatus;
}
