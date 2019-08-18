package com.joy.xxfy.informationaldxn.staff.web.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class IdNumberReq {
    /**
     * 身份证号码
     */
    private String idNumber;

}
