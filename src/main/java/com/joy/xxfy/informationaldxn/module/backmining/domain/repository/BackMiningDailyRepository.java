package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BackMiningDailyRepository extends BaseRepository<BackMiningDailyEntity>, JpaRepository<BackMiningDailyEntity, Long> {
    // 通过工作面以及日期获取日报信息
    BackMiningDailyEntity findAllByBackMiningFaceAndDailyTime(BackMiningFaceEntity backMiningFaceEntity, Date dailyTime);

    // 通过工作面获取日报列表
    List<BackMiningDailyEntity> findAllByBackMiningFace(BackMiningFaceEntity backMiningFaceEntity);

    // 统计某工作面月累计进尺&月累计产煤
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo(sum(d.totalDoneLength),sum(d.totalOutput)) " +
            " from BackMiningDailyEntity d where d.backMiningFace = :face and d.dailyTime between :start and :end")
    CmStatisticVo statisticDoneLengthAndOutPut(@Param("face")BackMiningFaceEntity face, @Param("start")Date start, @Param("end")Date end);

    // 统计某工作面月累计产煤
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.BackMiningStatisticVo(sum(d.totalOutput)) " +
            " from BackMiningDailyEntity d where d.backMiningFace = :face and d.dailyTime between :start and :end")
    CmStatisticVo statisticOutPut(@Param("face")BackMiningFaceEntity face, @Param("start")Date start, @Param("end")Date end);
}
