package com.joy.xxfy.informationaldxn.cmplatform.domain.repository;

import com.joy.xxfy.informationaldxn.cmplatform.domain.entity.CmPlatformEntity;
import com.joy.xxfy.informationaldxn.cmplatform.web.req.GetCmPlatformListReq;
import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CmPlatformRepository extends BaseRepository<CmPlatformEntity>, JpaRepository<CmPlatformEntity, Long> {
    // Where cmName = #{cmName}
    CmPlatformEntity findAllByCmName(String cmName);
    // Where name = #{cmName} And id != #{id}
    CmPlatformEntity findAllByCmNameAndIdNot(String cmName, Long id);

}
