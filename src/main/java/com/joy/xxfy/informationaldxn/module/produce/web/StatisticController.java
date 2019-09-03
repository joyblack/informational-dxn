package com.joy.xxfy.informationaldxn.module.produce.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.produce.service.CpStatisticService;
import com.joy.xxfy.informationaldxn.module.produce.service.StatisticService;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("produce-statistic")
@RestController
public class StatisticController extends BaseController {
    @Autowired
    private StatisticService statisticService;
    /**
     * 今日产煤
     */
    @RequestMapping(value = "/getTodayOutput")
    public JoyResult getTodayOutput( HttpServletRequest request) {
        return statisticService.getTodayOutput(getLoginUser(request));
    }

    /**
     * 最近15日：产煤
     */
    @RequestMapping(value = "/getNear15DayOutput")
    public JoyResult getNear15DayOutput( HttpServletRequest request) {
        return statisticService.getNear15DayOutput(getLoginUser(request));
    }

    /**
     * 本月累计产煤
     */
    @RequestMapping(value = "/getThisMonthOutput")
    public JoyResult getThisMonthOutput( HttpServletRequest request) {
        return statisticService.getThisMonthOutput(getLoginUser(request));
    }

    /**
     * 月度产煤趋势：产煤
     */
    @RequestMapping(value = "/getEveryMonthOutput")
    public JoyResult getEveryMonthOutput( HttpServletRequest request) {
        return statisticService.getEveryMonthOutput(getLoginUser(request));
    }


    /**
     * 今日进尺
     */
    @RequestMapping(value = "/getTodayLength")
    public JoyResult getTodayLength( HttpServletRequest request) {
        return statisticService.getTodayLength(getLoginUser(request));
    }

    /**
     * 最近15日：进尺
     */
    @RequestMapping(value = "/getNear15DayLength")
    public JoyResult getNear15DayLength( HttpServletRequest request) {
        return statisticService.getNear15DayLength(getLoginUser(request));
    }

    /**
     * 月度掘进回采趋势：进尺
     */
    @RequestMapping(value = "/getEveryMonthDoneLength")
    public JoyResult getEveryMonthDoneLength( HttpServletRequest request) {
        return statisticService.getEveryMonthLength(getLoginUser(request));
    }


}
