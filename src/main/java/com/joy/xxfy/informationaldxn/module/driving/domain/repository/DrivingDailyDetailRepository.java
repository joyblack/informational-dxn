package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrivingDailyDetailRepository extends BaseRepository<DrivingDailyDetailEntity>, JpaRepository<DrivingDailyDetailEntity, Long> {
    // 获取日报详情信息
    List<DrivingDailyDetailEntity> findAllByDrivingDaily(DrivingDailyEntity drivingDaily);

    // 更新日报关联的详细信息删除状态
    @Modifying
    @Query("update DrivingDailyDetailEntity d set isDelete = :isDelete where d.drivingDaily = :drivingDaily")
    void updateIsDeleteByDrivingDaily(@Param("drivingDaily") DrivingDailyEntity drivingDaily, @Param("isDelete") boolean isDelete);

    // 日报、队伍、同班次
    DrivingDailyDetailEntity findAllByDrivingDailyAndDrivingTeamAndShifts(DrivingDailyEntity drivingDailyEntity,
                                                                          DepartmentEntity departmentEntity,
                                                                          DailyShiftEnum shifts);
    // 统计某个工作面的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from DrivingDailyDetailEntity d where d.drivingDaily.drivingFace = :drivingFace")
    SumDrivingDailyDetailVo aggDailyDetail(@Param("drivingFace") DrivingFaceEntity drivingFace);

    // 统计某个日报的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from DrivingDailyDetailEntity d where d.drivingDaily = :drivingDaily")
    SumDrivingDailyDetailVo aggDailyDetail(@Param("drivingDaily") DrivingDailyEntity drivingDailyEntity);

}
