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
//    /**
//     * 添加
//     */
//    @RequestMapping(value = "/getTodayOutput")
//    public JoyResult getTodayOutput( HttpServletRequest request) {
//        return statisticService.getTodayOutput(getLoginUser(request));
//    }



}