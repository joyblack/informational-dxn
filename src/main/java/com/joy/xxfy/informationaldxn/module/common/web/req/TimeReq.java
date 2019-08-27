package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ToString
public class TimeReq {
    @NotBlank(message = "时间不能为空")
    private Date time;
}
