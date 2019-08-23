package com.joy.xxfy.informationaldxn.aop;

import com.joy.xxfy.informationaldxn.config.JwtParamConfig;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.JwtUtil;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.Token;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截
 */
//@Aspect
@Component
public class AuthorizeAspect {

    @Autowired
    private JwtParamConfig jwtParamConfig;

    @Pointcut("execution(public * com.joy.xxfy.informationaldxn.*.web.*Controller.*(..))"
            + "&&!execution(public * com.joy.xxfy.informationaldxn.module.login.web.LoginController.*(..))"
            + "&&!execution(public * com.joy.xxfy.informationaldxn.module.login..web.UserController.*(..))"
            + "&&!execution(public * com.joy.xxfy.informationaldxn.module.system.web.HeartController.*(..))"
           )
    public void auth() {
    }

    @Before("auth()")
    public void doAuth() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        final Object authHeader = request.getHeader(Token.AUTHORIZATION.getName());
        if (authHeader == null) {
            throw new JoyException(JoyResult.buildFailedResult(Notice.USER_NOT_LOGIN));
        }
        final Claims claims = JwtUtil.parseJWT(authHeader.toString(), jwtParamConfig.getBase64Security());
        if (claims == null) {
            throw new JoyException(Notice.USER_NOT_LOGIN);
        }
        request.setAttribute(Token.CLAIMS.getName(), claims);
        response.setHeader(Token.AUTHORIZATION.getName(), JwtUtil.createJWT(claims, jwtParamConfig));
    }
}
