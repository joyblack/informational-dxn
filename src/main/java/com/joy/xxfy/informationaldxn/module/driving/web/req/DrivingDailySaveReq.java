package com.joy.xxfy.informationaldxn.module.driving.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class DrivingDailySaveReq extends BaseAddReq {

    /**
     * 关联的掘进工作面信息
     */
    @NotNull(message = "掘进工作面信息不能为空")
    private Long drivingFaceId;

    /**
     * 日期
     */
    @NotNull(message = "日期不能为空")
    private Date dailyTime;

    /**
     * 详情
     */
    @Valid
    @NotNull
    private List<DrivingDailySaveItem> items;

}
