package com.joy.xxfy.informationaldxn.module.staff.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends BaseRepository<PositionEntity>, JpaRepository<PositionEntity, Long> {
    // 根据职位名称查询职位信息
    PositionEntity findFirstByPositionName(String positionName);
    // 根据名称并不等于ID查询职位信息
    PositionEntity findFirstByPositionNameAndIdNot(String positionName, Long id);
}
