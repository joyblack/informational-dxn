package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffShiftGetListReq extends BasePageReq {
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
     * 调入煤矿信息
     */
    private Long targetCompanyId;

    /**
     * 调入部门的信息
     */
    private Long targetDepartmentId;


    /**
     * 调入的职位信息
     */
    private Long targetPositionId;

    /**
     * 调动时间
     */
    private Date shiftTimeStart;
    private Date shiftTimeEnd;
}
