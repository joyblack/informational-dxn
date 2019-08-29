package com.joy.xxfy.informationaldxn.module.safe.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.InspectType;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 提交的数据问题项
 */
@Data
@ToString(callSuper = true)
public class SafeInspectionProblemItem {
    /**
     * 巡检地点
     */
    @NotBlank(message = "巡检地点不能为空")
    private String inspectPlace;

    /**
     * 问题描述
     */
    @NotBlank(message = "问题描述不能为空")
    private String problemDescribes;

    /**
     * 整改期限
     */
    @NotNull(message = "整改期限不能为空")
    private Date deadTime;

    /**
     * 超时前提示天数
     */
    @NotNull(message = "超时前提示天数不能为空")
    private Long tipDays;
}
