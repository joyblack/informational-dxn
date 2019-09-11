package com.joy.xxfy.informationaldxn.module.document.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.BorrowEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.ReturnStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BorrowRepository extends BaseRepository<BorrowEntity>, JpaRepository<BorrowEntity, Long> {
    /**
     * 所有超时未还的借阅记录全部更新为超时
     * 是否超时：No
     * 当前时间
     * 归还状态: NO
     */
    @Query("update BorrowEntity s set s.isOverTime = :isOverTimeNo where s.deadTime < :now and s.returnStatus = :returnStatusNo")
    @Modifying
    void updateIsOvertTimeByNowAndReturnStatus(@Param("isOverTimeNo") CommonYesEnum isOverTimeNo,
                                                      @Param("now") Date now,
                                                      @Param("returnStatusNo") ReturnStatusEnum returnStatusNo);
}
