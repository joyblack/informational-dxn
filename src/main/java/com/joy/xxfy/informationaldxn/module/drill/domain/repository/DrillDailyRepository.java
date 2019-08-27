package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrillStatisticVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrillDailyRepository extends BaseRepository<DrillDailyEntity>, JpaRepository<DrillDailyEntity, Long> {
    // 打钻工作、日期、班次、打钻队伍 获取日报信息: drill_work_id = ? and daily_time = ? and team = ? and shifts = ?
    DrillDailyEntity findAllByDrillWorkAndDrillTeamAndDailyTimeAndShifts(DrillWorkEntity drillWorkEntity, DepartmentEntity team,
                                                                               Date dailyTime, DailyShiftEnum shifts);

    // 通过打钻工作获取打钻日报: drill_work_id = ?
    List<DrillDailyEntity> findAllByDrillWork(DrillWorkEntity drillWorkEntity);

    // 获取打钻工作某日的日报信息: drill_work_id = ? and daily_time = ?
    List<DrillDailyEntity> findAllByDrillWorkAndDailyTime(DrillWorkEntity drillWorkEntity, Date dailyTime);

    // 统计某工作面月累计进尺&月累计产煤
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrillStatisticVo(sum(d.totalDoneLength)) " +
            " from DrillDailyEntity d where d.drillWork = :work and d.dailyTime between :start and :end")
    DrillStatisticVo statisticDoneLength(@Param("work") DrillWorkEntity work, @Param("start") Date start, @Param("end")Date end);
}
