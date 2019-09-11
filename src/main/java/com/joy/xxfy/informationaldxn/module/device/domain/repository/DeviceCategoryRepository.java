package com.joy.xxfy.informationaldxn.module.device.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceCategoryRepository extends BaseRepository<DeviceCategoryEntity>, JpaRepository<DeviceCategoryEntity, Long> {

    /**
     * 通过（所属平台、父ID、类型名称）获取类型信息
     */
    DeviceCategoryEntity findFirstByBelongCompanyAndParentIdAndCategoryName(DepartmentEntity company, Long parentId, String categoryName);

    /**
     * 通过（所属平台、父ID）获取下一级子列表
     */
    List<DeviceCategoryEntity> findAllByBelongCompanyAndParentId(DepartmentEntity company, Long parentId);

    /**
     * 根据父路径查询所有子节点
     */
    List<DeviceCategoryEntity> findAllByBelongCompanyAndPathStartingWith(DepartmentEntity company,String path);

    /**
     * 根据父节点查询一个子节点信息
     */
    DeviceCategoryEntity findFirstByParentId(Long parentId);
}
