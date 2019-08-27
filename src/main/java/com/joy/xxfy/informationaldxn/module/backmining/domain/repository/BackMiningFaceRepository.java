package com.joy.xxfy.informationaldxn.module.backmining.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackMiningFaceRepository extends BaseRepository<BackMiningFaceEntity>, JpaRepository<BackMiningFaceEntity, Long> {
    // back_mining_face_name = ?
    BackMiningFaceEntity findAllByBackMiningFaceName(String name);
    // id != and name = ?
    BackMiningFaceEntity findAllByBackMiningFaceNameAndIdNot(String name, Long id);

    // belong_company_id = ?
    List<BackMiningFaceEntity> findAllByBelongCompany(DepartmentEntity company);
}
