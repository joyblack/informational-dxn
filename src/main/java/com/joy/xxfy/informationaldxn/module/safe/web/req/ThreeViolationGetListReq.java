package com.joy.xxfy.informationaldxn.module.safe.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.ThreeViolationTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class ThreeViolationGetListReq extends BasePageReq {
    /**
     * 违章的煤矿平台
     */
    private Long violationCompanyId;

    /**
     * 违章日期区间
     */
    private Date violationTimeStart;
    private Date violationTimeEnd;

    /**
     * 违章部门
     */
    private Long violationDepartmentId;

    /**
     * 违章人
     */
    private String violationPeople;

    /**
     * 违章地点
     */
    private String violationPlace;

    /**
     * 违章类型
     */
    private ThreeViolationTypeEnum threeViolationType;

    /**
     * 班次
     */
    private DailyShiftEnum dailyShift;

    /**
     * 检查人
     */
    private String checkPeople;
}
