package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.IkAndBvVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.ShiftsAndBValueVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SingleValueVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SkAndBvVo;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BackMiningDailyRepository extends BaseRepository<BackMiningDailyEntity>, JpaRepository<BackMiningDailyEntity, Long> {

    /**
     * 1: 工作面、日期、队伍、班次
     */
    BackMiningDailyEntity findFirstByBackMiningFaceAndDailyTimeAndTeamAndShifts(BackMiningFaceEntity backMiningFaceEntity,
                                                                                Date dailyTime, DepartmentEntity team, DailyShiftEnum shifts);

    /**
     * 1：通过回采面信息获取回采日报信息
     */
    BackMiningDailyEntity findFirstByBackMiningFace(BackMiningFaceEntity info);

    /**
     * n: 通过工作面以及日期获取日报
     */
    List<BackMiningDailyEntity> findAllByBackMiningFaceAndDailyTime(BackMiningFaceEntity backMiningFaceEntity, Date dailyTime);

    /**
     * 1:统计某工作面月累计进尺&月累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo(sum(d.doneLength),sum(d.output)) " +
            " from BackMiningDailyEntity d where d.backMiningFace = :face and concat(year(d.dailyTime),month(d.dailyTime)) = :ym")
    CmStatisticVo statisticDoneLengthAndOutput(@Param("face")BackMiningFaceEntity face, @Param("ym")String ym);


    /**
     * 1: 统计某工作面月累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.BackMiningStatisticVo(sum(d.output)) " +
            " from BackMiningDailyEntity d where d.backMiningFace = :face and concat(year(d.dailyTime),month(d.dailyTime)) = :ym")
    CmStatisticVo statisticOutput(@Param("face")BackMiningFaceEntity face, @Param("ym")String ym);

    /**
     * 1:今日累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo(sum(d.output)) from BackMiningDailyEntity d where d.dailyTime = :dailyTime and d.backMiningFace.belongCompany = :belongCompany")
    StatisticOutputVo statisticTodayOutput(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 今日累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SingleValueVo(sum(d.doneLength)) from BackMiningDailyEntity d where d.dailyTime = :dailyTime and d.backMiningFace.belongCompany = :belongCompany")
    SingleValueVo statisticTodayLength(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 1: 按时间区间统计累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo(sum(d.output)) from BackMiningDailyEntity d where d.dailyTime between :start and :end and d.backMiningFace.belongCompany = :belongCompany")
    StatisticOutputVo statisticThisMonthOutput(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * n: 早中晚班次累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo(d.shifts, sum(d.output)) from BackMiningDailyEntity d where d.dailyTime = :dailyTime and d.backMiningFace.belongCompany = :belongCompany group by d.shifts")
    List<StatisticOutputVo> statisticTodayOutputGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 早中晚班次累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.ShiftsAndBValueVo(d.shifts, sum(d.doneLength)) from BackMiningDailyEntity d where d.dailyTime = :dailyTime and d.backMiningFace.belongCompany = :belongCompany group by d.shifts")
    List<ShiftsAndBValueVo> statisticTodayLengthGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**daily
     * 根据时间区间统计每一天的产煤量
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SkAndBvVo(concat(month(d.dailyTime),'-',day(d.dailyTime)), sum(d.output)) from BackMiningDailyEntity d where d.backMiningFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<SkAndBvVo> statisticEveryDayOutputByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**daily
     * 根据时间区间统计每一天的进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SkAndBvVo(concat(month(d.dailyTime),'-',day(d.dailyTime)), sum(d.doneLength)) from BackMiningDailyEntity d where d.backMiningFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<SkAndBvVo> statisticEveryDayLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据时间区间统计每个月的产煤量
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.IkAndBvVo(month(d.dailyTime), sum(d.output)) from BackMiningDailyEntity d where d.backMiningFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<IkAndBvVo> statisticEveryMonthOutputByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据时间区间统计每月进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.IkAndBvVo(month(d.dailyTime), sum(d.doneLength)) from BackMiningDailyEntity d where d.backMiningFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<IkAndBvVo> statisticEveryMonthLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


}
