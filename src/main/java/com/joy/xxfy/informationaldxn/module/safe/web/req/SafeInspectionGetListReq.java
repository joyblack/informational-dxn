package com.joy.xxfy.informationaldxn.module.safe.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.InspectType;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class SafeInspectionGetListReq extends BasePageReq {
    /**
     * 巡检日期区间
     */
    private Date inspectTimeStart;
    private Date inspectTimeEnd;

    /**
     * 整改期限截止
     */
    private CommonYesEnum deadTimeStart;
    private CommonYesEnum deadTimeEnd;


    /**
     * 巡检的煤矿
     */
    private Long inspectCompanyId;


    /**
     * 巡检的部门
     */
    private Long inspectDepartmentId;

    /**
     * 巡检的类型
     */
    private InspectType inspectType;


    /**
     * 巡检地点
     */
    private String inspectPlace;

    /**
     * 整改状态
     */
    private RectificationStatusEnum rectificationStatus;

    /**
     * 整改超时
     */
    private CommonYesEnum isOverTime;


    /**
     * 整改人
     */
    private String rectificationPeople;


}
