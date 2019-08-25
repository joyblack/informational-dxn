package com.joy.xxfy.informationaldxn.module.backmining.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 回采日报详情
 */
@Entity
@Table(name = "produce_back_mining_daily_detail")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class BackMiningDailyDetailEntity extends BaseEntity {
    /**
     * 所属日报
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "back_mining_daily_id", nullable = false)
    private BackMiningDailyEntity backMiningDaily;

    /**
     * 班次
     */
    @Column(nullable = false)
    private DailyShiftEnum shifts;

    /**
     * 回采队伍
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity team;

    /**
     * 人数
     */
    private Long peopleNumber;

    /**
     * 推进度
     */
    @Column(nullable = false)
    private BigDecimal doneLength;

    /**
     * 产量(t)
     */
    private BigDecimal output;

    /**
     * 工作内容
     */
    @Lob
    private String workContent;

}
