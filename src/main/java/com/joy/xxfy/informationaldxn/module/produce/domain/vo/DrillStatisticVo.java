package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 钻孔工作统计VO
 */
@Data
@ToString
public class DrillStatisticVo {
    // 名字
    private String name;

    // 打钻进尺
    private BigDecimal dayLength= BigDecimal.ZERO;
    private BigDecimal monthLength= BigDecimal.ZERO;

    public DrillStatisticVo() {
    }

    public DrillStatisticVo(BigDecimal monthLength) {
        this.monthLength = monthLength;
    }
}
