package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BackMiningDailyRepository extends BaseRepository<BackMiningDailyEntity>, JpaRepository<BackMiningDailyEntity, Long> {
    // 通过工作面以及日期获取日报信息
    BackMiningDailyEntity findAllByBackMiningFaceAndDailyTime(BackMiningFaceEntity backMiningFaceEntity, Date dailyTime);

    // 通过工作面获取日报列表
    List<BackMiningDailyEntity> findAllByBackMiningFace(BackMiningFaceEntity backMiningFaceEntity);
}
