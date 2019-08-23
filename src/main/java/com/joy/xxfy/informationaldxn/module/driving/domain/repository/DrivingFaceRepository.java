package com.joy.xxfy.informationaldxn.module.driving.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingFaceRepository extends BaseRepository<DrivingFaceEntity>, JpaRepository<DrivingFaceEntity, Long> {
    // driving_face_name = ?
    DrivingFaceEntity findAllByDrivingFaceName(String drivingFaceName);

    // name = ? and id != ?
    DrivingFaceEntity findAllByDrivingFaceNameAndIdNot(String drivingFaceName, Long id);
}
