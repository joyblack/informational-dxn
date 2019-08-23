package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.InsuredEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.LeaveTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class StaffLeaveUpdateReq extends BaseUpdateReq {
    /**
     * 离职时间
     */
    @NotNull(message = "离职时间不能为空")
    private Date leaveTime;

    /**
     * 离职类型
     */
    @NotNull(message = "离职类型不能为空")
    private LeaveTypeEnum leaveType;

    /**
     * 参保状态
     */
    private InsuredEnum insured;

}
