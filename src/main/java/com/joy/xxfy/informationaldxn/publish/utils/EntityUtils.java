package com.joy.xxfy.informationaldxn.publish.utils;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;

import java.util.Date;

public class EntityUtils {

    private static boolean isDelete = false;

    /**
     * 初始化entity，配置初始信息
     */
    public static <T extends BaseEntity>  T initialBasicInfo(T t){
        t.setIsDelete(isDelete);
        t.setUpdateTime(new Date());
        t.setCreateTime(new Date());
        return t;
    }

    /**
     * 初始化entity，配置更新信息
     */
    public static <T extends BaseEntity>  T initialBasicUpdateInfo(T t){
        t.setUpdateTime(new Date());
        return t;
    }
}
