package com.joy.xxfy.informationaldxn.module.document.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.BorrowEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends BaseRepository<BorrowEntity>, JpaRepository<BorrowEntity, Long> {

}
