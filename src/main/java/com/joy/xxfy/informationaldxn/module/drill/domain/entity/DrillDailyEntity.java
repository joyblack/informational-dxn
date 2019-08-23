package com.joy.xxfy.informationaldxn.module.drill.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钻孔日报
 */
@Entity
@Table(name = "produce_drill_daily")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrillDailyEntity extends BaseEntity {

    /**
     * 关联的钻孔工作
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "drill_work_id", nullable = false)
    private DrillWorkEntity drillWork;


    /**
     * 钻孔日期
     */
    @Column(nullable = false)
    private Date dailyTime;

    /**
     * 班次
     */
    @Column(nullable = false)
    private DailyShiftEnum shifts;

    /**
     * 打钻队伍
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity drillTeam;

    /**
     * 打钻的人数
     */
    @Column(nullable = false)
    private Long peopleNumber;

    /**
     * (当前日期、班次、打钻队伍下)的打钻总量
     */
    @Column(nullable = false)
    private BigDecimal doneTotalLength;


}
