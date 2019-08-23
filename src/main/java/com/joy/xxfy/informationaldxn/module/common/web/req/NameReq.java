package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class NameReq {
    @NotBlank(message = "名称不能为空")
    private String name;
}
