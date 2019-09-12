package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffInjuryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffInjuryRepository extends BaseRepository<StaffInjuryEntity>, JpaRepository<StaffInjuryEntity, Long> {

}
