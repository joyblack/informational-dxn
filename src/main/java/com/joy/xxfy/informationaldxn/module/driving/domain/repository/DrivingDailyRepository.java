package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.IkAndBvVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.ShiftsAndBValueVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SingleValueVo;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SkAndBvVo;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrivingStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrivingDailyRepository extends BaseRepository<DrivingDailyEntity>, JpaRepository<DrivingDailyEntity, Long> {
    /*



    // 获取日报详情信息
    List<DrivingDailyDetailEntity> findAllByDrivingDaily(DrivingDailyEntity drivingDaily);

    // 更新日报关联的详细信息删除状态
    @Modifying
    @Query("update DrivingDailyDetailEntity d set isDelete = :isDelete where d.drivingDaily = :drivingDaily")
    void updateIsDeleteByDrivingDaily(@Param("drivingDaily") DrivingDailyEntity drivingDaily, @Param("isDelete") boolean isDelete);


    // 统计某个工作面的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from DrivingDailyDetailEntity d where d.drivingDaily.drivingFace = :drivingFace")
    SumDrivingDailyDetailVo aggDailyDetail(@Param("drivingFace") DrivingFaceEntity drivingFace);

    // 统计某个日报的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from DrivingDailyDetailEntity d where d.drivingDaily = :drivingDaily")
    SumDrivingDailyDetailVo aggDailyDetail(@Param("drivingDaily") DrivingDailyEntity drivingDailyEntity);
*/

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
     * 今日累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo(sum(d.output)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany")
    StatisticOutputVo statisticTodayOutput(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 今日累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SingleValueVo(sum(d.doneLength)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany")
    SingleValueVo statisticTodayLength(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 按时间区间统计累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo(sum(d.output)) from DrivingDailyEntity d where d.dailyTime between :start and :end and d.drivingFace.belongCompany = :belongCompany")
    StatisticOutputVo statisticThisMonthOutput(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 早中晚班次累计产煤
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo(d.shifts, sum(d.output)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany group by d.shifts")
    List<StatisticOutputVo> statisticTodayOutputGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 早中晚班次累计进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.ShiftsAndBValueVo(d.shifts, sum(d.doneLength)) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany group by d.shifts")
    List<ShiftsAndBValueVo> statisticTodayLengthGroupByShifts(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime") Date dailyTime);

    /**
     * 根据时间区间统计每一天的产煤量
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SkAndBvVo(concat(month(d.dailyTime), '-', day(d.dailyTime)), sum(d.output)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<SkAndBvVo> statisticEveryDayOutputByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据时间区间统计每一天的进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.SkAndBvVo(concat(month(d.dailyTime), '-', day(d.dailyTime)), sum(d.doneLength)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by d.dailyTime")
    List<SkAndBvVo> statisticEveryDayLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 根据时间区间统计每个月的产煤量
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.IkAndBvVo(month(d.dailyTime), sum(d.output)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<IkAndBvVo> statisticEveryMonthOutputByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);


    /**
     * 根据时间区间统计每月进尺
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.statistic.IkAndBvVo(month(d.dailyTime), sum(d.doneLength)) from DrivingDailyEntity d where d.drivingFace.belongCompany = :belongCompany and d.dailyTime between :start and :end group by month(d.dailyTime)")
    List<IkAndBvVo> statisticEveryMonthLengthByTimeZone(@Param("belongCompany") DepartmentEntity belongCompany, @Param("start") Date start, @Param("end") Date end);
}
