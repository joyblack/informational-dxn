package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseSaveReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class BackMiningDailySaveReq extends BaseSaveReq {
    /**
     * 关联的工作面信息
     */
    @NotNull(message = "工作面信息不能为空")
    private Long backMiningFaceId;

    /**
     * 日期
     */
    @NotNull(message = "日期不能为空")
    private Date dailyTime;


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
