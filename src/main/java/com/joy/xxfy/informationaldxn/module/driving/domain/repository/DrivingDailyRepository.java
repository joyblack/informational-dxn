package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrivingStatisticVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrivingDailyRepository extends BaseRepository<DrivingDailyEntity>, JpaRepository<DrivingDailyEntity, Long> {
    // 通过掘进工作面以及日期获取掘进日报(不会有多个煤矿操作，因此同一个工作面同一日期必定只有一个日报信息)
    DrivingDailyEntity findAllByDrivingFaceAndDailyTime(DrivingFaceEntity drivingFaceEntity, Date dailyTime);

    // 通过掘进工作面信息获取日报信息
    List<DrivingDailyEntity> findAllByDrivingFace(DrivingFaceEntity drivingFaceEntity);

    // 统计某工作面月累计进尺&月累计产煤
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo(sum(d.totalDoneLength),sum(d.totalOutput)) " +
            " from DrivingDailyEntity d where d.drivingFace = :face and d.dailyTime between :start and :end")
    CmStatisticVo statisticDoneLengthAndOutPut(@Param("face") DrivingFaceEntity drivingFace, @Param("start")Date start, @Param("end")Date end);


    // 统计某工作面月累计进尺&月累计产煤
    @Query("select new com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrivingStatisticVo(sum(d.totalDoneLength)) " +
            " from DrivingDailyEntity d where d.drivingFace = :face and d.dailyTime between :start and :end")
    DrivingStatisticVo statisticDoneLength(@Param("face") DrivingFaceEntity drivingFace, @Param("start")Date start, @Param("end")Date end);
}
