package com.joy.xxfy.informationaldxn.aop;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.joy.xxfy.informationaldxn.module.common.enums.LimitUserTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.ResourceEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.ResourceRepository;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.Token;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.TokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.xmlbeans.UserType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

// Request and ResponseLogger.
@Component
@Aspect
public class PermissionAuthAop {


    private String projectPrefix = "/informational-dxn/v1/";

    @Autowired
    private ResourceRepository resourceRepository;


    @Around("within(com.joy.xxfy.informationaldxn.module.*.web.*)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;
        long start = System.currentTimeMillis();
        // Get Request URI
        HttpServletRequest request = getRequest(joinPoint);
        LogUtil.info("----------------------------- Request start -----------------------------");
        LogUtil.info("Request url: {}", request.getRequestURI());
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        LogUtil.info("请求方法: {}.{}", method.getDeclaringClass().getName(),method.getName());
        LogUtil.info("args={}", joinPoint.getArgs());
        UserEntity loginUser = getLoginUser(request);
        if(loginUser == null){
            LogUtil.info("用户信息: ");
            LogUtil.info("登陆状态：未登录");
            result = joinPoint.proceed();
        }else{
            LogUtil.info("登陆状态：已登录");
            LogUtil.info("登录名：{}", loginUser.getLoginName());
            LogUtil.info("用户类型：{}", loginUser.getUserType());
            LogUtil.info("用户权限：{}", loginUser.getPermissions());
            String resourceUrl = request.getRequestURI().replace(projectPrefix,"");
            LogUtil.info("资源URL：{}", resourceUrl);
            // 拥有权限才允许执行
            if(hasPermission(loginUser,resourceUrl)){
                result = joinPoint.proceed();
            }else {
                result = JoyResult.buildFailedResult(Notice.PERMISSION_FORBIDDEN);
            }
        }
        LogUtil.info("返回结果: {}", JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue));
        LogUtil.info("耗费时间: {}ms", System.currentTimeMillis() - start);
        LogUtil.info("----------------------------- 请求结束 -----------------------------");
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

    /**
     * 获取登陆用户
     */
    private UserEntity getLoginUser(HttpServletRequest request){
        try {
            Claims claims = (Claims) request.getAttribute(Token.CLAIMS.getName());
            return JSONUtil.toBean(JSONUtil.toJsonStr(claims.get(Token.USER.getName())), UserEntity.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检测用户是否拥有访问权限
     */
    public boolean hasPermission(UserEntity user, String requestUrl){
        if(StringUtil.isEmpty(requestUrl)){
            return true;
        }
        // 手动检测限制用户类型访问的资源列表
        if(!userTypePermissionCheck(user,requestUrl)){
            LogUtil.info("权限认证结果：禁止。原因：不允许类型为【{}】的用户访问资源【{}】", user.getUserType(), requestUrl);
            return false;
        }
        /**
         * 获取资源ID
         */
        ResourceEntity resource = resourceRepository.findFirstByResourceUrl(requestUrl);

        if(resource == null){
            LogUtil.info("权限认证结果：允许。原因：系统还未配置该资源的信息，允许访问.");
            return true;
        }else{

            // 检测资源:为空代表拥有所有权限
            if(StringUtil.isEmpty(user.getPermissions())){
                LogUtil.info("权限认证结果：允许。" +
                        "用户资源配置为空（未配置），允许访问");
                return true;
            }
            if(Arrays.asList(user.getPermissions().split("-")).contains(resource.getId().toString())){
                LogUtil.info("权限认证结果：用户资源配置了该权限，允许访问");
                return true;
            }
        }
        return false;
    }

    /**
     * 优先禁止，一些菜单项是不允许某类成员进入的
     */
    public boolean userTypePermissionCheck(UserEntity user, String requestUrl){
        /**
         * 煤矿不允许访问的列表
         * 生产管理：集团调度日报
         * 人事管理：入职审核
         * 设置： 创建煤矿平台
         */
        if(user.getUserType().equals(UserTypeEnum.CM_ADMIN)  && (
                   requestUrl.startsWith("produce-cp-statistic")
                || requestUrl.startsWith("staff-review")
                || requestUrl.startsWith("cm-platform")))
            return false;

        /**
         * 集团不允许访问的列表
         * 设备管理：所有
         * 生产管理：基础资料、日报、煤矿生产日报
         */
        if(user.getUserType().equals(UserTypeEnum.CP_ADMIN)  && (
                   requestUrl.startsWith("device-")
                || requestUrl.startsWith("produce-drill-")
                || requestUrl.startsWith("produce-driving-")
                || requestUrl.startsWith("produce-back-mining-")
                || requestUrl.startsWith("produce-cm-statistic")))
            return false;
        return true;
    }

}
