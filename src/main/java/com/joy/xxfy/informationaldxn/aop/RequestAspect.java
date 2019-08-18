package com.joy.xxfy.informationaldxn.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

// Request and ResponseLogger.
@Component
@Aspect
public class RequestAspect {
    private static Logger log = LoggerFactory.getLogger(RequestAspect.class);


    @Around("within(com.joy.xxfy.informationaldxn.*.web.*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        // Get Request URI
        HttpServletRequest request = getRequest(joinPoint);
        log.info("----------------------------- 请求开始 -----------------------------");
        log.info("请求地址: {}", request.getRequestURI());
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("请求方法: {}.{}", method.getDeclaringClass().getName(),method.getName());

        log.info("args={}", joinPoint.getArgs());

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        log.info("返回结果: {}", JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue));
        log.info("耗费时间: {}ms", System.currentTimeMillis() - start);
        log.info("----------------------------- 请求结束 -----------------------------");
        return result;
    }

    // Get Http Request Object
    private HttpServletRequest getRequest(JoinPoint point){
        Object[] args = point.getArgs();
        for (Object object : args) {
            if(object instanceof HttpServletRequest) {
                return (HttpServletRequest) object;
            }
        }
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
}
