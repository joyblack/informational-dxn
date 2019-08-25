package com.joy.xxfy.informationaldxn.module.backmining.web.res;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class BackMiningDailyRes extends BackMiningDailyEntity {
    /**
     * 日报详情
     */
    private List<BackMiningDailyDetailEntity> backMiningDailyDetail;
}
