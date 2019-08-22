package com.joy.xxfy.informationaldxn.train.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.staff.domain.enums.EducationEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.staff.domain.enums.SexEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class TrainingGetListReq extends BasePageReq {
    /**
     * 培训名称
     */
    private String trainingName;

    /**
     * 培训人姓名
     */
    private String trainingUsername;


    /**
     * 培训日期截止
     */
    private Date trainingTimeStart;
    private Date trainingTimeEnd;

    /**
     * 部门
     */
    private Long departmentId;


}
