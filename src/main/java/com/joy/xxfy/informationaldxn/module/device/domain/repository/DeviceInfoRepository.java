package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DeviceInfoRepository extends BaseRepository<DeviceInfoEntity>, JpaRepository<DeviceInfoEntity, Long> {
    // 平台限定：获取同名设备信息
    DeviceInfoEntity findFirstByBelongCompanyAndDeviceName(DepartmentEntity company, String deviceName);

    // 获取一个具有指定类型的设备信息
    DeviceInfoEntity findFirstByDeviceCategory(DeviceCategoryEntity deviceCategoryEntity);

    // 获取需要提示维保的设备信息
    @Query("select d from DeviceInfoEntity d where d.tipStartTime is not null and d.tipStartTime <= :now ")
    List<DeviceInfoEntity> getApproachMaintain(@Param("now") Date now);
}
