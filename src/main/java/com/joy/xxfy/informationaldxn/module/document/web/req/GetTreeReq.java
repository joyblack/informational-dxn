package com.joy.xxfy.informationaldxn.module.document.web.req;

import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class GetTreeReq {
    @NotNull(message = "顶层节点ID")
    private Long id;

    @NotNull(message = "所属公司/平台ID不能为空")
    private Long belongCompanyId;

    @NotNull(message = "权限类型不能为空")
    private PermissionTypeEnum permissionType;

}
