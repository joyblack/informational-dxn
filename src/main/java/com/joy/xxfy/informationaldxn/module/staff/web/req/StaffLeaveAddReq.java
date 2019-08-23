package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.*;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class StaffLeaveAddReq extends BaseAddReq {
    /**
     * 身份证
     */
    @NotEmpty(message = "身份证号不能为空")
    private String idNumber;

    /**
     * 离职的岗位（入职信息ID）
     */
    @NotEmpty(message = "至少选择一个需要离职的岗位信息")
    private List<Long> entries;

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
