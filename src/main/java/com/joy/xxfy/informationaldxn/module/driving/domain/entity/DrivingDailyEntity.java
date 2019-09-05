package com.joy.xxfy.informationaldxn.module.driving.domain.entity;

import com.joy.xxfy.informationaldxn.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 掘进日报
 */
@Entity
@Table(name = "produce_driving_daily")
@Data
@ToString(callSuper = true)
@Where(clause = "is_delete = 0")
public class DrivingDailyEntity extends BaseEntity {
    /**
     * 关联的掘进工作面
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "driving_face_id", nullable = false)
    private DrivingFaceEntity drivingFace;

    /**
     * 日报填写日期
     */
    @Column(nullable = false)
    private Date dailyTime;


    /**
     * 班次
     */
    @Column(nullable = false)
    private DailyShiftEnum shifts;

    /**
     * 掘进队伍
     */
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity drivingTeam;

    /**
     * 人数
     */
    private Long peopleNumber;

    /**
     * 进尺
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
