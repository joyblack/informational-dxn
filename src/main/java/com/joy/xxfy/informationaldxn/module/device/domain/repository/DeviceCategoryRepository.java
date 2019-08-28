package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceCategoryRepository extends BaseRepository<DeviceCategoryEntity>, JpaRepository<DeviceCategoryEntity, Long> {

    // 通过（所属平台、父ID、类型名称）获取类型信息
    DeviceCategoryEntity findAllByBelongCompanyAndParentIdAndCategoryName(DepartmentEntity company, Long parentId, String categoryName);

    // 通过（所属平台、父ID）获取下一级子列表
    List<DeviceCategoryEntity> findAllByBelongCompanyAndParentId(DepartmentEntity company, Long parentId);

    // 根据父路径查询所有子节点
    List<DeviceCategoryEntity> findAllByBelongCompanyAndPathStartingWith(DepartmentEntity company,String path);

}