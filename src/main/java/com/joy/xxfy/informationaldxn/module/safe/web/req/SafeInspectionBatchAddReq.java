package com.joy.xxfy.informationaldxn.module.safe.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.InspectType;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class SafeInspectionBatchAddReq extends BaseAddReq {
    /**
     * 巡检日期
     */
    @NotNull(message = "巡检日期不能为空")
    private Date inspectTime;

    /**
     * 巡检的煤矿
     */
    @NotNull(message = "巡检煤矿信息不能为空")
    private Long inspectCompanyId;


    /**
     * 巡检的部门
     */
    @NotNull(message = "巡检部门信息不能为空")
    private Long inspectDepartmentId;

    /**
     * 巡检的类型
     */
    @NotNull(message = "巡检类型不能为空")
    private InspectType inspectType;

    /**
     * 问题项
     */
    @NotEmpty(message = "问题项不能为空")
    private List<SafeInspectionProblemItem> problemItems;
}
