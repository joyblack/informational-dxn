package com.joy.xxfy.informationaldxn.file.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.file.domain.entity.SystemFileEntity;
import com.joy.xxfy.informationaldxn.file.enums.UploadModuleEnums;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemFileRepository extends BaseRepository<SystemFileEntity>, JpaRepository<SystemFileEntity, Long> {
    // id = ? and upload_module = ?
    SystemFileEntity findAllByIdAndUploadModule(Long idNumberPhotoId, UploadModuleEnums staffIdNumberPhoto);
}
