package com.joy.xxfy.informationaldxn.module.common.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 只需要回一个值的返回映射实体
 */
@Data
@ToString
public class NameValueRes<T> {
    private String name;
    private T value;

    public NameValueRes() {
    }

    public NameValueRes(String name, T value) {
        this.name = name;
        this.value = value;
    }
}
