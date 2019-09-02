package com.joy.xxfy.informationaldxn.module.safe.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class ThisMonthStatusCountRes {
    // 总数、已整改、未整改、整改率
    private Long totalNumber;
    private Long yesNumber;
    private Long noNumber;
    private String rate;
}
