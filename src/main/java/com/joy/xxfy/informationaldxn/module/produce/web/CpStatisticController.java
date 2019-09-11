package com.joy.xxfy.informationaldxn.module.produce.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.produce.service.CpStatisticService;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@RequestMapping("produce-cp-statistic")
@RestController
public class CpStatisticController extends BaseController {
    @Autowired
    private CpStatisticService cpStatisticService;
    /**
     * 添加
     */
    @PostMapping(
            value = "/getStatisticData",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getStatisticData(@RequestBody @Valid TimeReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return cpStatisticService.getData(getLoginUser(request),req.getTime());
        }
    }

    /**
     * 导出
     */
    @RequestMapping(value = "exportData")
    public void exportData(@DateTimeFormat(pattern = "yyyy-MM-dd") Date time, HttpServletRequest request, HttpServletResponse response) {
        if (time == null) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            cpStatisticService.exportData(time, getLoginUser(request), request, response);
        }
    }



}
