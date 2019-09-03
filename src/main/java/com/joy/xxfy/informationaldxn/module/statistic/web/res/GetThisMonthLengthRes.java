package com.joy.xxfy.informationaldxn.module.statistic.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class GetThisMonthLengthRes {

    /**
     * 掘进进尺
     */
    private BigDecimal drivingLength = BigDecimal.ZERO;

    /**
     * 回采进尺
     */
    private BigDecimal miningLength = BigDecimal.ZERO;
}
