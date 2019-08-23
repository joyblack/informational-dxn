package com.joy.xxfy.informationaldxn.module.staff.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalOneInchPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffPersonalOneInchPhotoRepository extends BaseRepository<StaffPersonalOneInchPhotoEntity>, JpaRepository<StaffPersonalOneInchPhotoEntity, Long> {

}
