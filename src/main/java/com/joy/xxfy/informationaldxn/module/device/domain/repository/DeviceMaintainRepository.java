package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceMaintainEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceMaintainRepository extends BaseRepository<DeviceMaintainEntity>, JpaRepository<DeviceMaintainEntity, Long> {
    // 通过设备信息获取一条维修状态信息
    DeviceMaintainEntity findFirstByDeviceInfo(DeviceInfoEntity deviceInfoEntity);

    // 将设备的所有维保信息的维保情况
    @Modifying
    @Query("update DeviceMaintainEntity m set m.maintainStatus = :maintainStatus where m.deviceInfo = :deviceInfo")
    void updateCompletedByDeviceInfo(@Param("deviceInfo") DeviceInfoEntity deviceInfoEntity, @Param("maintainStatus") MaintainStatusEnum maintainStatus);

}
