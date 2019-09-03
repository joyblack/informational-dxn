package com.joy.xxfy.informationaldxn.module.statistic.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.statistic.service.StatisticDrillService;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("statistic-drill")
@RestController
public class StatisticDrillController extends BaseController {
    @Autowired
    private StatisticDrillService statisticDrillService;

    /**
     * 今日进尺
     */
    @RequestMapping(value = "/getToday")
    public JoyResult getToday(HttpServletRequest request) {
        return statisticDrillService.getToday(getLoginUser(request));
    }

    /**
     * 最近15日：进尺
     */
    @RequestMapping(value = "/getNear15Day")
    public JoyResult getNear15Day( HttpServletRequest request) {
        return statisticDrillService.getNear15Day(getLoginUser(request));
    }

    /**
     * 本月累计掘进回采
     */
    @RequestMapping(value = "/getThisMonth")
    public JoyResult getThisMonth( HttpServletRequest request) {
        return statisticDrillService.getThisMonth(getLoginUser(request));
    }

    /**
     * 月度掘进回采趋势：进尺
     */
    @RequestMapping(value = "/getEveryMonth")
    public JoyResult getEveryMonth( HttpServletRequest request) {
        return statisticDrillService.getEveryMonth(getLoginUser(request));
    }
}
