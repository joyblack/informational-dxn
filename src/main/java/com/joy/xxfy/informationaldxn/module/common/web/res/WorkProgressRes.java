package com.joy.xxfy.informationaldxn.module.common.web.res;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class WorkProgressRes {
    // 工作名
    private String workName;
    // 全长
    private BigDecimal totalLength;
    // 已处理
    private BigDecimal doneLength;
    // 完成进度
    private String progress;
    private String progressUsePercent;
    // 剩余
    private BigDecimal leftLength;

}
