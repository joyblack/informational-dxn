package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackMiningFaceRepository extends BaseRepository<BackMiningFaceEntity>, JpaRepository<BackMiningFaceEntity, Long> {
    // back_mining_face_name = ?
    BackMiningFaceEntity findAllByBackMiningFaceName(String name);
    // id != and name = ?
    BackMiningFaceEntity findAllByBackMiningFaceNameAndIdNot(String name, Long id);
}
