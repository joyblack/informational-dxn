package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 回采工作面统计VO
 */
@Data
@ToString
public class BackMiningStatisticVo {
    // 名字
    private String name;

    // 产量
    private BigDecimal morningOutput = BigDecimal.ZERO;
    private BigDecimal noonOutput= BigDecimal.ZERO;
    private BigDecimal eveningOutput= BigDecimal.ZERO;
    private BigDecimal dayOutput= BigDecimal.ZERO;
    private BigDecimal monthOutput= BigDecimal.ZERO;

    public BackMiningStatisticVo() {
    }

    public BackMiningStatisticVo(BigDecimal monthOutput) {
        this.monthOutput = monthOutput;
    }
}
