package com.joy.xxfy.informationaldxn.train.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingAttachmentRepository extends BaseRepository<TrainingAttachmentEntity>, JpaRepository<TrainingAttachmentEntity, Long> {
}
