package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BackMiningFaceRepository extends BaseRepository<BackMiningFaceEntity>, JpaRepository<BackMiningFaceEntity, Long> {
    // back_mining_face_name = ?
    BackMiningFaceEntity findAllByBackMiningFaceName(String name);
    // id != and name = ?
    BackMiningFaceEntity findAllByBackMiningFaceNameAndIdNot(String name, Long id);

    // belong_company_id = ?
    List<BackMiningFaceEntity> findAllByBelongCompany(DepartmentEntity company);

    @Query("select distinct(d.backMiningFace) from BackMiningDailyEntity d where d.dailyTime = :dailyTime and d.backMiningFace.belongCompany = :belongCompany")
    List<BackMiningFaceEntity> findAllByDailyTimeAndBelongCompany(@Param("dailyTime") Date dailyTime, @Param("belongCompany") DepartmentEntity company);

    /**
     * 统计工作完成进度信息
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo(w.backMiningFaceName, w.doneLength, w.slopeLength) from BackMiningFaceEntity w where w.belongCompany = :belongCompany")
    List<WorkProgressVo> getWorkProgress(@Param("belongCompany") DepartmentEntity belongCompany);
}
