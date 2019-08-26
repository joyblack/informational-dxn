package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DrivingDailyRepository extends BaseRepository<DrivingDailyEntity>, JpaRepository<DrivingDailyEntity, Long> {
    // 通过掘进工作面以及日期获取掘进日报(不会有多个煤矿操作，因此同一个工作面同一日期必定只有一个日报信息)
    DrivingDailyEntity findAllByDrivingFaceAndDailyTime(DrivingFaceEntity drivingFaceEntity, Date dailyTime);

    // 通过掘进工作面信息获取日报信息
    List<DrivingDailyEntity> findAllByDrivingFace(DrivingFaceEntity drivingFaceEntity);
}
