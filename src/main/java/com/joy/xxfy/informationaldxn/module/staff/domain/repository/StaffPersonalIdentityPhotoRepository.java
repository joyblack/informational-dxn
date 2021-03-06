package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.entity.StaffPersonalIdentityPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffPersonalIdentityPhotoRepository extends BaseRepository<StaffPersonalIdentityPhotoEntity>, JpaRepository<StaffPersonalIdentityPhotoEntity, Long> {

}
