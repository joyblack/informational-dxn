package com.joy.xxfy.informationaldxn.produce.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.domain.enums.CrossSectionTypeEnum;
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
@Table(name = "all_driving_face")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrivingFace extends BaseEntity {

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
    private Date startTime;

    /**
     * 掘进高度
     */
    private BigDecimal high;

    /**
     * 掘进坡度
     */
    private BigDecimal slope;


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









}
