package com.joy.xxfy.informationaldxn.train.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingPhotoRepository extends BaseRepository<TrainingPhotoEntity>, JpaRepository<TrainingPhotoEntity, Long> {

    List<TrainingPhotoEntity> findAllByTraining(TrainingEntity training);



    // delete by train
    @Modifying
    @Query("update TrainingPhotoEntity t set t.isDelete = ture where t.training = :training")
    void softDeleteByTraining(@Param("train") TrainingEntity trainingEntity);
}
