package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffShiftRepository extends BaseRepository<StaffShiftEntity>, JpaRepository<StaffShiftEntity, Long> {
    /**
     * 通过个人信息查找调动信息
     */
    StaffShiftEntity findAllByStaffPersonal(StaffPersonalEntity staffPersonalEntity);
}
