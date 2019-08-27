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
    /**
     * 工作面名称
     */
    private String workName;

    /*早中晚圆进尺*/
    private BigDecimal morningLength = BigDecimal.ZERO;
    private BigDecimal noonLength = BigDecimal.ZERO;
    private BigDecimal eveningLength = BigDecimal.ZERO;
    private BigDecimal shiftTotalLength = BigDecimal.ZERO;

    /*早中晚入井人数*/
    private Long morningPeople = 0L;
    private Long noonPeople = 0L;
    private Long eveningPeople = 0L;
    private Long shiftTotalPeople = 0L;

    public CmStatisticVo() {
    }

    public CmStatisticVo(BigDecimal monthLength) {
        this.monthLength = monthLength;
    }

    public CmStatisticVo(BigDecimal monthLength, BigDecimal monthOutput) {
        this.monthLength = monthLength;
        this.monthOutput = monthOutput;
    }

    /**
     * 月累计进尺
     */
    private BigDecimal monthLength = BigDecimal.ZERO;

    /**
     * 今日产煤
     */
    private BigDecimal todayOutput = BigDecimal.ZERO;


    /**
     * 月累计产煤
     */
    private BigDecimal monthOutput = BigDecimal.ZERO;


}
