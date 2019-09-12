package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffShiftRepository extends BaseRepository<StaffShiftEntity>, JpaRepository<StaffShiftEntity, Long> {
    /**
     * 通过个人信息查找调动信息
     */
    StaffShiftEntity findFirstByStaffPersonal(StaffPersonalEntity staffPersonalEntity);
}
