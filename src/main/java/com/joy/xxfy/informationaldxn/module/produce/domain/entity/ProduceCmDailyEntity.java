package com.joy.xxfy.informationaldxn.module.produce.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 煤矿生产日报
 */
@Entity
@Table(name = "produce_cm_daily")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class ProduceCmDailyEntity extends BaseEntity {
    /**
     * 所属的煤矿平台，设置为当前操作者所在的集团/煤矿平台
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "belong_company_id")
    private DepartmentEntity belongCompany;

    /**
     * 日报的日期
     */
    @Column(nullable = false)
    private Date dailyTime;
}
