package com.joy.xxfy.informationaldxn.module.statistic.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class KeyAndValueVo<T,V> {
    private T key;
    private V value;

    public KeyAndValueVo() {
    }

    public KeyAndValueVo(T key, V value) {
        this.key = key;
        this.value = value;
    }
}
