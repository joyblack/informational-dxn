package com.joy.xxfy.informationaldxn.module.drill.web;

import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.NameReq;
import com.joy.xxfy.informationaldxn.module.drill.service.DrillWorkService;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkGetListReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkUpdateReq;
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
@RequestMapping("produce-drill-work")
public class DrillWorkController {
    @Autowired
    private DrillWorkService drillWorkService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid DrillWorkAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return drillWorkService.add(req);
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
            return drillWorkService.delete(req.getId());
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
            // copy
            return drillWorkService.get(req.getId());
        }
    }

    /**
     * 获取（通过名称）
     */
    @PostMapping(
            value = "/getByName",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByName(@RequestBody @Valid NameReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return drillWorkService.getByName(req.getName());
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid DrillWorkUpdateReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return drillWorkService.update(req);
        }
    }

    /**
     * 获取分页数据
     */
    @PostMapping(
            value = "/getPagerList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getPagerList(@RequestBody @Valid DrillWorkGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return drillWorkService.getPagerList(req);
        }
    }

    /**
     * 获取所有数据
     */
    @PostMapping(
            value = "/getAllList",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getAllList(@RequestBody @Valid DrillWorkGetListReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return drillWorkService.getAllList(req);
        }
    }
}
