package com.joy.xxfy.informationaldxn.module.drill.web.res;

import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DrillDailyRes extends DrillDailyEntity {
    // 打钻详情
    private List<DrillDailyDetailEntity> drillDailyDetail;
}
