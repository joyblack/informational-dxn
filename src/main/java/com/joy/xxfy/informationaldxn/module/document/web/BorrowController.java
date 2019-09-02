package com.joy.xxfy.informationaldxn.module.document.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.device.service.DeviceInfoService;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoAddReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoChangeStatusReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoGetListReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoUpdateReq;
import com.joy.xxfy.informationaldxn.module.document.service.BorrowService;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowAddReq;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowChangeStatus;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowGetListReq;
import com.joy.xxfy.informationaldxn.module.document.web.req.BorrowUpdateReq;
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
@RequestMapping("document-borrow")
public class BorrowController extends BaseController {

    @Autowired
    private BorrowService borrowService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid BorrowAddReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.add(req, getLoginUser(request));
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid BorrowUpdateReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.update(req, getLoginUser(request));
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.delete(idRequest.getId(), getLoginUser(request));
        }
    }

    /**
     * 获取
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.get(idRequest.getId(), getLoginUser(request));
        }
    }

    /**
     * 获取全部列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid BorrowGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.getAllList(req, getLoginUser(request));
        }
    }

    /**
     * 分页
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid BorrowGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.getPagerList(req, getLoginUser(request));
        }
    }

    /**
     * 变更整改状态
     */
    @RequestMapping(
            value = "changeReturnStatus",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult changeReturnStatus(@RequestBody @Valid BorrowChangeStatus req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return borrowService.changeReturnStatus(req, getLoginUser(request));
        }
    }

    /**
     * 获取到底未归还信息数
     */
    @PostMapping(
            value = "/getNotReturnNum",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getNotReturnNum(HttpServletRequest req) {
        return borrowService.getNotReturnNum(getLoginUser(req));
    }

    /**
     * 获取到底未归还信息
     */
    @PostMapping(
            value = "/getNotReturn",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getNotReturn(HttpServletRequest req) {
        return borrowService.getNotReturn(getLoginUser(req));
    }


}
