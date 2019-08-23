package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrillDailyGetListReq extends BasePageReq {
    /**
     * 钻孔工作名称
     */
    private String drillWorkName;


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