package com.joy.xxfy.informationaldxn.module.common.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * k - Integer
 * v - bigDecimal
 */
@Data
@ToString
public class IkAndBvVo {
    private Integer key;
    private BigDecimal value;

    public IkAndBvVo() {
    }

    public IkAndBvVo(Integer key, BigDecimal value) {
        this.key = key;
        this.value = value;
    }
}
