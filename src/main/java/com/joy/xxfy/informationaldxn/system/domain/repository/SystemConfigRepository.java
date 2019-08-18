package com.joy.xxfy.informationaldxn.system.domain.repository;

import com.joy.xxfy.informationaldxn.cmplatform.domain.entity.CmPlatformEntity;
import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.system.domain.entity.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends BaseRepository<SystemConfigEntity>, JpaRepository<SystemConfigEntity, Long> {
    // 根据配置名称获取配置信息
    SystemConfigEntity findAllByConfigName(String configName);
}
