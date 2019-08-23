package com.joy.xxfy.informationaldxn.module.staff.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString(callSuper = true)
public class UsernameReq {
    @NotBlank(message = "姓名不能为空")
    private String username;

}
