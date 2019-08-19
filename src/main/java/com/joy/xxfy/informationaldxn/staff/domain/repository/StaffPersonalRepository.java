package com.joy.xxfy.informationaldxn.staff.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffPersonalRepository extends BaseRepository<StaffPersonalEntity>, JpaRepository<StaffPersonalEntity, Long> {
    // id_number = ?
    StaffPersonalEntity findAllByIdNumber(String idNumber);

    // username = ?
    List<StaffPersonalEntity> findAllByUsername(String username);
}
