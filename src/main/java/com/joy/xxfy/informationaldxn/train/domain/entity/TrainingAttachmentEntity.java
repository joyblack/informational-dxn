package com.joy.xxfy.informationaldxn.train.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joy.xxfy.informationaldxn.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * 培训附件
 */
@Entity
@Data
@ToString(callSuper = true)
@Table(name = "train_training_attachment")
@Where(clause = "is_delete = 0")
public class TrainingAttachmentEntity extends BaseEntity {
    /**
     * 培训信息
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "training_id")
    @JsonIgnore
    private TrainingEntity training;

    // 存储路径
    @Lob
    @Column(nullable = false)
    private String storePath;

    // 名称
    private String fileName;

    // 大小
    private Long fileSize;

}
