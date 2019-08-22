package com.joy.xxfy.informationaldxn.train.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingAttachmentEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingAttachmentRepository extends BaseRepository<TrainingAttachmentEntity>, JpaRepository<TrainingAttachmentEntity, Long> {

    List<TrainingAttachmentEntity> findAllByTraining(TrainingEntity training);

    // delete by train
    @Modifying
    @Query("update TrainingAttachmentEntity t set t.isDelete = ture where t.training = :training")
    void softDeleteByTraining(@Param("train") TrainingEntity trainingEntity);
}
