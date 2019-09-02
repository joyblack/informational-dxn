package com.joy.xxfy.informationaldxn.module.safe.domain.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PerMonthTotalCountVo{
    public Integer month;
    public Long ct;

    public PerMonthTotalCountVo() {
    }

    public PerMonthTotalCountVo(Integer month, Long ct) {
        this.month = month;
        this.ct = ct;
    }
}
