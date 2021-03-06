package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrivingFaceRepository extends BaseRepository<DrivingFaceEntity>, JpaRepository<DrivingFaceEntity, Long> {
    // driving_face_name = ?
    DrivingFaceEntity findFirstByDrivingFaceName(String drivingFaceName);

    // driving_face_name like %?% and belong_company = ?
    DrivingFaceEntity findFirstByBelongCompanyAndDrivingFaceNameContaining(DepartmentEntity departmentEntity, String drivingFaceName);

    // name = ? and id != ?
    DrivingFaceEntity findFirstByDrivingFaceNameAndIdNot(String drivingFaceName, Long id);

    // belong_company = ?
    List<DrivingFaceEntity> findAllByBelongCompany(DepartmentEntity departmentEntity);

    /**
     * 获取在指定日期内拥有日报信息的工作面列表
     */
    @Query("select distinct(d.drivingFace) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany")
    List<DrivingFaceEntity> findAllByDailyTimeAndBelongCompany(@Param("dailyTime") Date dailyTime, @Param("belongCompany") DepartmentEntity belongCompany);


    /**
     * 统计工作完成进度信息
     */
    @Query("select new com.joy.xxfy.informationaldxn.module.common.domain.vo.WorkProgressVo(w.drivingFaceName, w.doneLength, w.totalLength) from DrivingFaceEntity w where w.belongCompany = :belongCompany")
    List<WorkProgressVo> getWorkProgress(@Param("belongCompany") DepartmentEntity belongCompany);


}
