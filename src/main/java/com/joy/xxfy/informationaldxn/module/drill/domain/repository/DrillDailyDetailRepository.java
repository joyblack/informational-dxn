package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrillDailyDetailRepository extends BaseRepository<DrillDailyDetailEntity>, JpaRepository<DrillDailyDetailEntity, Long> {
    // 通过日报删除其关联的打钻信息（软）
    @Modifying
    @Query("update DrillDailyDetailEntity d set d.isDelete = :isDelete where d.drillDaily = :drillDaily")
    void updateIsDeleteByDrillDaily(@Param("drillDaily") DrillDailyEntity drillDailyEntity, @Param("isDelete") Boolean isDelete);

    // 通过日报获取其关联的打钻信息
    List<DrillDailyDetailEntity> findAllByDrillDaily(DrillDailyEntity drillDailyEntity);

}