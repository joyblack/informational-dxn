package com.joy.xxfy.informationaldxn.module.produce.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.produce.service.CmStatisticService;
import com.joy.xxfy.informationaldxn.module.produce.web.req.SetRemarkReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryGetListReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
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

@RequestMapping("produce-cm-statistic")
@RestController
public class CmStatisticController extends BaseController {
    @Autowired
    private CmStatisticService cmStatisticService;
    @PostMapping(
            value = "/getStatisticData",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getStatisticData(@RequestBody @Valid TimeReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            UserEntity user = TokenUtil.getUser(request);
            return cmStatisticService.getData(user.getCompany(),req.getTime());
        }
    }

    /**
     * 设置备注信息
     */
    @PostMapping(
            value = "/setRemarks",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult setRemarks(@RequestBody @Valid SetRemarkReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            UserEntity user = TokenUtil.getUser(request);
            return cmStatisticService.setRemarks(req, user);
        }
    }


    /**
     * 导出
     */
    @RequestMapping(value = "exportData",produces = {"application/json;charset=UTF-8"})
    public void exportData(Date time, HttpServletRequest request, HttpServletResponse response) {
        if (time == null) {
            throw new JoyException(Notice.REQUEST_PARAMETER_IS_ERROR);
        } else {
            cmStatisticService.exportData(time, getLoginUser(request), request, response);
        }
    }


}
