package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends BaseRepository<DeviceInfoEntity>, JpaRepository<DeviceInfoEntity, Long> {
    // 平台限定：获取同名设备信息
    DeviceInfoEntity findFirstByBelongCompanyAndDeviceName(DepartmentEntity company, String deviceName);

    // 获取一个具有指定类型的设备信息
    DeviceInfoEntity findFirstByDeviceCategory(DeviceCategoryEntity deviceCategoryEntity);

}
