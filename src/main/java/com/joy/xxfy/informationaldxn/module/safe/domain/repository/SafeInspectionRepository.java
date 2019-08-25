package com.joy.xxfy.informationaldxn.module.safe.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.entity.SafeInspectionEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface SafeInspectionRepository extends BaseRepository<SafeInspectionEntity>, JpaRepository<SafeInspectionEntity, Long> {
    // 所有未整改的记录若超过截止时间，则都设置为超时状态
    @Query("update SafeInspectionEntity s set s.isOverTime = :isOverTime where s.deadTime < :now and s.rectificationStatus = :rectificationStatus")
    @Modifying
    void updateIsOvertTimeByNowAndRectificationStatus(@Param("isOverTime") CommonYesEnum isOverTime,
                                @Param("now") Date now,
                                @Param("rectificationStatus")RectificationStatusEnum rectificationStatus);
}
