package com.joy.xxfy.informationaldxn.produce.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrillCategoryEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrillRockCharacterEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.DrillTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 钻孔工作详情
 */
@Entity
@Table(name = "produce_drill_work_detail")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrillWorkDetailEntity extends BaseEntity {

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
     * 倾角
     */
    private BigDecimal dipAngle;

    /**
     * 预计见煤
     */
    private BigDecimal predicateAppearCoal;

    /**
     * 预计止煤
     */
    private BigDecimal predicateDisappearCoal;

    /**
     * 预计煤厚
     */
    private BigDecimal predicateCoalThickness;

    /**
     * 关联的钻孔工作
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_work_id", nullable = false)
    private DrillWorkEntity drillWork;

}
