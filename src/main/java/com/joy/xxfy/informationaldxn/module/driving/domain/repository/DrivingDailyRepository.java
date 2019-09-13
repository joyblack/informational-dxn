package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrivingStatisticVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DrivingDailyRepository extends BaseRepository<DrivingDailyEntity>, JpaRepository<DrivingDailyEntity, Long> {

    /**
     * 1：同工作面、时间、队伍、同班次
     */
    DrivingDailyEntity findFirstByDrivingFaceAndDailyTimeAndDrivingTeamAndShifts(@Param("drivingFace") DrivingFaceEntity drivingFace, @Param("dailyTime") Date dailyTime, @Param("department") DepartmentEntity departmentEntity, @Param("shifts") DailyShiftEnum shifts);

    /**
     * 1:通过掘进工作面信息获取日报信息
     */
    DrivingDailyEntity findFirstByDrivingFace(DrivingFaceEntity drivingFace);

    /**
     * n: 通过工作面以及日期获取日报
     */
    List<DrivingDailyEntity> findAllByDrivingFaceAndDailyTime(DrivingFaceEntity drivingFaceEntity, Date dailyTime);

    /**
     * 1: 统计某工作面月累计进尺&月累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo(sum(d.doneLength),sum(d.output)) " +
            " from DrivingDailyEntity d where d.drivingFace = :face and concat(year(d.dailyTime),month(d.dailyTime)) = :ym")
    CmStatisticVo statisticDoneLengthAndOutPut(@Param("face") DrivingFaceEntity drivingFace, @Param("ym")String ym);

    /**
     * 1：统计某工作面月累计进尺&月累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrivingStatisticVo(sum(d.doneLength)) " +
            " from DrivingDailyEntity d where d.drivingFace = :face and concat(year(d.dailyTime),month(d.dailyTime)) = :ym")
    DrivingStatisticVo statisticDoneLength(@Param("face") DrivingFaceEntity drivingFace, @Param("ym")String ym);


    /**
     * 统计指定工作面具体某一天的总进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo(sum(d.doneLength)) from DrivingDailyEntity d where d.drivingFace = :drivingFace and d.dailyTime = :dailyTime")
    SingleValueVo<BigDecimal> statisticDoneLength(DrivingFaceEntity drivingFace, Date dailyTime);


    /**
     * 今日累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo(sum(d.output)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany")
    SingleValueVo<BigDecimal> statisticTodayOutput(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 今日累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo(sum(d.doneLength)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany")
    SingleValueVo<BigDecimal> statisticTodayLength(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 按时间区间统计累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo(sum(d.output)) from DrivingDailyEntity d where d.dailyTime between :start and :end and d.drivingFace.belongCompany = :belongCompany")
    SingleValueVo<BigDecimal> statisticThisMonthOutput(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 按时间区间统计累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo(sum(d.doneLength)) from DrivingDailyEntity d where d.dailyTime between :start and :end and d.drivingFace.belongCompany = :belongCompany")
    SingleValueVo<BigDecimal> statisticThisMonthLength(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 早中晚班次累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(d.shifts, sum(d.output)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany group by d.shifts")
    List<KeyAndValueVo<DailyShiftEnum,BigDecimal>> statisticTodayOutputGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 早中晚班次累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(d.shifts, sum(d.doneLength)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany group by d.shifts")
    List<KeyAndValueVo<DailyShiftEnum,BigDecimal>> statisticTodayLengthGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 根据时间区间统计每一天的产煤量
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(concat(month(d.dailyTime), '-', day(d.dailyTime)), sum(d.output)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<KeyAndValueVo<String,BigDecimal>> statisticEveryDayOutputByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据时间区间统计每一天的进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(concat(month(d.dailyTime), '-', day(d.dailyTime)), sum(d.doneLength)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<KeyAndValueVo<String,BigDecimal>> statisticEveryDayLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 根据时间区间统计每个月的产煤量
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(month(d.dailyTime), sum(d.output)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<KeyAndValueVo<Integer,BigDecimal>> statisticEveryMonthOutputByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 根据时间区间统计每月进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo(month(d.dailyTime), sum(d.doneLength)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<KeyAndValueVo<Integer,BigDecimal>> statisticEveryMonthLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 统计时间区间内，填写了日报的时间
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo(d.dailyTime) from DrivingDailyEntity d where d.dailyTime between :startDate and :endDate and d.drivingFace.belongCompany = :belongCompany")
    Set<DateVo> findAllFillDate(Date startDate, Date endDate, DepartmentEntity belongCompany);

    /**
     * 通过工作面信息、日期，删除日报信息
     */
    @Modifying
    void deleteAllByDrivingFaceAndDailyTime(DrivingFaceEntity drivingFace, Date dailyTime);
}
