package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 统计产煤
 */
@Data
@ToString
public class StatisticOutputVo {
    private BigDecimal output;
    // 班次
    private DailyShiftEnum shifts;

    public StatisticOutputVo() {
    }

    public StatisticOutputVo(DailyShiftEnum shifts, BigDecimal output) {
        this.shifts = shifts;
        this.output = output;
    }

    public StatisticOutputVo(BigDecimal output) {
        this.output = output;
    }
}
