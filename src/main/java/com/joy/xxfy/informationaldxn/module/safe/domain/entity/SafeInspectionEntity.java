package com.joy.xxfy.informationaldxn.module.safe.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.InspectType;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 安全巡检
 */
@Entity
@Table(name = "safe_inspection")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class SafeInspectionEntity extends BaseEntity {
    /**
     * 巡检日期
     */
    @Column(nullable = false)
    private Date inspectTime;

    /**
     * 巡检的煤矿
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private DepartmentEntity inspectCompany;

    /**
     * 巡检的部门
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private DepartmentEntity inspectDepartment;

    /**
     * 巡检的类型
     */
    @Column(nullable = false)
    private InspectType inspectType;

    /**
     * 巡检地点
     */
    @Column(nullable = false)
    private String inspectPlace;

    /**
     * 问题描述
     */
    @Lob
    @Column(nullable = false)
    private String problemDescribes;


    /**
     * 整改期限
     */
    @Column(nullable = false)
    private Date deadTime;

    /**
     * 提示开始时间
     */
    private Date tipStartTime;


    /**
     * 超时前提示天数
     */
    @Column(nullable = false)
    private Long tipDays;


    /**
     * 整改人
     */
    private String rectificationPeople;

    /**
     * 整改状态
     */
    @Column(nullable = false)
    private RectificationStatusEnum rectificationStatus;

    /**
     * 整改超时
     */
    @Column(nullable = false)
    private CommonYesEnum isOverTime;
}
