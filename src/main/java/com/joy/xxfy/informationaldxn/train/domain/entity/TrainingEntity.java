package com.joy.xxfy.informationaldxn.train.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 培训
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "train_training")
@Where(clause = "is_delete = 0")
public class TrainingEntity extends BaseEntity {
    /**
     * 培训名称
     */
    @Column(nullable = false)
    private String trainingName;

    /**
     * 培训日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(nullable = false)
    private Date trainingTime;

    /**
     * 培训人姓名
     */
    @Column(nullable = false)
    private String trainingUsername;

    /**
     * 受训的部门
     */
    @JoinColumn(name = "department_id")
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    private DepartmentEntity department;

    /**
     * 培训的内容
     */
    @Lob
    @Column(nullable = false)
    private String trainingContent;

}
