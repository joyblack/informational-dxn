package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends BaseRepository<DeviceInfoEntity>, JpaRepository<DeviceInfoEntity, Long> {

}
