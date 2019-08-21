package com.joy.xxfy.informationaldxn.train.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * 培训照片
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "train_training_photo")
@Where(clause = "is_delete = 0")
public class TrainingPhotoEntity extends BaseEntity {

    // 存储路径
    @Lob
    @Column(nullable = false)
    private String storePath;

    // 名称
    private String fileName;

    // 大小
    private Long fileSize;

    /**
     * 培训信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "training_id")
    @JsonIgnore
    private TrainingEntity training;

}
