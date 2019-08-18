package com.joy.xxfy.informationaldxn.staff.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffPersonalRepository extends BaseRepository<StaffPersonalEntity>, JpaRepository<StaffPersonalEntity, Long> {
    StaffPersonalEntity findAllByIdNumber(String idNumber);
}
