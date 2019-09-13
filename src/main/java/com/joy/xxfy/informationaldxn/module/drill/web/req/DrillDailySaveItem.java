package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class DrillDailySaveItem extends BaseAddReq{
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
    @Min(value = 0,message = "打钻人数不能小于0")
    private Long peopleNumber;

    /**
     * 钻孔列表
     */
    @NotEmpty(message = "至少添加一条钻孔记录信息")
    @Valid
    private List<DrillDailySaveItemItem> items;
}
