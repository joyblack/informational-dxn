package com.joy.xxfy.informationaldxn.module.cmplatform.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class AddCmPlatformReq extends BaseAddReq {

    @NotEmpty(message = "名称不能为空")
    private String cmName;

    /**
     * 登录账号
     */
    @NotNull(message = "登录账号不能为空")
    private String loginName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 管理员姓名
     */
    private String username;

    /**
     * 联系电话
     */
    private String phone;


}
