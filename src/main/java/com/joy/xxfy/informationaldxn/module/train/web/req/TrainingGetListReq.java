package com.joy.xxfy.informationaldxn.module.train.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

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

    /**
     * 受训煤矿
     */
    private Long companyId;


}
