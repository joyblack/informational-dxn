package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DrivingDailyRepository extends BaseRepository<DrivingDailyEntity>, JpaRepository<DrivingDailyEntity, Long> {
    // 通过掘进工作面以及日期获取掘进日报
    DrivingDailyEntity findAllByDrivingFaceAndDailyTime(DrivingFaceEntity drivingFaceEntity, Date dailyTime);
}
