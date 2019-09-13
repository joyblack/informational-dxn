package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class BackMiningDailyUpdateReq extends BaseUpdateReq {
    /**
     * 班次
     */
    @NotNull(message = "班次不能为空")
    private DailyShiftEnum shifts;

    /**
     * 队伍
     */
    @NotNull(message = "回采队伍不能为空")
    private Long teamId;

    /**
     * 人数
     */
    @Min(value = 0,message = "人数不能小于0")
    private Long peopleNumber;

    /**
     * 进尺
     */
    @NotNull(message = "推进度不能为空")
    @Min(value = 0,message = "推进度不能小于0")
    private BigDecimal doneLength;

    /**
     * 产量(t)
     */
    @Min(value = 0,message = "产量不能小于0")
    private BigDecimal output;

    /**
     * 工作内容
     */
    private String workContent;

}
