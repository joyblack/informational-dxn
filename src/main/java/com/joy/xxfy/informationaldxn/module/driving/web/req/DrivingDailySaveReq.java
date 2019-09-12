package com.joy.xxfy.informationaldxn.module.driving.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseSaveReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrivingDailySaveReq extends BaseSaveReq {

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
     * 班次
     */
    @NotNull(message = "班次不能为空")
    private DailyShiftEnum shifts;

    /**
     * 队伍
     */
    @NotNull(message = "掘进队伍不能为空")
    private Long drivingTeamId;

    /**
     * 人数
     */
    private Long peopleNumber;

    /**
     * 进尺
     */
    @NotNull(message = "进尺不能为空")
    @Min(value = 0,message = "进尺不能小于0")
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
