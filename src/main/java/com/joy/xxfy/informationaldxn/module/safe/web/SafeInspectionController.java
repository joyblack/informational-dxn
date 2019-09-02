package com.joy.xxfy.informationaldxn.module.safe.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.safe.service.SafeInspectionService;
import com.joy.xxfy.informationaldxn.module.safe.web.req.*;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("safe-inspection")
public class SafeInspectionController extends BaseController {

    @Autowired
    private SafeInspectionService safeInspectionService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid SafeInspectionAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.add(req);
        }
    }

    /**
     * 添加
     */
    @PostMapping(
            value = "/batchAdd",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult batchAdd(@RequestBody @Valid SafeInspectionBatchAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.add(req);
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid SafeInspectionUpdateReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.update(req);
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.delete(idRequest.getId());
        }
    }

    /**
     * 获取
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.get(idRequest.getId());
        }
    }

    /**
     * 获取全部列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid SafeInspectionGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.getAllList(req);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid SafeInspectionGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.getPagerList(req);
        }
    }

    /**
     * 变更整改状态
     */
    @RequestMapping(
            value = "changeRectificationStatus",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult changeRectificationStatus(@RequestBody @Valid ChangeRectificationStatusReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return safeInspectionService.changeRectificationStatus(req);
        }
    }

    /**
     * 获取临近截止日期的项目数量
     */
    @PostMapping(
            value = "/getApproachRectificationNum",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getApproachRectificationNum(HttpServletRequest req) {
        return safeInspectionService.getApproachRectificationNum(getLoginUser(req));
    }

    /**
     * 获取临近截止日期的项目信息
     */
    @PostMapping(
            value = "/getApproachRectification",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getApproachRectification(HttpServletRequest req) {
        return safeInspectionService.getApproachRectification(getLoginUser(req));
    }

    /**
     * 获取本月状态统计信息
     */
    @PostMapping(
            value = "/getThisMonthStatusCount",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getThisMonthStatusCount(HttpServletRequest req) {
        return safeInspectionService.getThisMonthStatusCount(getLoginUser(req));
    }

}
