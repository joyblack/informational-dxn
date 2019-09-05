package com.joy.xxfy.informationaldxn.module.device.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备保养
 */
@Entity
@Table(name = "device_maintain")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DeviceMaintainEntity extends BaseEntity {
    /**
     * 设备信息
     */
    @JoinColumn(name = "device_info_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DeviceInfoEntity deviceInfo;

    /**
     * 维保日期
     */
    @Column(nullable = false)
    private Date maintainTime;

    /**
     * 维保情况
     */
    @Column(nullable = false)
    private MaintainStatusEnum maintainStatus;

    /**
     * 维保类型
     */
    private MaintainTypeEnum maintainType;

    /**
     * 维保人员
     */
    private String maintainPeople;

    /**
     * 维保详情
     */
    @Lob
    private String maintainDetail;


}
