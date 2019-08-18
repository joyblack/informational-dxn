package com.joy.xxfy.informationaldxn.login.web;

import com.joy.xxfy.informationaldxn.login.service.LoginService;
import com.joy.xxfy.informationaldxn.login.web.request.LoginReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录名及密码登陆
     */
    @PostMapping(
            value = "/login",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult login(@RequestBody LoginReq loginReq) {
        return loginService.login(loginReq);
    }


}
