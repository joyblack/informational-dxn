package com.joy.xxfy.informationaldxn.login.service;

import com.joy.xxfy.informationaldxn.config.JwtParamConfig;
import com.joy.xxfy.informationaldxn.login.web.request.LoginReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.MD5Util;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.JwtUtil;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.Token;
import com.joy.xxfy.informationaldxn.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtParamConfig jwtParamConfig;

    public JoyResult login(LoginReq loginReq) {
        if (StringUtil.isEmpty(loginReq.getLoginName())) {
            return JoyResult.buildFailedResult("用户名不能为空");
        }
        if (StringUtil.isEmpty(loginReq.getPassword())) {
            return JoyResult.buildFailedResult("密码不能为空");
        }
        String md5Password = MD5Util.encode(loginReq.getPassword());
        // 登录名查询
        UserEntity user = userRepository.findAllByLoginName(loginReq.getLoginName());
        if(user != null){
            Map<String, Object> claims = new HashMap<String, Object>();
            claims.put(Token.USER.getName(), user);
            System.out.println(user);
            return JoyResult.buildSuccessResultWithData(JwtUtil.createJWT(claims, jwtParamConfig));
        }else{
            return JoyResult.buildFailedResult("登录名/手机号/身份证/与密码不匹配");
        }
    }


}
