package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class TestReq {
    @NotNull(message = "test times can't be null")
    private Integer times;
}
