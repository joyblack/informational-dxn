package com.joy.xxfy.informationaldxn.module.driving.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.CrossSectionTypeEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.DrivingTechnologyTypeEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.RockCharacterEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.SupportMethodEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrivingDailyGetListReq extends BasePageReq {

    /**
     * 掘进工作面ID
     */
    private String drivingFaceId;

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
    private Long drivingTeamId;

}
