package com.joy.xxfy.informationaldxn.module.system.web;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.TestReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;



    /**
     * 添加
     */
    @PostMapping(
            value = "add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return userService.add(user);
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return userService.update(user);
        }
    }

    /**
     * 获取
     */
    @PostMapping(
            value = "get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return userService.get(req.getId());
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult delete(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return userService.delete(req.getId());
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid BasePageReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return userService.getPagerList(req);
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid BasePageReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return userService.getAllList(req);
        }
    }
}