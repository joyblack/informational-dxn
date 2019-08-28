package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceMaintainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceMaintainRepository extends BaseRepository<DeviceMaintainEntity>, JpaRepository<DeviceMaintainEntity, Long> {
    // 通过设备信息获取一条维修状态信息
    DeviceMaintainEntity findFirstByDeviceInfo(DeviceInfoEntity deviceInfoEntity);

}
