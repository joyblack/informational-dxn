package com.joy.xxfy.informationaldxn.train.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffBlacklistEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingRepository extends BaseRepository<TrainingEntity>, JpaRepository<TrainingEntity, Long> {
    TrainingEntity findAllByTrainingName(String trainingName);
    TrainingEntity findAllByTrainingNameAndIdNot(String trainingName, Long id);
}
