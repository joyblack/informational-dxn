package com.joy.xxfy.informationaldxn.module.statistic.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.statistic.service.StatisticLengthService;
import com.joy.xxfy.informationaldxn.module.statistic.service.StatisticOutputService;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("statistic-length")
@RestController
public class StatisticLengthController extends BaseController {

    @Autowired
    private StatisticLengthService statisticLengthService;

    /**
     * 今日进尺
     */
    @RequestMapping(value = "/getToday")
    public JoyResult getToday( HttpServletRequest request) {
        return statisticLengthService.getToday(getLoginUser(request));
    }

    /**
     * 最近15日：进尺
     */
    @RequestMapping(value = "/getNear15Day")
    public JoyResult getNear15Day( HttpServletRequest request) {
        return statisticLengthService.getNear15Day(getLoginUser(request));
    }

    /**
     * 本月累计掘进回采
     */
    @RequestMapping(value = "/getThisMonth")
    public JoyResult getThisMonth( HttpServletRequest request) {
        return statisticLengthService.getThisMonth(getLoginUser(request));
    }

    /**
     * 月度掘进回采趋势：进尺
     */
    @RequestMapping(value = "/getEveryMonth")
    public JoyResult getEveryMonth( HttpServletRequest request) {
        return statisticLengthService.getEveryMonth(getLoginUser(request));
    }

}
