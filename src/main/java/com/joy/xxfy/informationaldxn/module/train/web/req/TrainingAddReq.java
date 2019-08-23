package com.joy.xxfy.informationaldxn.module.train.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class TrainingAddReq extends BaseAddReq {
    /**
     * 培训名称
     */
    @NotBlank(message = "名称不能为空")
    private String trainingName;

    /**
     * 培训日期
     */
    @NotNull(message = "培训日期不能为空")
    private Date trainingTime;

    /**
     * 培训人姓名
     */
    @NotBlank(message = "培训人不能为空")
    private String trainingUsername;

    /**
     * 受训的部门
     */
    @NotNull(message = "受训部门不能为空")
    private Long departmentId;

    /**
     * 培训的内容
     */
    @NotBlank(message = "培训内容不能为空")
    private String trainingContent;


}
