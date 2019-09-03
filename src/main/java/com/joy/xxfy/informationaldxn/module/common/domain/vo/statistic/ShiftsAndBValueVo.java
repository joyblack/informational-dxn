package com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * k - shifts
 * v - bigDecimal
 */
@Data
@ToString
public class ShiftsAndBValueVo {
    private DailyShiftEnum shifts;
    private BigDecimal value;

    public ShiftsAndBValueVo() {
    }

    public ShiftsAndBValueVo(DailyShiftEnum shifts, BigDecimal value) {
        this.shifts = shifts;
        this.value = value;
    }
}
