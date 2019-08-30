package com.joy.xxfy.informationaldxn.module.resource.web;

import com.joy.informationdxn.domain.entity.Resources;
import com.joy.informationdxn.module.common.result.JoyResult;
import com.joy.informationdxn.module.common.result.Notice;
import com.joy.informationdxn.module.common.web.request.IdRequest;
import com.joy.informationdxn.module.common.web.request.TestRequest;
import com.joy.informationdxn.module.resource.service.ResourceService;
import com.joy.informationdxn.module.resource.web.req.ResourceListReq;
import com.joy.informationdxn.module.resource.web.req.ResourceReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * department
     */
    @PostMapping(
            value = "/test",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid TestRequest req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.test(req.getTimes());
        }
    }

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid Resources resources, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.add(resources);
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid Resources resources, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.update(resources);
        }
    }

    /**
     * 删除
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid IdRequest idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.delete(idRequest.getId());
        }
    }

    /**
     * 获取
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdRequest idRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.get(idRequest.getId());
        }
    }

    /**
     * 获取列表
     */
    @RequestMapping(
            value = "getList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getList(@RequestBody @Valid ResourceListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.getList(req);
        }
    }

    /**
     * 获取子资源
     */
    @PostMapping(
            value = "/getChildren",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getChildren(@RequestBody @Valid ResourceReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.getChildren(req.getParentId());
        }
    }

    /**
     * 获取子资源树
     */
    @PostMapping(
            value = "/getTree",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getTree(@RequestBody @Valid ResourceReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return resourceService.getTree(req.getParentId());
        }
    }

}
