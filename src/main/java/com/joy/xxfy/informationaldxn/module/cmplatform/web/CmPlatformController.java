package com.joy.xxfy.informationaldxn.module.cmplatform.web;

import com.joy.xxfy.informationaldxn.module.cmplatform.service.CmPlatformService;
import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.AddCmPlatformReq;
import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.GetCmPlatformListReq;
import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.UpdateCmPlatformReq;
import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePermissionReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.ChangePasswordReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
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

@RequestMapping("cm-platform")
@RestController
public class CmPlatformController extends BaseController {

    @Autowired
    private CmPlatformService cmPlatformService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid AddCmPlatformReq addRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.add(addRequest,getLoginUser(request));
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid UpdateCmPlatformReq updateUserReq, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.update(updateUserReq,getLoginUser(request));
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult delete(@RequestBody @Valid IdReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.delete(req.getId(),getLoginUser(request));
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
            return cmPlatformService.get(idRequest.getId(),getLoginUser(request));
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid GetCmPlatformListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.getAllList(req,getLoginUser(request));
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid GetCmPlatformListReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.getPagerList(req,getLoginUser(request));
        }
    }



    /**
     * 启用
     */
    @PostMapping(
            value = "/enable",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult enable(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.enable(idRequest.getId(),getLoginUser(request));
        }
    }

    /**
     * 启用
     */
    @PostMapping(
            value = "/disable",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult disable(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.disable(idRequest.getId(),getLoginUser(request));
        }
    }

    /**
     * 重置密码
     */
    @PostMapping(
            value = "/resetPassword",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult resetPassword(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.resetPassword(idRequest.getId(),getLoginUser(request));
        }
    }

    /**
     * 修改密码
     */
    @PostMapping(
            value = "/changePassword",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult changePassword(@RequestBody @Valid ChangePasswordReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.changePassword(req,getLoginUser(request));
        }
    }

    /**
     * 设置权限
     */
    @PostMapping(
            value = "/updatePermission",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult setPermission(@RequestBody @Valid BasePermissionReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.updatePermission(req,getLoginUser(request));
        }
    }
}
