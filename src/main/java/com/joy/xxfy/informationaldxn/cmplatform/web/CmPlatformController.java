package com.joy.xxfy.informationaldxn.cmplatform.web;

import com.joy.xxfy.informationaldxn.cmplatform.service.CmPlatformService;
import com.joy.xxfy.informationaldxn.cmplatform.web.req.AddCmPlatformReq;
import com.joy.xxfy.informationaldxn.cmplatform.web.req.GetCmPlatformListReq;
import com.joy.xxfy.informationaldxn.cmplatform.web.req.UpdateCmPlatformReq;
import com.joy.xxfy.informationaldxn.common.web.req.BasePermissionReq;
import com.joy.xxfy.informationaldxn.common.web.req.ChangePasswordReq;
import com.joy.xxfy.informationaldxn.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("cm-platform")
@RestController
public class CmPlatformController {

    @Autowired
    private CmPlatformService cmPlatformService;

    /**
     * 测试
     */
    @PostMapping(
            value = "/test")
    public JoyResult get() {
        return cmPlatformService.test();
    }

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid AddCmPlatformReq addRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.add(addRequest);
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid UpdateCmPlatformReq updateUserReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.update(updateUserReq);
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
            // copy
            return cmPlatformService.delete(req.getId());
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
            return cmPlatformService.get(idRequest.getId());
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid GetCmPlatformListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.getAllList(req);
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid GetCmPlatformListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.getPagerList(req);
        }
    }



    /**
     * 启用
     */
    @PostMapping(
            value = "/enable",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult enable(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.enable(idRequest.getId());
        }
    }

    /**
     * 启用
     */
    @PostMapping(
            value = "/disable",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult disable(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.disable(idRequest.getId());
        }
    }

    /**
     * 重置密码
     */
    @PostMapping(
            value = "/resetPassword",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult resetPassword(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.resetPassword(idRequest.getId());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping(
            value = "/changePassword",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult changePassword(@RequestBody @Valid ChangePasswordReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.changePassword(req);
        }
    }

    /**
     * 设置权限
     */
    @PostMapping(
            value = "/updatePermission",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult setPermission(@RequestBody @Valid BasePermissionReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return cmPlatformService.updatePermission(req);
        }
    }
}
