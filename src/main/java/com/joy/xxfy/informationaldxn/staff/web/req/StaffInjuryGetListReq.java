package com.joy.xxfy.informationaldxn.staff.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.staff.domain.enums.InsuredEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.LeaveTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffInjuryGetListReq extends BasePageReq {
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
     * 工伤原因
     */
    private String injuryReasons;


    /**
     * 工商时间区间
     */
    private Date injuryTimeStart;
    private Date injuryTimeEnd;
}
