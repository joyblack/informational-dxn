package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class DeviceCategoryAddReq extends BaseAddReq {
    /**
     * 名称
     */
    @NotBlank(message = "类别名称不能为空")
    private String categoryName;

    /**
     * 父节点
     */
    @NotNull(message = "父节点信息不能为空")
    private Long parentId;
}
