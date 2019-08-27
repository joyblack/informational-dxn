package com.joy.xxfy.informationaldxn.module.produce.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class SetRemarkReq {
    @NotNull(message = "时间不能为空")
    private Date time;

    private String remarks;
}
