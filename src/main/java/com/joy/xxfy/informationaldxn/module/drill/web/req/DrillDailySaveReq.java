package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class DrillDailySaveReq extends BaseAddReq {
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
     * 详情
     */
    @NotNull(message = "日志列表不能为空")
    @Valid
    private List<DrillDailySaveItem> items;

}
