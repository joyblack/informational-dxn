package com.joy.xxfy.informationaldxn.module.resource.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.resource.domain.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends BaseRepository<ResourceEntity>, JpaRepository<ResourceEntity, Long> {

    // 根据parentId&name获取信息
    DepartmentEntity findAllByParentIdAndResourceName(Long parentId, String name);

    // 根据parentId&name&!id 获取部门信息
    DepartmentEntity findAllByParentIdAndResourceNameAndIdNot(Long parentId, String name, Long id);

    // 根据父节点ID获取子节点信息
    List<DepartmentEntity> findAllByParentId(Long parentId);

    // 根据父路径查询所有子节点
    List<DepartmentEntity> findAllByPathStartingWith(String path);

}
