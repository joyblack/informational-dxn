package com.joy.xxfy.informationaldxn.module.pan.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.pan.service.FileService;
import com.joy.xxfy.informationaldxn.module.pan.web.req.MkdirReq;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.jwt.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("pan-file")
@RestController
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;
    /**
     * 创建文件夹
     */
    @PostMapping(
            value = "/mkdir",
            produces = {"application/json;charset=UTF-8"})
    public JoyResult mkdir(@RequestBody @Valid MkdirReq req, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return JoyResult.buildFailedResult(Notice.REQUEST_PARAMETER_IS_ERROR, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return fileService.mkdir(req, getLoginUser(request));
        }
    }
}
