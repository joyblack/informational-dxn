package com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SingleValueVo {
    private Integer intValue;

    private Long longValue;

    private BigDecimal bigDecimalValue;

    public SingleValueVo() {
    }

    public SingleValueVo(Integer intValue) {
        this.intValue = intValue;
    }

    public SingleValueVo(Long longValue) {
        this.longValue = longValue;
    }

    public SingleValueVo(BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }
}
