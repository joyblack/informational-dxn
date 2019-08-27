package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 煤矿统计实体
 */
@Data
@ToString
public class CmStatisticVo {
    /*早中晚圆进尺*/
    private BigDecimal morningLength;
    private BigDecimal noonLength;
    private BigDecimal eveningLength;
    private BigDecimal shiftTotalLength;

    /*早中晚入井人数*/
    private BigDecimal morningPeople;
    private BigDecimal noonPeople;
    private BigDecimal eveningPeople;
    private BigDecimal shiftTotalPeople;

    /**
     * 月累计进尺
     */
    private BigDecimal monthLength;

    /**
     * 今日产煤
     */
    private BigDecimal todayOutput;


    /**
     * 月累计产煤
     */
    private BigDecimal monthOutput;

}
