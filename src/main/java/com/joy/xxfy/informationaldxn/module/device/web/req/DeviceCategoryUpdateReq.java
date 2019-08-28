package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.UpdateCmPlatformReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class DeviceCategoryUpdateReq extends BaseUpdateReq {
    /**
     * 名称
     */
    @NotBlank(message = "类别名称不能为空")
    private String categoryName;

}
