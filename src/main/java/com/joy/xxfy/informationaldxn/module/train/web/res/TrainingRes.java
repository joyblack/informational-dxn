package com.joy.xxfy.informationaldxn.module.train.web.res;

import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingAttachmentEntity;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingPhotoEntity;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class TrainingRes extends TrainingEntity {
    private List<TrainingPhotoEntity> trainingPhotos = new ArrayList<>();

    private List<TrainingAttachmentEntity> trainingAttachments = new ArrayList<>();
}
