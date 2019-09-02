package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BackMiningDailyRepository extends BaseRepository<BackMiningDailyEntity>, JpaRepository<BackMiningDailyEntity, Long> {
    /*
    // 获取日报详情信息
    List<BackMiningDailyDetailEntity> findAllByBackMiningDaily(BackMiningDailyEntity backMiningDailyEntity);

    // 更新日报关联的详细信息删除状态
    @Modifying
    @Query("update BackMiningDailyDetailEntity d set isDelete = :isDelete where d.backMiningDaily = :daily")
    void updateIsDeleteByBackMiningDaily(@Param("daily") BackMiningDailyEntity daily, @Param("isDelete") boolean isDelete);


    // 统计某个工作面的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from BackMiningDailyDetailEntity d where d.backMiningDaily.backMiningFace = :backMiningFace")
    SumBackMiningDailyDetailVo aggDailyDetail(@Param("backMiningFace") BackMiningFaceEntity backMiningFace);


    // 统计某个日报的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from BackMiningDailyDetailEntity d where d.backMiningDaily = :daily")
    SumBackMiningDailyDetailVo aggDailyDetail(@Param("daily") BackMiningDailyEntity daily);


    /**
     * 统计日累计产煤(不区分工作面)
     */
    /*
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.DayOutputSumVo(sum(d.output)) from BackMiningDailyDetailEntity d where d.backMiningDaily.backMiningFace.belongCompany = :belongCompany and d.backMiningDaily.dailyTime = :dailyTime group by d.shifts")
    DayOutputSumVo statisticDayOutput(@Param("belongComapny")DepartmentEntity belongCompany, @Param("dailyTime")Date dailyTime);

     */

    /*


    // 通过工作面获取日报列表
    List<BackMiningDailyEntity> findAllByBackMiningFace(BackMiningFaceEntity backMiningFaceEntity);



     */

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


}
