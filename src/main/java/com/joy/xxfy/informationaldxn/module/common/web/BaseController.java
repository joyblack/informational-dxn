package com.joy.xxfy.informationaldxn.module.common.web;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.TokenUtil;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    // 获取登陆账户的信息
    public UserEntity getLoginUser(HttpServletRequest request){
        try{
            return TokenUtil.getUser(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new JoyException(Notice.USER_NOT_LOGIN);
        }
    }
}
