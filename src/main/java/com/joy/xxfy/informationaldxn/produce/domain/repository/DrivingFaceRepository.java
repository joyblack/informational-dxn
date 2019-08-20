package com.joy.xxfy.informationaldxn.produce.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingFaceRepository extends BaseRepository<DrivingFaceEntity>, JpaRepository<DrivingFaceEntity, Long> {
    // driving_face_name = ?
    DrivingFaceEntity findAllByDrivingFaceName(String drivingFaceName);
}
