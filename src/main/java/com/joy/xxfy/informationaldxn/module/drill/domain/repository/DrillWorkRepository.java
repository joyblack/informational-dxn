package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrillWorkRepository extends BaseRepository<DrillWorkEntity>, JpaRepository<DrillWorkEntity, Long> {
   DrillWorkEntity findAllByDrillWorkName(String drillWorkName);
   DrillWorkEntity findAllByDrillWorkNameAndIdNot(String drillWorkName, Long id);
}
