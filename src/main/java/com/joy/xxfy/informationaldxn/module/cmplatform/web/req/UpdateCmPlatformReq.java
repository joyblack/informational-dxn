package com.joy.xxfy.informationaldxn.module.cmplatform.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class UpdateCmPlatformReq extends BaseUpdateReq {
    /**
     * 平台名称
     */
    @NotNull(message = "煤矿平台名称不能为空")
    private String cmName;

    /**
     * 登录账号
     */
    @NotNull(message = "登陆账号不能为空")
    private String loginName;

    /**
     * 管理员姓名
     */
    private String username;

    /**
     * 联系电话
     */
    private String phone;



}
