package com.joy.xxfy.informationaldxn.module.pan.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.pan.domain.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository  extends BaseRepository<FileEntity>, JpaRepository<FileEntity, Long> {
    // 父文件夹ID并且是文件夹
    FileEntity findAllByIdAndIsFolder(Long id, Boolean isFolder);
}
