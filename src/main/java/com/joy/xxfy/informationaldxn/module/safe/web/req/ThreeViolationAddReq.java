package com.joy.xxfy.informationaldxn.module.safe.web.req;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.ThreeViolationTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class ThreeViolationAddReq extends BaseAddReq {
    /**
     * 违章的煤矿平台
     */
    @NotNull(message = "违章的煤矿平台信息不能为空")
    private Long violationCompanyId;

    /**
     * 违章日期
     */
    @NotNull(message = "违章日期不能为空")
    private Date violationTime;

    /**
     * 违章类型
     */
    @NotNull(message = "违章类型不能为空")
    private ThreeViolationTypeEnum threeViolationType;


    /**
     * 违章部门
     */
    @NotNull(message = "违章的部门信息不能为空")
    private Long violationDepartmentId;

    /**
     * 违章人
     */
    @NotBlank(message = "违章人不能为空")
    private String violationPeople;

    /**
     * 违章地点
     */
    @NotBlank(message = "违章地点不能为空")
    private String violationPlace;

    /**
     * 班次
     */
    @NotNull(message = "违章班次不能为空")
    private DailyShiftEnum dailyShift;


    /**
     * 检查人
     */
    private String checkPeople;

    /**
     * 违章详情
     */
    private String violationContent;


    /**
     * 处理意见
     */
    private String handlerSuggestion;


}
