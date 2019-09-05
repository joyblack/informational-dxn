package com.joy.xxfy.informationaldxn.module.login.web.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class LoginReq implements Serializable {

    private static final long serialVersionUID = 7562653215764454872L;

    @NotNull(message = "登录名不能为空")
    private String loginName;

    @NotNull(message = "密码不能为空")
    private String password;
}
