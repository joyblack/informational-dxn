package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class BackMiningDailyDetailGetListReq extends BasePageReq {

    /**
     * 掘进工作面ID
     */
    private String backMiningFaceId;

    /**
     * 日期起止
     */
    private Date dailyTimeStart;
    private Date dailyTimeEnd;

    /**
     * 班次
     */
    private DailyShiftEnum shifts;

    /**
     * 队伍
     */
    private Long teamId;

}
