package com.joy.xxfy.informationaldxn.module.system.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.ResourceType;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class ResourceAddReq extends BaseAddReq {
    /**
     * 资源URL
     */
    @NotBlank(message = "资源URL不能为空")
    private String resourceUrl;

    /**
     * 资源类型 => ResourceType 0-菜单 1-按钮
     */
    @NotNull(message = "资源类型不能为空")
    private ResourceType resourceType;

    /**
     * 限制用户的类型:只允许集团；只允许煤矿平台；亦或是允许所有
     */
    @NotNull(message = "限制用户类型不能为空")
    private UserTypeEnum userType;

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称不能为空")
    private String resourceName;

    /**
     * 资源名称
     */
    @NotBlank(message = "资源别称不能为空")
    private String resourceAliasName;

    /**
     * 父资源ID
     */
    @NotNull(message = "父ID不能为空")
    private Long parentId;


}
