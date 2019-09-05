package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class DeviceMaintainAddReq extends BaseAddReq {
    /**
     * 设备信息
     */
    @NotNull(message = "设备信息不能为空")
    private Long deviceInfoId;

    /**
     * 维保日期
     */
    @NotNull(message = "维保日期不能为空")
    private Date maintainTime;

    /**
     * 维保情况
     */
    @NotNull(message = "维保情况不能为空")
    private MaintainStatusEnum maintainStatus;

    /**
     * 维保类型
     */
    private MaintainTypeEnum maintainType;

    /**
     * 维保人员
     */
    private String maintainPeople;


    /**
     * 维保详情
     */
    private String maintainDetail;

}
