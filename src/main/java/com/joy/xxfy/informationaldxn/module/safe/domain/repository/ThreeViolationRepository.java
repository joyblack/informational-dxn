package com.joy.xxfy.informationaldxn.module.safe.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.entity.SafeInspectionEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.entity.ThreeViolationEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ThreeViolationRepository extends BaseRepository<ThreeViolationEntity>, JpaRepository<ThreeViolationEntity, Long> {

}
