package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.module.staff.domain.enums.ReviewStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class ReviewReq {
    /**
     * 员工ID
     */
    @NotNull(message = "入职信息ID不能为空")
    private Long entryId;

    /**
     * 审核状态
     */
    @NotNull(message = "审核结果不能为空")
    private ReviewStatusEnum reviewStatus;

    /**
     * 备注信息、原因
     */
    private String reviewRemarks;


}
