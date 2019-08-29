package com.joy.xxfy.informationaldxn.module.document.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.BorrowEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.LicenceEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.LicenceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LicenceRepository extends BaseRepository<LicenceEntity>, JpaRepository<LicenceEntity, Long> {
    // 所属平台 and 证件类型
    LicenceEntity findFirstByBelongCompanyAndLicenceType(DepartmentEntity belongCompany, LicenceTypeEnum licenceType);
    // 所属平台
    List<LicenceEntity> findAllByBelongCompany(DepartmentEntity belongCompany);
}
