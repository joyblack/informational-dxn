package com.joy.xxfy.informationaldxn.module.backmining.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SumBackMiningDailyDetailVo {
    private BigDecimal totalDoneLengthSum;
    private BigDecimal totalOutputSum;
    private Long totalPeopleNumberSum;

    public SumBackMiningDailyDetailVo() {
    }

    public SumBackMiningDailyDetailVo(BigDecimal totalDoneLengthSum, BigDecimal totalOutputSum, Long totalPeopleNumberSum) {
        this.totalDoneLengthSum = totalDoneLengthSum;
        this.totalOutputSum = totalOutputSum;
        this.totalPeopleNumberSum = totalPeopleNumberSum;
    }
}
