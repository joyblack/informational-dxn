package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BackMiningDailyDetailRepository extends BaseRepository<BackMiningDailyDetailEntity>, JpaRepository<BackMiningDailyDetailEntity, Long> {
    // 获取日报详情信息
    List<BackMiningDailyDetailEntity> findAllByBackMiningDaily(BackMiningDailyEntity backMiningDailyEntity);

    // 更新日报关联的详细信息删除状态
    @Modifying
    @Query("update BackMiningDailyDetailEntity d set isDelete = :isDelete where d.backMiningDaily = :daily")
    void updateIsDeleteByBackMiningDaily(@Param("daily") BackMiningDailyEntity daily, @Param("isDelete") boolean isDelete);

    // 日报、队伍、同班次
    BackMiningDailyDetailEntity findAllByBackMiningDailyAndTeamAndShifts(BackMiningDailyEntity backMiningDailyEntity,
                                                                          DepartmentEntity departmentEntity,
                                                                          DailyShiftEnum shifts);

    // 统计某个工作面的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from BackMiningDailyDetailEntity d where d.backMiningDaily.backMiningFace = :backMiningFace")
    SumBackMiningDailyDetailVo aggDailyDetail(@Param("backMiningFace") BackMiningFaceEntity backMiningFace);


    // 统计某个日报的总已处理长度、总产量以及总人数
    @Query("select new com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo(sum(d.doneLength), sum(d.output), sum(d.peopleNumber)) from BackMiningDailyDetailEntity d where d.backMiningDaily = :daily")
    SumBackMiningDailyDetailVo aggDailyDetail(@Param("daily") BackMiningDailyEntity daily);


}
