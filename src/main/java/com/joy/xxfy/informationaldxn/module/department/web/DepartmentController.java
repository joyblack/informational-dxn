package com.joy.xxfy.informationaldxn.module.department.web;

import com.joy.xxfy.informationaldxn.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.department.service.DepartmentService;
import com.joy.xxfy.informationaldxn.module.department.web.req.DepartmentAddReq;
import com.joy.xxfy.informationaldxn.module.department.web.req.DepartmentUpdateReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;


    /**
     * 获取部门
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.get(req.getId());
        }
    }


    /**
     * 添加部门
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid DepartmentAddReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.add(req);
        }
    }

    /**
     * 更新部门
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid DepartmentUpdateReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.update(req);
        }
    }

    /**
     * 删除部门
     */
    @PostMapping(
            value = "/delete",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.delete(req.getId());
        }
    }

    /**
     * 获取子部门
     */
    @PostMapping(
            value = "/getChildren",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getChildren(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.getChildren(req.getId());
        }
    }

    /**
     * 获取子部门树
     */
    @PostMapping(
            value = "/getTree",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getTree(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.getTree(req.getId());
        }
    }

    // 获取当前用户权限范围内所能展示的公司/平台列表
    @RequestMapping(
            value = "/getCompanyList")
    public JoyResult getCompanyList() {
        // SESSION : 查询用户信息作为参数
        UserEntity user = null;
        return departmentService.getCompanyList(user);
    }

    // 获取父部门遍历树（包含自身）
    @PostMapping(
            value = "/getParentNodes",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getParentNodes(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.getParentNodes(req.getId());
        }
    }

    // 获取父部门遍历树（包含自身）字符串
    @PostMapping(
            value = "/getParentNodesJustIds",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getParentNodesJustIds(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return departmentService.getParentNodesJustIds(req.getId());
        }
    }
}
