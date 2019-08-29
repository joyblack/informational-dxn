package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 分页列表基础参数
 */
@Data
@ToString
public class BelongCompanyReq {
    /**
     * 所属平台信息
     */
    @NotNull(message = "集团/平台ID不能为空")
    private Long belongCompanyId;
}
