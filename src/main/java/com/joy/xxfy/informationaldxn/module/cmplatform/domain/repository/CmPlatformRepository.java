package com.joy.xxfy.informationaldxn.module.cmplatform.domain.repository;

import com.joy.xxfy.informationaldxn.module.cmplatform.domain.entity.CmPlatformEntity;
import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmPlatformRepository extends BaseRepository<CmPlatformEntity>, JpaRepository<CmPlatformEntity, Long> {
    // Where cmName = #{cmName}
    CmPlatformEntity findAllByCmName(String cmName);
    // Where name = #{cmName} And id != #{id}
    CmPlatformEntity findAllByCmNameAndIdNot(String cmName, Long id);

}
