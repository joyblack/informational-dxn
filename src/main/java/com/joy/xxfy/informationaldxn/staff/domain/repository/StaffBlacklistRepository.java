package com.joy.xxfy.informationaldxn.staff.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffBlacklistEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffInjuryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffBlacklistRepository extends BaseRepository<StaffBlacklistEntity>, JpaRepository<StaffBlacklistEntity, Long> {
    /**
     * 通过个人信息查找黑名单信息
     */
    StaffBlacklistEntity findAllByStaffPersonal(StaffPersonalEntity staffPersonalEntity);
}
