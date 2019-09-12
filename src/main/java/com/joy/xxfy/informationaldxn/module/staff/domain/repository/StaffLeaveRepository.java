package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffLeaveRepository extends BaseRepository<StaffLeaveEntity>, JpaRepository<StaffLeaveEntity, Long> {
    // staff_personal_id = ? order by create_time limit 1.
    StaffLeaveEntity findFirstByStaffPersonalOrderByCreateTimeDesc(StaffPersonalEntity staffPersonalEntity);

    // 查找一个职位相关的职工信息
    StaffLeaveEntity findFirstByPosition(PositionEntity position);

}
