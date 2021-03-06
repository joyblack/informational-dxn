package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class DrillDailyDetailUpdateReq extends BaseUpdateReq {
    /**
     * 序号
     */
    @NotNull(message = "序号不能为空")
    private Long orderNumber;

    /**
     * 打孔长度
     */
    @NotNull(message = "打孔长度不能为空")
    private BigDecimal doneLength;


}
