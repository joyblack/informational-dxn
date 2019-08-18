package com.joy.xxfy.informationaldxn.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString(callSuper = true)
public class BasePermissionReq {
    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "权限信息不能为空")
    private List<Long> permission;
}
