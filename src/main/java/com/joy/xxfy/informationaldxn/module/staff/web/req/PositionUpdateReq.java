package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class PositionUpdateReq extends BaseUpdateReq {

    // 职位名称
    @NotBlank(message = "职位名称不能为空")
    private String positionName;

    // 描述信息
    private String describes;
}
