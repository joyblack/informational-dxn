package com.joy.xxfy.informationaldxn.module.document.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository  extends BaseRepository<FileEntity>, JpaRepository<FileEntity, Long> {
    // 父文件夹ID并且是文件夹
    FileEntity findAllByIdAndIsFolder(Long id, Boolean isFolder);

    // 查找同名文件
    FileEntity findFirstByBelongCompanyAndParentIdAndFileName(DepartmentEntity belongCompany, Long parentId, String filename);
}
