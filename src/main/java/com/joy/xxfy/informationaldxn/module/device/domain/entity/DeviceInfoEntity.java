package com.joy.xxfy.informationaldxn.module.device.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备
 */
@Entity
@Table(name = "device_info")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DeviceInfoEntity extends BaseEntity {

    // 设备名称，显示使用，真实名称出现重复的时候该名称为“挖掘机-1”
    @Column(nullable = false)
    private String deviceName;


    /**
     * 设备分类
     */
    @JoinColumn(name = "category_id",nullable = false)
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DeviceCategoryEntity deviceCategory;

    /**
     * 规格型号
     */
    private String deviceModel;

    /**
     * 生产厂家
     */
    private String manufacture;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 出厂日期
     */
    private Date manufactureTime;

    /**
     * 使用地点
     */
    private String usePlace;

    /**
     * 矿编
     */
    private String cmCode;

    /**
     * 厂编
     */
    private String productionCode;

    /**
     * 最近保养日期
     */
    private Date beforeMaintainTime;


    /**
     * 保养间隔时间
     */
    private Long maintainIntervalTime;

    /**
     * 超时前的提示天数
     */
    private Long tipDays;

    /**
     * 开始提示的时间
     */
    private Date tipStartTime;


    /**
     * 设备状态
     */
    private  DeviceStatusEnum deviceStatus;

    /**
     * 技术数据
     */
    @Lob
    private String technicalData;

    /**
     * 维保次数
     */
    private Long maintainNumber;


    /**
     * 下次保养日期：需要定时任务更新，检测设备是否到了该保养的时间
     */
    private Date nextMaintainTime;

    /**
     * 所属平台
     */
    @JoinColumn(name = "company_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity belongCompany;
}
