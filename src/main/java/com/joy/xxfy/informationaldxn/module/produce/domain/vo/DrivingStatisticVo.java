package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 掘进工作面统计VO
 */
@Data
@ToString
public class DrivingStatisticVo {
    // 名字
    private String name;

    // 进尺
    private BigDecimal morningLength= BigDecimal.ZERO;
    private BigDecimal noonLength= BigDecimal.ZERO;
    private BigDecimal eveningLength= BigDecimal.ZERO;
    private BigDecimal dayLength= BigDecimal.ZERO;
    private BigDecimal monthLength= BigDecimal.ZERO;

    public DrivingStatisticVo() {
    }

    public DrivingStatisticVo(BigDecimal monthLength) {
        this.monthLength = monthLength;
    }
}
