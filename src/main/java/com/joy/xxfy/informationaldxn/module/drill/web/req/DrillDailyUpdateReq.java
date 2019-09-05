package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrillDailyUpdateReq extends BaseUpdateReq {
    /**
     * 关联的钻孔工作ID
     */
    @NotNull(message = "钻孔工作不能为空")
    private Long drillWorkId;

    /**
     * 钻孔日期
     */
    @NotNull(message = "日期不能为空")
    private Date dailyTime;

    /**
     * 班次
     */
    @NotNull(message = "班次不能为空")
    private DailyShiftEnum shifts;

    /**
     * 打钻队伍
     */
    @NotNull(message = "打钻队伍不能为空")
    private Long drillTeamId;

    /**
     * 打钻的人数
     */
    @NotNull(message = "打钻人数不能为空")
    private Long peopleNumber;

}
