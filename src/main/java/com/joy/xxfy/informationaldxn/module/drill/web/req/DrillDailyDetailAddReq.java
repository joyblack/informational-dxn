package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class DrillDailyDetailAddReq extends BaseAddReq {
    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    private Long orderNumber;

    /**
     * 钻孔信息(钻孔工作详情)
     */
    @NotNull(message = "钻孔ID不能为空")
    private Long drillHoleId;

    /**
     * 打孔长度
     */
    @NotNull(message = "打孔长度不能为空")
    private BigDecimal doneLength;

    /**
     * 所属的日报信息
     */
    @NotNull(message = "所属日报信息不能为空")
    private Long drillDailyId;

}
