package com.joy.xxfy.informationaldxn.module.safe.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.ThreeViolationTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 违章
 */
@Entity
@Table(name = "safe_three_violation")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class ThreeViolationEntity extends BaseEntity {
    /**
     * 违章的煤矿平台
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "violation_company_id")
    private DepartmentEntity violationCompany;

    /**
     * 违章日期
     */
    @Column(nullable = false)
    private Date violationTime;

    /**
     * 违章类型
     */
    @Column(nullable = false)
    private ThreeViolationTypeEnum threeViolationType;


    /**
     * 违章部门
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "violation_department_id")
    private DepartmentEntity violationDepartment;

    /**
     * 违章人
     */
    @Column(nullable = false)
    private String violationPeople;

    /**
     * 违章地点
     */
    @Column(nullable = false)
    private String violationPlace;

    /**
     * 班次
     */
    @Column(nullable = false)
    private DailyShiftEnum dailyShift;

    /**
     * 检查人
     */
    private String checkPeople;

    /**
     * 违章详情
     */
    @Lob
    private String violationContent;


    /**
     * 处理意见
     */
    @Lob
    private String handlerSuggestion;


}
