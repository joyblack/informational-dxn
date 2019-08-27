package com.joy.xxfy.informationaldxn.module.produce.web.res;

import com.joy.xxfy.informationaldxn.module.produce.domain.entity.ProduceCmDailyEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CpStatisticVo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 煤矿日报的统计结果返回
 */
@Data
@ToString
public class CpStatisticRes {
    // 煤矿平台名称
    private String cmPlatformName;

    // 掘进
    List<CpStatisticVo> drivingStatistic;

    // 回采
    List<CpStatisticVo> backMiningStatistic;

    // 打钻
    List<CpStatisticVo> drillStatistic;

    // 备注信息
    private String remarks;

}
