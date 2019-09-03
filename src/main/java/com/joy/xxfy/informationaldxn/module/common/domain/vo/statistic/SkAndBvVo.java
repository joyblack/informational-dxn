package com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * k - string
 * v - bigDecimal
 */
@Data
@ToString
public class SkAndBvVo {
    private String key;
    private BigDecimal value;

    public SkAndBvVo() {
    }

    public SkAndBvVo(String key, BigDecimal value) {
        this.key = key;
        this.value = value;
    }
}
