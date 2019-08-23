package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrillDailyRepository extends BaseRepository<DrillDailyEntity>, JpaRepository<DrillDailyEntity, Long> {

}
