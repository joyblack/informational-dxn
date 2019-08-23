package com.joy.xxfy.informationaldxn.module.staff.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString(callSuper = true)
public class IdNumberReq {
    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证不能为空")
    private String idNumber;

}
