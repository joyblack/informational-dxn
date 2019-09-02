package com.joy.xxfy.informationaldxn.module.common.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class WorkProgressVo {
    // 工作名
    private String workName;
    // 全长
    private BigDecimal totalLength;
    // 已处理
    private BigDecimal doneLength;

    public WorkProgressVo() {
    }

    public WorkProgressVo(String workName,BigDecimal doneLength,  BigDecimal totalLength) {
        this.workName = workName;
        this.totalLength = totalLength;
        this.doneLength = doneLength;
    }
}
