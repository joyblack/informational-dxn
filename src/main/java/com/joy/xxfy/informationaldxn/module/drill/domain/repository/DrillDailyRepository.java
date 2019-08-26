package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DrillDailyRepository extends BaseRepository<DrillDailyEntity>, JpaRepository<DrillDailyEntity, Long> {
    // 打钻工作、日期、班次、打钻队伍 获取日报信息
    DrillDailyEntity findAllByDrillWorkAndDrillTeamAndDailyTimeAndShifts(DrillWorkEntity drillWorkEntity, DepartmentEntity team,
                                                                               Date dailyTime, DailyShiftEnum shifts);

    // 通过打钻工作获取打钻日报
    List<DrillDailyEntity> findAllByDrillWork(DrillWorkEntity drillWorkEntity);


}
