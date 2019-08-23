package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class ChangePasswordReq {
    @NotNull(message = "ID不能为空")
    private Long id;

    @NotEmpty(message = "旧密码不能为空")
    private String password;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    @NotEmpty(message = "确认密码不能为空")
    private String affirmPassword;



}
