package com.joy.xxfy.informationaldxn.publish.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理
 *
 * @author herim
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    public static final Log log = LogFactory.getLog(ExceptionHandler.class);

    private String view = "redirect:/client/404.html";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
                                         Exception ex) {
        HandlerMethod method = null;
        if (ex instanceof InvalidException) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/client/invalid.html");
            return mv;
        }
        if (ex instanceof JoyException) {
            JoyException joyException = (JoyException) ex;
            ModelAndView mv = new ModelAndView();
            MappingJackson2JsonView mjjv = new MappingJackson2JsonView();
            mjjv.setAttributesMap(joyException.getErrorMessage().convertToMap());
            mv.setView(mjjv);
            return mv;
        }
        // other types need print stack trace, it will be debug...
        ex.printStackTrace();
        if (ex instanceof DataIntegrityViolationException) {
            ModelAndView mv = new ModelAndView();
            MappingJackson2JsonView mjjv = new MappingJackson2JsonView();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("detailMessage", "");
            map.put("message", "数据正在使用中");
            map.put("state", false);
            map.put("code", HttpStatus.NOT_ACCEPTABLE.value());
            mjjv.setAttributesMap(map);
            mv.setView(mjjv);
            return mv;
        }
        if (ex instanceof JpaSystemException || ex instanceof InvalidDataAccessResourceUsageException) {
            ModelAndView mv = new ModelAndView();
            MappingJackson2JsonView mjjv = new MappingJackson2JsonView();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("detailMessage", ex.getMessage());
            map.put("message", "服务器繁忙，请稍后再试");
            map.put("state", false);
            map.put("data", null);
            map.put("code", HttpStatus.NOT_ACCEPTABLE.value());
            mjjv.setAttributesMap(map);
            mv.setView(mjjv);
            return mv;
        }
        if(ex instanceof  IllegalArgumentException){
            ModelAndView mv = new ModelAndView();
            MappingJackson2JsonView mjjv = new MappingJackson2JsonView();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("detailMessage", ex.getMessage());
            map.put("message", "参数解析错误...");
            map.put("state", false);
            map.put("data", null);
            map.put("code", HttpStatus.NOT_ACCEPTABLE.value());
            mjjv.setAttributesMap(map);
            mv.setView(mjjv);
            return mv;
        }

        if (ex instanceof Exception) {
            ModelAndView mv = new ModelAndView();
            MappingJackson2JsonView mjjv = new MappingJackson2JsonView();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("detailMessage", ex.getMessage());
            map.put("message", "服务器异常...");
            map.put("state", false);
            map.put("data", null);
            map.put("code", HttpStatus.NOT_ACCEPTABLE.value());
            mjjv.setAttributesMap(map);
            mv.setView(mjjv);
            return mv;
        }
        log.error(ex.getMessage(), ex);
        return new ModelAndView(view);

    }
}
