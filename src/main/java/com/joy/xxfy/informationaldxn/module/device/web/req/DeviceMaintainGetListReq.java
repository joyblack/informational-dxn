package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DeviceMaintainGetListReq extends BasePageReq {
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
     * 设备编号
     */
    private String deviceCode;

    /**
     * 生产厂家
     */
    private String manufacture;

    /**
     * 维保类型
     */
    private MaintainTypeEnum maintainType;



    /**
     * 维保日期区间
     */
    private Date maintainTimeStart;
    private Date maintainTimeEnd;

    /**
     * 维保情况
     */
    private MaintainStatusEnum maintainStatus;

    /**
     * 维保人员
     */
    private String maintainPeople;


}
