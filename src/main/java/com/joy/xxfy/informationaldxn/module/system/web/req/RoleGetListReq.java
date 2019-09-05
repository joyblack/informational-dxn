package com.joy.xxfy.informationaldxn.module.system.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class RoleGetListReq extends BasePageReq {
    /**
     * 名称
     */
    private String roleName;

    /**
     * 权限ID
     */
    private String permissions;
}
