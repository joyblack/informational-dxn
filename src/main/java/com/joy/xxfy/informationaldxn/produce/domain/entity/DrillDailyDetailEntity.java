package com.joy.xxfy.informationaldxn.produce.domain.entity;

import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 钻孔日报详情信息
 */
@Entity
@Table(name = "produce_drill_daily_detail")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrillDailyDetailEntity extends BaseEntity {
    /**
     * 序号
     */
    @Column(nullable = false)
    private Long orderNumber;

    /**
     * 打孔长度
     */
    @Column(nullable = false)
    private Long doneLength;

    /**
     * 钻孔信息(钻孔工作详情)
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_hole_id", nullable = false)
    private DrillHoleEntity drillHole;

    /**
     * 所属的日报信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_daily_id")
    private DrillDailyEntity drillDaily;

}
