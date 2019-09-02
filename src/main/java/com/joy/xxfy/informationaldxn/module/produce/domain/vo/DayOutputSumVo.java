package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class DayOutputSumVo {

    private BigDecimal morningOutput= BigDecimal.ZERO;
    private BigDecimal noonOutput = BigDecimal.ZERO;
    private BigDecimal eveningOutput = BigDecimal.ZERO;
    private BigDecimal dayOutput = BigDecimal.ZERO;

    public DayOutputSumVo() {
    }

    public DayOutputSumVo(BigDecimal morningOutput, BigDecimal noonOutput, BigDecimal eveningOutput) {
        this.morningOutput = morningOutput;
        this.noonOutput = noonOutput;
        this.eveningOutput = eveningOutput;
    }
}
