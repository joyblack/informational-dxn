package com.joy.xxfy.informationaldxn.module.device.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.device.service.DeviceInfoService;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoAddReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoChangeStatusReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoGetListReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoUpdateReq;
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
@RequestMapping("device-info")
public class DeviceInfoController extends BaseController {

    @Autowired
    private DeviceInfoService deviceInfoService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid DeviceInfoAddReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return deviceInfoService.add(req, getLoginUser(request));
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid DeviceInfoUpdateReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return deviceInfoService.update(req, getLoginUser(request));
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
            // copy
            return deviceInfoService.delete(idRequest.getId(), getLoginUser(request));
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
            // copy
            return deviceInfoService.get(idRequest.getId(), getLoginUser(request));
        }
    }

    /**
     * 获取全部列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid DeviceInfoGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return deviceInfoService.getAllList(req, getLoginUser(request));
        }
    }

    /**
     * 分页
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid DeviceInfoGetListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return deviceInfoService.getPagerList(req, getLoginUser(request));
        }
    }

    /**
     * 变更状态
     */
    @RequestMapping(
            value = "changeDeviceStatus",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult changeDeviceStatus(@RequestBody @Valid DeviceInfoChangeStatusReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return deviceInfoService.changeDeviceStatus(req, getLoginUser(request));
        }
    }

    /**
     *  是否存在同名设备
     */
    @RequestMapping(
            value = "whetherExistSameNameDevice",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult whetherExistSameNameDevice(@RequestBody @Valid DeviceInfoAddReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return deviceInfoService.whetherExistSameNameDevice(req, getLoginUser(request));
        }
    }

    /**
     * 获取临近截止维保日期的设备信息
     */
    @PostMapping(
            value = "/getApproachMaintainNum",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getApproachMaintainNum(HttpServletRequest req) {
        return deviceInfoService.getApproachMaintainNum(getLoginUser(req));
    }

    /**
     * 获取临近截止维保日期的设备信息
     */
    @PostMapping(
            value = "/getApproachMaintain",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getApproachMaintain(HttpServletRequest req) {
        return deviceInfoService.getApproachMaintain(getLoginUser(req));
    }
}
