package com.joy.xxfy.informationaldxn.staff.web;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.staff.domain.service.StaffPersonalService;
import com.joy.xxfy.informationaldxn.staff.web.req.IdNumberReq;
import com.joy.xxfy.informationaldxn.staff.web.req.UsernameReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("staff-personal")
public class StaffPersonalController {
    @Autowired
    private StaffPersonalService staffPersonalService;
    /**
     * 解析身份证信息
     */
    @RequestMapping(
            value = "explainIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult explainIdNumber(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.explainIdNumber(req.getIdNumber());
        }
    }

    /**
     * 获取个人信息(idNumber)
     */
    @RequestMapping(
            value = "getByIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByIdNumber(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.getPersonalByIdNumber(req.getIdNumber());
        }
    }

    /**
     * 获取个人信息(id)
     */
    @RequestMapping(
            value = "get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.get(req.getId());
        }
    }

    /**
     * 获取个人信息(username)
     */
    @RequestMapping(
            value = "getByUsername",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByUsername(@RequestBody @Valid UsernameReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.getByUsername(req.getUsername());
        }
    }

}
