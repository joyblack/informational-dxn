package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrillDailyDetailRepository extends BaseRepository<DrillDailyDetailEntity>, JpaRepository<DrillDailyDetailEntity, Long> {
    /**
     * 通过日报删除其关联的打钻信息（软）
     */
    @Modifying
    void deleteAllByDrillDaily(DrillDailyEntity drillDailyEntity);

    /**
     * 通过日报获取其关联的打钻信息
     */
    List<DrillDailyDetailEntity> findAllByDrillDaily(DrillDailyEntity drillDailyEntity);

    /**
     * 通过日报、钻孔信息获取具体的打钻信息，这个信息一定的是唯一的
     */
    DrillDailyDetailEntity findFirstByDrillDailyAndDrillHole(DrillDailyEntity drillDailyEntity, DrillHoleEntity drillHoleEntity);

    /**
     * 获取指定日期当天的所有关于该工作面的详情信息：通过工作面、日期
     */
    @Query("from DrillDailyDetailEntity d where d.drillDaily.drillWork = :drillWork and d.drillDaily.dailyTime = :dailyTime")
    List<DrillDailyDetailEntity> findAllByDrillWorkAndDailyTime(DrillWorkEntity drillWork, Date dailyTime);

    /**
     * 删除指定日期当天的所有关于该工作面的详情信息：通过工作面、日期
     */
    @Modifying
    void deleteAllByDrillDailyIn(List<DrillDailyEntity> drillDailies);
}
