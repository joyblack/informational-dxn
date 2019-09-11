package com.joy.xxfy.informationaldxn.module.document.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository  extends BaseRepository<FileEntity>, JpaRepository<FileEntity, Long> {
    // 父文件夹ID并且是文件夹
    FileEntity findFirstByIdAndIsFolder(Long id, Boolean isFolder);

    // 查找同名文件
    FileEntity findFirstByPermissionTypeAndBelongCompanyAndParentIdAndFileName(PermissionTypeEnum permissionType, DepartmentEntity belongCompany, Long parentId, String filename);

    /**
     * 获取文件树结构（只要文件夹）
     */
    List<FileEntity> findAllByPathStartingWithAndBelongCompanyAndIsFolderAndPermissionType(String path, DepartmentEntity belongCompany, Boolean isFolder,PermissionTypeEnum permissionType);
}
