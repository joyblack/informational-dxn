package com.joy.xxfy.informationaldxn.module.safe.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.entity.SafeInspectionEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.vo.RectificationStatusCountVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SafeInspectionRepository extends BaseRepository<SafeInspectionEntity>, JpaRepository<SafeInspectionEntity, Long> {
    // 所有未整改的记录若超过截止时间，则都设置为超时状态
    @Query("update SafeInspectionEntity s set s.isOverTime = :isOverTime where s.deadTime < :now and s.rectificationStatus = :rectificationStatus")
    @Modifying
    void updateIsOvertTimeByNowAndRectificationStatus(@Param("isOverTime") CommonYesEnum isOverTime,
                                @Param("now") Date now,
                                @Param("rectificationStatus")RectificationStatusEnum rectificationStatus);

    /**
     * 获取所有待提醒项，即当前时间大于提醒时间，且未完成的
     * rectificationStatus = No
     */
    @Query("select s from SafeInspectionEntity s where s.rectificationStatus = :rectificationStatus and s.tipStartTime <= :now and s.inspectCompany = :inspectCompany")
    List<SafeInspectionEntity> getAllApproach(@Param("rectificationStatus") RectificationStatusEnum rectificationStatus,
                                              @Param("now") Date now,
                                              @Param("inspectCompany") DepartmentEntity inspectCompany);

    /**
     * 统计本月，按是否整改进行数量的分组统计
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.safe.domain.vo.RectificationStatusCountVo(s.rectificationStatus,count(s.id)) from SafeInspectionEntity s where s.inspectCompany = :inspectCompany and concat(year(s.inspectTime), month(s.inspectTime))  = :month group by s.rectificationStatus")
    List<RectificationStatusCountVo> rectificationStatusCountByMonth(@Param("inspectCompany") DepartmentEntity inspectCompany, @Param("month") String month);
}
