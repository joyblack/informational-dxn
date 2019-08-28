package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ToString
public class DeviceInfoGetListReq extends BasePageReq {
    // 设备名称
    private String deviceName;

    /**
     * 设备分类ID
     */
    private Long deviceCategoryId;

    /**
     * 规格型号
     */
    private String deviceModel;

    /**
     * 生产厂家
     */
    private String manufacture;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 出厂日期区间
     */
    private Date manufactureTimeStart;
    private Date manufactureTimeEnd;

    /**
     * 使用地点
     */
    private String usePlace;

    /**
     * 设备状态
     */
    private DeviceStatusEnum deviceStatus;


}
