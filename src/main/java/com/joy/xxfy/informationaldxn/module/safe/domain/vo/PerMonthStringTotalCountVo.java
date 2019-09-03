package com.joy.xxfy.informationaldxn.module.safe.domain.vo;

import lombok.Data;
import lombok.ToString;

/**
 * 和PerMonthTotalCountVo不同的是返回的key是美化过的数据。
 */
@Data
@ToString(callSuper = true)
public class PerMonthStringTotalCountVo {
    public String month;
    public Long ct;

    public PerMonthStringTotalCountVo() {
    }

    public PerMonthStringTotalCountVo(String month, Long ct) {
        this.month = month;
        this.ct = ct;
    }
}
