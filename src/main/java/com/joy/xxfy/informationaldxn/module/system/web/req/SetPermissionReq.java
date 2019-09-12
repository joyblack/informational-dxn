package com.joy.xxfy.informationaldxn.module.system.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class SetPermissionReq {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "权限信息不能为空")
    private String permission;
}
