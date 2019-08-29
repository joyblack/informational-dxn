package com.joy.xxfy.informationaldxn.module.document.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.BelongCompanyReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.document.service.BorrowService;
import com.joy.xxfy.informationaldxn.module.document.service.LicenceService;
import com.joy.xxfy.informationaldxn.module.document.web.req.*;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("document-licence")
public class LicenceController extends BaseController {

    @Autowired
    private LicenceService licenceService;

    /**
     * 添加
     */
    @PostMapping(
            value = "/add",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult add(@RequestBody @Valid LicenceAddReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return licenceService.add(req, getLoginUser(request));
        }
    }

    /**
     * 更新
     */
    @PostMapping(
            value = "/update",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult update(@RequestBody @Valid LicenceUpdateReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return licenceService.update(req, getLoginUser(request));
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
            return licenceService.delete(idRequest.getId(), getLoginUser(request));
        }
    }

    /**
     * 获取(通过ID)
     */
    @PostMapping(
            value = "/get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq idRequest, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return licenceService.get(idRequest.getId(), getLoginUser(request));
        }
    }

    /**
     * 获取（通过登陆信息）
     */
    @PostMapping(
            value = "/getMyLicence",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getMyLicence(HttpServletRequest request) {
        return licenceService.getMyLicence(getLoginUser(request));
    }

    /**
     * 获取（通过登陆信息、证照类型）
     */
    @PostMapping(
            value = "/getMyLicenceByLicenceType",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getMyLicenceByLicenceType(@RequestBody @Valid LicenceTypeReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return licenceService.getMyLicenceByLicenceType(getLoginUser(request), req.getLicenceType());
        }
    }

    /**
     * 获取（通过平台信息、证照类型）
     */
    @PostMapping(
            value = "/getByBelongCompanyAndLicenceType",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByBelongCompanyAndLicenceType(@RequestBody @Valid LicenceAddReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return licenceService.getByBelongCompanyAndLicenceType(req);
        }
    }

    /**
     * 获取（通过平台信息）
     */
    @PostMapping(
            value = "/getByBelongCompany",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByBelongCompany(@RequestBody @Valid  BelongCompanyReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return licenceService.getByBelongCompany(req.getBelongCompanyId(), getLoginUser(request));
        }

    }

}
