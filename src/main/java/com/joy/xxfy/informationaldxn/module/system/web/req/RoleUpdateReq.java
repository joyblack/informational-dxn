package com.joy.xxfy.informationaldxn.module.system.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class RoleUpdateReq extends BaseUpdateReq {
    /**
     * 名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 权限ID串
     */
    private String permissions;
}
