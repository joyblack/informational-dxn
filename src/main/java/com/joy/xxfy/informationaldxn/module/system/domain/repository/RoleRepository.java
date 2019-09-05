package com.joy.xxfy.informationaldxn.module.system.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends BaseRepository<RoleEntity>, JpaRepository<RoleEntity, Long> {
    /**
     * 平台内获取同名数据
     */
    RoleEntity findFirstByBelongCompanyAndRoleName(@Param("belongCompany")DepartmentEntity belongCompany, @Param("roleName") String roleName);


}
