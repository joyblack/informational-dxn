package com.joy.xxfy.informationaldxn.module.account.web;

import com.joy.xxfy.informationaldxn.module.common.web.BaseController;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.IdReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.TestReq;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.user.service.UserService;
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

@RequestMapping("account")
@RestController
public class AccountController extends BaseController {

    /**
     * 获取当前登录用户的账户信息
     */
    @RequestMapping(
            value = "getMyInformation")
    public JoyResult getMyInformation(HttpServletRequest request) {
        return JoyResult.buildSuccessResultWithData(getLoginUser(request));
    }

    @RequestMapping(
            value = "getMyCompany")
    public JoyResult getMyCompany(HttpServletRequest request) {
        return JoyResult.buildSuccessResultWithData(getLoginUser(request).getCompany());
    }

    @RequestMapping(
            value = "getMyDepartment")
    public JoyResult getMyDepartment(HttpServletRequest request) {
        return JoyResult.buildSuccessResultWithData(getLoginUser(request).getDepartment());
    }



}
