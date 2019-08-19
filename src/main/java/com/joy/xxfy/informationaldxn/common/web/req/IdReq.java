package com.joy.xxfy.informationaldxn.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class IdReq {
    @NotBlank(message = "ID不能为空")
    private Long id;
}
