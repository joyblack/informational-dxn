package com.joy.xxfy.informationaldxn.module.statistic.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SingleValueVo<T> {
    private T value;

    public SingleValueVo() {
    }

    public SingleValueVo(T value) {
        this.value = value;
    }
}
