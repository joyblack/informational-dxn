package com.joy.xxfy.informationaldxn.module.document.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.BorrowEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BorrowRepository extends BaseRepository<BorrowEntity>, JpaRepository<BorrowEntity, Long> {

    // 所有超时未还的借阅记录全部更新为超时
    @Query("update BorrowEntity s set s.isOverTime = :isOverTime where s.deadTime < :now and s.returnStatus = :returnStatus")
    @Modifying
    void updateIsOvertTimeByNowAndReturnStatus(@Param("isOverTime") CommonYesEnum isOverTime,
                                                      @Param("now") Date now,
                                                      @Param("returnStatus") CommonYesEnum no);
}
