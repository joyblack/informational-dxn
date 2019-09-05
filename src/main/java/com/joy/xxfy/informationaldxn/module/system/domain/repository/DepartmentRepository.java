package com.joy.xxfy.informationaldxn.module.system.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends BaseRepository<DepartmentEntity>, JpaRepository<DepartmentEntity, Long> {

    /**
     * 根据parentId&name获取部门信息
     */
    DepartmentEntity findAllByParentIdAndDepartmentName(Long parentId, String departmentName);

    /**
     * 根据parentId&departmentName&!id 获取部门信息
     */
    DepartmentEntity findAllByParentIdAndDepartmentNameAndIdNot(Long parentId, String departmentName, Long id);

    /**
     * 根据父节点ID获取子节点信息
     */
    List<DepartmentEntity> findAllByParentId(Long parentId);

    /**
     * 根据父路径查询所有子节点
     */
    List<DepartmentEntity> findAllByPathStartingWith(String path);

    /**
     * 更新所有相关的节点的路径
     */
    @Modifying
    @Query(value = "UPDATE all_department SET path = REPLACE(path, :oldPath, :newPath) WHERE path LIKE CONCAT('',:oldPath,'%')", nativeQuery = true)
    void updateAllDepartmentPath(@Param("oldPath") String oldPath, @Param("newPath")String newPath);
}
