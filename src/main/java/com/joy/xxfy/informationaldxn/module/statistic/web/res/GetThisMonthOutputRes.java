package com.joy.xxfy.informationaldxn.module.statistic.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class GetThisMonthOutputRes {
    /**
     * 总产煤
     */
    private BigDecimal totalOutput = BigDecimal.ZERO;

    /**
     * 掘进产煤
     */
    private BigDecimal drivingOutput = BigDecimal.ZERO;

    /**
     * 回采产煤
     */
    private BigDecimal miningOutput = BigDecimal.ZERO;
}
