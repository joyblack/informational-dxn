package com.joy.xxfy.informationaldxn.module.drill.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrillWorkRepository extends BaseRepository<DrillWorkEntity>, JpaRepository<DrillWorkEntity, Long> {
    DrillWorkEntity findFirstByDrillWorkName(String drillWorkName);

    DrillWorkEntity findFirstByDrillWorkNameAndIdNot(String drillWorkName, Long id);

    // 获取属于某平台的所有钻孔工作: belong_company_id = ?
    List<DrillWorkEntity> findAllByBelongCompany(DepartmentEntity company);

    @Query("select distinct(d.drillWork) from DrillDailyEntity d where d.dailyTime = :dailyTime and d.drillWork.belongCompany = :belongCompany")
    List<DrillWorkEntity> findAllByDistinctBelongCompanyAndDailyTime(@Param("belongCompany") DepartmentEntity belongCompany, @Param("dailyTime")Date dailyTime);

    /**
     * 统计工作完成进度信息
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo(w.drillWorkName, w.totalDoneLength, w.totalLength) from DrillWorkEntity w where w.belongCompany = :belongCompany")
    List<WorkProgressVo> getWorkProgress(@Param("belongCompany") DepartmentEntity belongCompany);
}
