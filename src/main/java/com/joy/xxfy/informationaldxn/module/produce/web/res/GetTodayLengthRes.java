package com.joy.xxfy.informationaldxn.module.produce.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class GetTodayLengthRes {
    /**
     * 今日累计进尺
     */
    private BigDecimal todayLength = BigDecimal.ZERO;

    /**
     * 今日早班进尺
     */
    private BigDecimal morningLength = BigDecimal.ZERO;

    /**
     * 中班进尺
     */
    private BigDecimal noonLength = BigDecimal.ZERO;

    /**
     * 晚班进尺
     */
    private BigDecimal eveningLength = BigDecimal.ZERO;
}
