package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrillWorkRepository extends BaseRepository<DrillWorkEntity>, JpaRepository<DrillWorkEntity, Long> {
    DrillWorkEntity findAllByDrillWorkName(String drillWorkName);
    DrillWorkEntity findAllByDrillWorkNameAndIdNot(String drillWorkName, Long id);

    // 获取属于某平台的所有钻孔工作: belong_company_id = ?
    List<DrillWorkEntity> findAllByBelongCompany(DepartmentEntity company);
}
