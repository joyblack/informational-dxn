package com.joy.xxfy.informationaldxn.module.train.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends BaseRepository<TrainingEntity>, JpaRepository<TrainingEntity, Long> {
    TrainingEntity findAllByTrainingName(String trainingName);
    TrainingEntity findAllByTrainingNameAndIdNot(String trainingName, Long id);
}
