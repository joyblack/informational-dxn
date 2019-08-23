package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffBlacklistGetListReq extends BasePageReq {
    /**
     * 姓名
     */
    private String username;

    /**
     * 身份证
     */
    private String idNumber;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 原因
     */
    private String blacklistReasons;


    /**
     * 解禁时间区间
     */
    private Date overTimeStart;
    private Date overTimeEnd;
}
