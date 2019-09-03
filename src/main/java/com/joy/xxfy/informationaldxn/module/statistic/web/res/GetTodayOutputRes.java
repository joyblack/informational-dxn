package com.joy.xxfy.informationaldxn.module.statistic.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class GetTodayOutputRes {
    /**
     * 今日累计产煤
     */
    private BigDecimal todayOutput = BigDecimal.ZERO;

    /**
     * 今日早班产煤
     */
    private BigDecimal morningOutput = BigDecimal.ZERO;

    /**
     * 中班产煤
     */
    private BigDecimal noonOutput = BigDecimal.ZERO;

    /**
     * 晚班产煤
     */
    private BigDecimal eveningOutput= BigDecimal.ZERO;
}
