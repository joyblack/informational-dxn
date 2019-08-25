package com.joy.xxfy.informationaldxn.module.backmining.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 回采日报
 */
@Entity
@Table(name = "produce_back_mining_daily")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class BackMiningDailyEntity extends BaseEntity {
    /**
     * 关联的工作面
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "back_mining_face_id", nullable = false)
    private BackMiningFaceEntity backMiningFace;

    /**
     * 日报填写日期
     */
    @Column(nullable = false)
    private Date dailyTime;


    /**
     * 总人数
     */
    private Long totalPeopleNumber;

    /**
     * 总推进度
     */
    @Column(nullable = false)
    private BigDecimal totalDoneLength;

    /**
     * 总产量(t)
     */
    private BigDecimal totalOutput;

}
