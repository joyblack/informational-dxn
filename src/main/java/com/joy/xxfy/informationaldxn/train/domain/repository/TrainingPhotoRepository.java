package com.joy.xxfy.informationaldxn.train.domain.repository;


import com.joy.xxfy.informationaldxn.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPhotoRepository extends BaseRepository<TrainingPhotoEntity>, JpaRepository<TrainingPhotoEntity, Long> {
}
