package com.joy.xxfy.informationaldxn.module.produce.web.res;

import com.joy.xxfy.informationaldxn.module.produce.domain.entity.ProduceCmDailyEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 煤矿日报的统计结果返回
 */
@Data
@ToString
public class CmStatisticRes extends ProduceCmDailyEntity {
    // 掘进
    List<CmStatisticVo> drivingStatistic;

    // 回采
    List<CmStatisticVo> backMiningStatistic;

    // 打钻
    List<CmStatisticVo> drillStatistic;

}
