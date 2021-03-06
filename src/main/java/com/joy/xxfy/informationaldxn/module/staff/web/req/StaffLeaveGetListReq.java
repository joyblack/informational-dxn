package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffLeaveGetListReq extends BasePageReq {
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
     * 离职类型
     */
    private LeaveTypeEnum leaveType;

    /**
     * 离职日期区间
     */
    private Date leaveTimeStart;
    private Date leaveTimeEnd;

    /**
     * 参保状态
     */
    private InsuredEnum insured;

    /**
     * 入职煤矿/平台
     */
    private Long companyId;

    /**
     * 入职部门
     */
    private Long departmentId;

    /**
     * 入职的职位/工种
     */
    private Long positionId;
}
