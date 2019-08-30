package com.joy.xxfy.informationaldxn.module.resource.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.resource.domain.enums.ResourceType;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
public class ResourceAddReq extends BaseAddReq {
    /**
     * 资源URL
     */
    private String resourceUrl;

    /**
     * 资源类型 => ResourceType 0-菜单 1-按钮
     */
    @NotNull(message = "资源类型不能为空")
    private ResourceType resourceType;

    /**
     * 资源名称
     */
    @NotEmpty(message = "资源名称不能为空")
    private String resourceName;

    /**
     * 资源名称
     */
    @NotEmpty(message = "资源别称不能为空")
    private String resourceAliasName;

    /**
     * 父资源ID
     */
    @NotNull(message = "父ID不能为空")
    private Long parentId;


}
