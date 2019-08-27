package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DrivingFaceRepository extends BaseRepository<DrivingFaceEntity>, JpaRepository<DrivingFaceEntity, Long> {
    // driving_face_name = ?
    DrivingFaceEntity findAllByDrivingFaceName(String drivingFaceName);

    // name = ? and id != ?
    DrivingFaceEntity findAllByDrivingFaceNameAndIdNot(String drivingFaceName, Long id);

    // belong_company = ?
    List<DrivingFaceEntity> findAllByBelongCompany(DepartmentEntity departmentEntity);

    // 通过日期获取分组列表
    @Query("select distinct(d.drivingFace) from DrivingDailyEntity d where d.dailyTime = :dailyTime and d.drivingFace.belongCompany = :belongCompany")
    List<DrivingFaceEntity> findAllByDailyTimeAndBelongCompany(@Param("dailyTime") Date dailyTime, @Param("belongCompany") DepartmentEntity belongCompany);


}
