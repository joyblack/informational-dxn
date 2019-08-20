package com.joy.xxfy.informationaldxn.position.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@ToString
public class PositionAddReq extends BaseAddReq {

    // 职位名称
    @NotBlank(message = "职位名称不能为空")
    private String positionName;

    // 描述信息
    private String describes;
}
