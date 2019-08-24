package com.joy.xxfy.informationaldxn.module.driving.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SumDrivingDailyDetailVo {
    private BigDecimal totalDoneLengthSum;
    private BigDecimal totalOutputSum;
    private Long totalPeopleNumberSum;

    public SumDrivingDailyDetailVo() {
    }

    public SumDrivingDailyDetailVo(BigDecimal totalDoneLengthSum, BigDecimal totalOutputSum, Long totalPeopleNumberSum) {
        this.totalDoneLengthSum = totalDoneLengthSum;
        this.totalOutputSum = totalOutputSum;
        this.totalPeopleNumberSum = totalPeopleNumberSum;
    }
}
