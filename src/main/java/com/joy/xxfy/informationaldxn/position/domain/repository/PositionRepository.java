package com.joy.xxfy.informationaldxn.position.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.position.domain.entity.PositionEntity;
import com.joy.xxfy.informationaldxn.user.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionRepository extends BaseRepository<PositionEntity>, JpaRepository<PositionEntity, Long> {
    // 根据职位名称查询职位信息
    PositionEntity findAllByPositionName(String positionName);

    // 根据名称并不等于ID查询职位信息
    PositionEntity findAllByPositionNameAndIdNot(String positionName, Long id);

}
