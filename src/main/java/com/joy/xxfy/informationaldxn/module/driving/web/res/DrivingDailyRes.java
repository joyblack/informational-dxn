package com.joy.xxfy.informationaldxn.module.driving.web.res;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class DrivingDailyRes extends DrivingDailyEntity {
    /**
     * 日报详情
     */
    private List<DrivingDailyDetailEntity> drivingDailyDetail;
}
