package com.joy.xxfy.informationaldxn.module.train.domain.repository;


import com.joy.xxfy.informationaldxn.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends BaseRepository<TrainingEntity>, JpaRepository<TrainingEntity, Long> {
    TrainingEntity findFirstByTrainingName(String trainingName);
}