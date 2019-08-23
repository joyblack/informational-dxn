package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrillDailyRepository extends BaseRepository<DrillDailyEntity>, JpaRepository<DrillDailyEntity, Long> {

}
