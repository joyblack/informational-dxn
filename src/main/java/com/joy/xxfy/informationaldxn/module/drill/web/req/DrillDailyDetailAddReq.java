package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_hole_id", nullable = false)
    private Long drillHoleId;

    /**
     * 打孔长度
     */
    @Column(nullable = false)
    private Long doneLength;



    /**
     * 所属的日报信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_daily_id")
    private DrillDailyEntity drillDaily;

}
