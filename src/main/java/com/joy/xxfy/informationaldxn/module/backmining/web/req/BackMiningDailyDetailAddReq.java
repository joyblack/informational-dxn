package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class BackMiningDailyDetailAddReq extends BaseAddReq {
    /**
     * 所属的日报信息
     */
    @NotNull(message = "所属日报信息不能为空")
    private Long backMiningDailyId;

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
    private Long peopleNumber;

    /**
     * 进尺
     */
    @NotNull(message = "推进都不能为空")
    private BigDecimal doneLength;

    /**
     * 产量(t)
     */
    private BigDecimal output;

    /**
     * 工作内容
     */
    private String workContent;

}
