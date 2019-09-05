package com.joy.xxfy.informationaldxn.module.drill.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.enums.DrillCategoryEnum;
import com.joy.xxfy.informationaldxn.module.drill.domain.enums.DrillRockCharacterEnum;
import com.joy.xxfy.informationaldxn.module.drill.domain.enums.DrillTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钻孔工作
 */
@Entity
@Table(name = "produce_drill_work")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrillWorkEntity extends BaseEntity {
    /**
     * 所属的煤矿平台，设置为当前操作者所在的集团/煤矿平台
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "belong_company_id")
    private DepartmentEntity belongCompany;

    /**
     * 钻孔工作名称
     */
    @Column(nullable = false)
    private String drillWorkName;

    /**
     * 开孔日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date drillTime;

    /**
     * 钻孔类别
     */
    private DrillCategoryEnum drillCategory;

    /**
     * 钻孔类型
     */
    private DrillTypeEnum drillType;


    /**
     * 钻孔岩性
     */
    private DrillRockCharacterEnum drillRockCharacter;

    /**
     * 钻孔设计总数
     */
    private Long totalDrillHoleNumber;

    /**
     * 已施工的钻孔数
     */
    private Long completedDrillHoleNumber;

    /**
     * 未施工的钻孔数
     */
    private Long notCompletedDrillHoleNumber;

    /**
     * 钻孔的总长度
     */
    private BigDecimal totalLength;

    /**
     * 进度
     */
    private String progress;

    /**
     * 已打总量
     */
    private BigDecimal totalDoneLength;

    /**
     * 未打总量
     */
    private BigDecimal totalLeftLength;

}
