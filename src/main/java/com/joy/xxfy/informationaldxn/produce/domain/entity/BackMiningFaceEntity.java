package com.joy.xxfy.informationaldxn.produce.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.produce.domain.enums.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 回采工作面
 */
@Entity
@Table(name = "produce_back_mining_face")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class BackMiningFaceEntity extends BaseEntity {

    /**
     * 名称
     */
    @Column(nullable = false)
    private String backMiningFaceName;

    /**
     * 采面斜长
     */
    @Column(nullable = false)
    private BigDecimal slopeLength;

    /**
     * 回风顺槽长度
     */
    @Column(nullable = false)
    private BigDecimal returnAirChute;

    /**
     * 运输顺槽
     */
    @Column(nullable = false)
    private BigDecimal transportChute;


    /**
     * 已采长度
     */
    @Column(nullable = false)
    private BigDecimal doneLength;

    /**
     * 采面走向长度
     */
    private BigDecimal trendLength;

    /**
     * 开采日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;


    /**
     * 煤层厚度
     */
    private BigDecimal coalSeamThickness;

    /**
     * 煤层倾角
     */
    private BigDecimal coalSeamDipAngle;

    /**
     * 采高
     */
    private BigDecimal miningHigh;


    /**
     * 通风方式
     */
    private VentilationModeEnum ventilationMode;

    /***
     * 回采方式
     */
    private BackMiningModeEnum backMiningMode;

    /**
     * 可采储量(t)
     */
    private BigDecimal recoverReserves;



}
