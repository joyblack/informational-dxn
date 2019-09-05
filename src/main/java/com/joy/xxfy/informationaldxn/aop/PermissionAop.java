package com.joy.xxfy.informationaldxn.aop;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 权限验证拦截器
 */
@Component
@Aspect
public class PermissionAop {
    @Pointcut(value="execution(* com.joy.xxfy.informationaldxn.module.*.service.*Service.*(..)) && args(..,loginUser)",argNames="loginUser")
    public void executeInService(UserEntity loginUser){
    }



}
