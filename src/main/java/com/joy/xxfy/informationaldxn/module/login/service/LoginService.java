package com.joy.xxfy.informationaldxn.module.login.service;

import com.joy.xxfy.informationaldxn.config.JwtParamConfig;
import com.joy.xxfy.informationaldxn.module.login.web.request.LoginReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.MD5Util;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.JwtUtil;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.Token;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Transactional
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
        UserEntity user = userRepository.findAllByLoginNameAndPassword(loginReq.getLoginName(),md5Password);
        if(user != null){
            Map<String, Object> claims = new HashMap<String, Object>();
            claims.put(Token.USER.getName(), user);
            return JoyResult.buildSuccessResultWithData(JwtUtil.createJWT(claims, jwtParamConfig));
        }else{
            return JoyResult.buildFailedResult("登录名/手机号/身份证/与密码不匹配");
        }
    }


}
