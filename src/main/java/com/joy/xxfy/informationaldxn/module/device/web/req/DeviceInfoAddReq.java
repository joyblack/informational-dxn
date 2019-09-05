package com.joy.xxfy.informationaldxn.module.device.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class DeviceInfoAddReq extends BaseAddReq {

    // 设备名称
    @NotBlank(message = "设备名称不能为空")
    private String deviceName;

    /**
     * 设备分类ID
     */
    @NotNull(message = "设备分类不能为空")
    private Long deviceCategoryId;

    /**
     * 设备数量
     */
    @NotNull(message = "设备数量不能为空")
    @Min(value = 1,message = "数量至少为1")
    private Long deviceCount;

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
     * 设备状态
     */
    private DeviceStatusEnum deviceStatus;

    /**
     * 技术数据
     */
    @Lob
    private String technicalData;


}
