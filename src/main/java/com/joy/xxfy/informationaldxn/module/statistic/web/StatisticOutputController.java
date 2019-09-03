package com.joy.xxfy.informationaldxn.module.statistic.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.statistic.service.StatisticOutputService;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("statistic-output")
@RestController
public class StatisticOutputController extends BaseController {

    @Autowired
    private StatisticOutputService statisticOutputService;

    /**
     * 今日产煤
     */
    @RequestMapping(value = "/getToday")
    public JoyResult getToday(HttpServletRequest request) {
        return statisticOutputService.getToday(getLoginUser(request));
    }

    /**
     * 最近15日：产煤
     */
    @RequestMapping(value = "/getNear15Day")
    public JoyResult getNear15Day( HttpServletRequest request) {
        return statisticOutputService.getNear15Day(getLoginUser(request));
    }

    /**
     * 本月累计产煤
     */
    @RequestMapping(value = "/getThisMonth")
    public JoyResult getThisMonth( HttpServletRequest request) {
        return statisticOutputService.getThisMonth(getLoginUser(request));
    }

    /**
     * 月度产煤趋势：产煤
     */
    @RequestMapping(value = "/getEveryMonth")
    public JoyResult getEveryMonth( HttpServletRequest request) {
        return statisticOutputService.getEveryMonth(getLoginUser(request));
    }
}
