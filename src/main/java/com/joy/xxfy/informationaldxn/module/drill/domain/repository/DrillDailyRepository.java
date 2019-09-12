package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import cn.hutool.core.date.DateTime;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrillStatisticVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DrillDailyRepository extends BaseRepository<DrillDailyEntity>, JpaRepository<DrillDailyEntity, Long> {
    /**
     * 打钻工作、日期、班次、打钻队伍 获取日报信息: drill_work_id = ? and daily_time = ? and team = ? and shifts = ?
     */
    DrillDailyEntity findFirstByDrillWorkAndDrillTeamAndDailyTimeAndShifts(DrillWorkEntity drillWorkEntity, DepartmentEntity team,
                                                                               Date dailyTime, DailyShiftEnum shifts);

    /**
     * 通过打钻工作获取打钻日报: drill_work_id = ?
     */
    List<DrillDailyEntity> findAllByDrillWork(DrillWorkEntity drillWorkEntity);

    /**
     * 获取打钻工作某日的日报信息: drill_work_id = ? and daily_time = ?
     */
    List<DrillDailyEntity> findAllByDrillWorkAndDailyTime(DrillWorkEntity drillWorkEntity, Date dailyTime);

    /**
     * 统计时间区间内，填写了日报的时间
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo(d.dailyTime) from DrillDailyEntity d where d.dailyTime between :startDate and :endDate and d.drillWork.belongCompany = :belongCompany")
    Set<DateVo> findAllFillDate(Date startDate, Date endDate, DepartmentEntity belongCompany);



    /**
     * 统计某工作面月累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrillStatisticVo(sum(d.totalDoneLength)) " +
            " from DrillDailyEntity d where d.drillWork = :work and concat(year(d.dailyTime),month(d.dailyTime)) = :ym")
    DrillStatisticVo statisticDoneLength(@Param("work") DrillWorkEntity work, @Param("ym")String ym);

    /**
     * 早中晚班次累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(d.shifts, sum(d.totalDoneLength)) from DrillDailyEntity d where d.dailyTime = :dailyTime and d.drillWork.belongCompany = :belongCompany group by d.shifts")
    List<KeyAndValueVo<DailyShiftEnum,BigDecimal>> statisticLengthGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 根据时间区间统计每一天的进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(concat(month(d.dailyTime), '-', day(d.dailyTime)), sum(d.totalDoneLength)) from DrillDailyEntity d where d.drillWork.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<KeyAndValueVo<String, BigDecimal>> statisticEveryDayLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * 1. 按时间区间统计累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo(sum(d.totalDoneLength)) from DrillDailyEntity d where d.dailyTime between :start and :end and d.drillWork.belongCompany = :belongCompany")
    SingleValueVo<BigDecimal> statisticThisMonthLength(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据时间区间统计每月进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(month(d.dailyTime), sum(d.totalDoneLength)) from DrillDailyEntity d where d.drillWork.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<KeyAndValueVo<Integer,BigDecimal>> statisticEveryMonthLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

}
