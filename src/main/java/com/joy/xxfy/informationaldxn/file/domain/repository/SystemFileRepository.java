package com.joy.xxfy.informationaldxn.file.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.file.domain.entity.SystemFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemFileRepository extends BaseRepository<SystemFileEntity>, JpaRepository<SystemFileEntity, Long> {

}
