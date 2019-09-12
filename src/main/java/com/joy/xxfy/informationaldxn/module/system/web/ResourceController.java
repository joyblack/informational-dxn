package com.joy.xxfy.informationaldxn.module.system.web;

import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.system.service.ResourceService;
import com.joy.xxfy.informationaldxn.module.system.web.req.ResourceAddReq;
import com.joy.xxfy.informationaldxn.module.system.web.req.ResourceUpdateReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("system-resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid ResourceAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return resourceService.add(req);
        }
    }


    /**
     * 更新
     */
    @PostMapping(
            value = "update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid ResourceUpdateReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return resourceService.update(req);
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

            return resourceService.delete(req.getId());
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

            return resourceService.get(req.getId());
        }
    }


    /**
     * 获取子资源
     */
    @PostMapping(
            value = "/getChildren",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getChildren(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return resourceService.getChildren(req.getId());
        }
    }

    /**
     * 获取子资源树
     */
    @PostMapping(
            value = "/getTree",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getTree(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {

            return resourceService.getTree(req.getId());
        }
    }

}
