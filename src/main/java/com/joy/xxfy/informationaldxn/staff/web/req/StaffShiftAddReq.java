package com.joy.xxfy.informationaldxn.staff.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class StaffShiftAddReq extends BaseAddReq {
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String username;

    /**
     * 身份证号码
     */
    @NotBlank(message = "身份证号码不能为空")
    private String idNumber;

    /**
     * 调用的职位ID列表
     */
    @NotEmpty(message = "至少选择一个需要调动的岗位信息")
    private List<Long> entries;

    /**
     * 调入公司的信息
     */
    @NotNull(message = "调入的煤矿平台（公司）信息不能为空")
    private Long targetCompanyId;

    /**
     * 调入部门的信息
     */
    @NotNull(message = "调入的部门信息不能为空")
    private Long targetDepartmentId;


    /**
     * 调入的职位信息
     */
    @NotNull(message = "调入的职位信息不能为空")
    private Long targetPositionId;

    /**
     * 调动时间（可选项）
     */
    @NotNull(message = "调动时间不能为空")
    private Date shiftTime;

}
