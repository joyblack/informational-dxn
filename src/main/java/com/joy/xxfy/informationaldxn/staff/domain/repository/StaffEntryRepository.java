package com.joy.xxfy.informationaldxn.staff.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffEntryRepository extends BaseRepository<StaffEntryEntity>, JpaRepository<StaffEntryEntity, Long> {
    /**
     * 查找同部门同职位是否有重复的用户出现
     */
    StaffEntryEntity findAllByStaffPersonalAndDepartmentAndPosition(StaffPersonalEntity staffPersonalEntity,
                                                                    DepartmentEntity departmentEntity,
                                                                    PositionEntity positionEntity);
    /**
     * 查找同部门同职位是否有重复的用户出现,排除指定ID
     */
    StaffEntryEntity findAllByStaffPersonalAndDepartmentAndPositionAndIdNot(StaffPersonalEntity staffPersonal, DepartmentEntity department, PositionEntity position, Long id);
    /**
     * 通过个人信息查找所有入职信息
     */
    List<StaffEntryEntity> findAllByStaffPersonal(StaffPersonalEntity staffPersonalEntity);

    // 通过身份证查找所有入职信息
    @Query("from StaffEntryEntity s where s.staffPersonal.idNumber = :idNumber")
    List<StaffEntryEntity> findAllByIdNumber(@Param("idNumber") String idNumber);
}
