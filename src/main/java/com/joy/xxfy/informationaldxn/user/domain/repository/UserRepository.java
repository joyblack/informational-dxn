package com.joy.xxfy.informationaldxn.user.domain.repository;

import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends BaseRepository<UserEntity>, JpaRepository<UserEntity, Long> {

    // 根据登录名获取用户信息
    UserEntity findAllByLoginName(String loginName);
}
