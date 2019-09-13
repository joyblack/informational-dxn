package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class DrillDailySaveItemItem extends BaseAddReq {
    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    private Long orderNumber;

    /**
     * 钻孔ID
     */
    @NotNull(message = "钻孔ID不能为空")
    private Long drillHoleId;

    /**
     * 已打长度
     */
    @NotNull(message = "当日已打长度不能为空")
    @Min(value = 0, message = "当日已打长度不能小于0")
    private BigDecimal doneLength;




}
