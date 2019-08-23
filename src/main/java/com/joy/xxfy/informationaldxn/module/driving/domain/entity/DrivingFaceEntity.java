package com.joy.xxfy.informationaldxn.module.driving.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.CrossSectionTypeEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.DrivingTechnologyTypeEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.RockCharacterEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.SupportMethodEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 掘进工作面
 */
@Entity
@Table(name = "produce_driving_face")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrivingFaceEntity extends BaseEntity {

    /**
     * 名称
     */
    @Column(nullable = false)
    private String drivingFaceName;


    /**
     * 设计长度(总长度)
     */
    @Column(nullable = false)
    private BigDecimal totalLength;


    /**
     * 已掘长度
     */
    @Column(nullable = false)
    private BigDecimal doneLength;


    /**
     * 剩余长度
     */
    private BigDecimal leftLength;

    /**
     * 开掘日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 掘进高度
     */
    private BigDecimal drivingHigh;

    /**
     * 掘进坡度
     */
    private BigDecimal drivingSlope;


    /**
     * 断面(m2)
     */
    private BigDecimal crossSection;

    /**
     * 断面形式
     */
    private CrossSectionTypeEnum crossSectionType;


    /**
     * 煤层厚度
     */
    private BigDecimal coalSeamThickness;


    /**
     * 掘进工艺
     */
    private DrivingTechnologyTypeEnum drivingTechnologyType;

    /**
     * 支护方式
     */
    private SupportMethodEnum supportMethod;

    /**
     * 岩性
     */
    private RockCharacterEnum rockCharacter;

}
