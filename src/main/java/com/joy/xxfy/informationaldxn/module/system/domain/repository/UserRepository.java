package com.joy.xxfy.informationaldxn.module.system.domain.repository;

import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends BaseRepository<UserEntity>, JpaRepository<UserEntity, Long> {

    /**
     * 根据登录名和密码获取用户信息
     */
    UserEntity findFirstByLoginNameAndPassword(String loginName,String password);

    /**
     * 根据登录名获取用户信息
     */
    UserEntity findFirstByLoginName(String loginName);
}
