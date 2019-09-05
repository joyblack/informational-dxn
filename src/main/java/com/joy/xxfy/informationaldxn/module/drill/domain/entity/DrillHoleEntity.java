package com.joy.xxfy.informationaldxn.module.drill.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钻孔信息
 */
@Entity
@Table(name = "produce_drill_hole")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrillHoleEntity extends BaseEntity {
    /**
     * 关联的钻孔工作
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_work_id")
    private DrillWorkEntity drillWork;

    /**
     * 钻孔序号
     */
    @Column(nullable = false)
    @NotNull(message = "钻孔序号不能为空")
    private Long orderNumber;

    /**
     * 编号
     */
    @NotBlank(message = "钻孔编号不能为空")
    @Column(nullable = false)
    private String code;

    /**
     * 钻孔设计长度
     */
    @NotNull(message = "钻孔设计长度不能为空")
    @Column(nullable = false)
    private BigDecimal totalLength;

    /**
     * 钻孔已打长度
     */
    @Column(nullable = false)
    private BigDecimal doneLength;

    /**
     * 钻孔剩余长度
     */
    @Column(nullable = false)
    private BigDecimal leftLength;

    /**
     * 进度
     */
    @Column(nullable = false)
    private String progress;

    /**
     * 倾角
     */
    private BigDecimal dipAngle;

    /**
     * 夹角
     */
    private BigDecimal intersectionAngle;

    /**
     * 预计见煤
     */
    private BigDecimal predicateAppearCoal;

    /**
     * 实际见煤
     */
    private BigDecimal realAppearCoal;

    /**
     * 预计止煤
     */
    private BigDecimal predicateDisappearCoal;

    /**
     * 实际止煤
     */
    private BigDecimal realDisappearCoal;

    /**
     * 预计煤厚
     */
    private BigDecimal predicateCoalThickness;

    /**
     * 实际煤厚
     */
    private BigDecimal realCoalThickness;

    /**
     * 成孔日期
     */
    private Date completeTime;


}
