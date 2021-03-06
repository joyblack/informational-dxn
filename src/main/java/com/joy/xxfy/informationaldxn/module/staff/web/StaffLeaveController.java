package com.joy.xxfy.informationaldxn.module.staff.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.module.staff.service.StaffLeaveService;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffLeaveAddReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffLeaveGetListReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffLeaveUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("staff-leave")
public class StaffLeaveController extends BaseController {
    @Autowired
    private StaffLeaveService staffLeaveService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid StaffLeaveAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return staffLeaveService.add(req);
        }
    }

    /**
     * 修改
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid StaffLeaveUpdateReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return staffLeaveService.update(req);
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult delete(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return staffLeaveService.delete(req.getId());
        }
    }

    /**
     * 获取
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return staffLeaveService.get(req.getId());
        }
    }

    /**
     * 更新
     */
//    @PostMapping(
//            value = "/update",
//            produces = {"application/json;charset=UTF-8"})
//    public JoyResult update(@RequestBody @Valid StaffEntryEntity staffEntry, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
//        } else {
//
//            return staffLeaveService.update(staffEntry);
//        }
//    }

    /**
     * 获取分页数据
     */
    @PostMapping(
            value = "/getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid StaffLeaveGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return staffLeaveService.getPagerList(req,getLoginUser(request));
        }
    }

    /**
     * 获取所有数据
     */
    @PostMapping(
            value = "/getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid StaffLeaveGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return staffLeaveService.getAllList(req, getLoginUser(request));
        }
    }








}
