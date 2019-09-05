package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrillDailyGetListReq extends BasePageReq {
    /**
     * 钻孔工作ID
     */
    private Long drillWorkId;


    /**
     * 钻孔日期
     */
    private Date dailyTimeStart;
    private Date dailyTimeEnd;

    /**
     * 班次
     */
    private DailyShiftEnum shifts;

    /**
     * 打钻队伍
     */
    private Long drillTeamId;


}
