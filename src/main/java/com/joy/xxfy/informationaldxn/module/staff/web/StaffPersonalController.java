package com.joy.xxfy.informationaldxn.module.staff.web;

import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.module.staff.service.StaffPersonalService;
import com.joy.xxfy.informationaldxn.module.staff.web.req.IdNumberReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.UsernameReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("staff-personal")
public class StaffPersonalController {
    @Autowired
    private StaffPersonalService staffPersonalService;
    /**
     * 解析身份证信息
     */
    @RequestMapping(
            value = "explainIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult explainIdNumber(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.explainIdNumber(req.getIdNumber());
        }
    }

    /**
     * 获取个人信息(idNumber)
     */
    @RequestMapping(
            value = "getByIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByIdNumber(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.getPersonalByIdNumber(req.getIdNumber());
        }
    }

    /**
     * 获取个人信息(id)
     */
    @RequestMapping(
            value = "get",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult get(@RequestBody @Valid IdReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.get(req.getId());
        }
    }

    /**
     * 获取个人信息(username)
     */
    @RequestMapping(
            value = "getByUsername",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByUsername(@RequestBody @Valid UsernameReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.getByUsername(req.getUsername());
        }
    }

    /**
     * getPersonalStatus(获取个人任职状态)
     */
    @RequestMapping(
            value = "getStatusByIdNumber",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult getByUsername(@RequestBody @Valid IdNumberReq req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            // copy
            return staffPersonalService.getStatusByIdNumber(req.getIdNumber());
        }
    }



    /**
     * 上传文件
     */
    @RequestMapping(value = "/uploadIdentityPhoto", method = RequestMethod.POST)
    public JoyResult uploadIdentityPhoto(@RequestParam("file") MultipartFile file, String module) {
        if (null == file) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_IS_NULL);
        } else {
            return staffPersonalService.uploadIdentityPhoto(file);
        }
    }

    /**
     * 上传文件
     */
    @RequestMapping(value = "/uploadOneInchPhoto", method = RequestMethod.POST)
    public JoyResult uploadFile(@RequestParam("file") MultipartFile file, String module) {
        if (null == file) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_IS_NULL);
        } else {
            return staffPersonalService.uploadOneInchPhoto(file);
        }
    }

    /**
     * 下载文件
     */
    @RequestMapping(
            value = "/downloadIdentityPhoto/{id}",
            method = RequestMethod.GET)
    public void downloadFile(@PathVariable("id")Long id, HttpServletRequest req, HttpServletResponse resp) {
        staffPersonalService.downloadIdentityPhoto(id, req, resp);
    }

    /**
     * 下载文件
     */
    @RequestMapping(
            value = "/downloadOneInchPhoto/{id}",
            method = RequestMethod.GET)
    public void downloadOneInchPhoto(@PathVariable("id")Long id, HttpServletRequest req, HttpServletResponse resp) {
        staffPersonalService.downloadOneInchPhoto(id, req, resp);
    }


}
