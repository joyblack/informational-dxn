package com.joy.xxfy.informationaldxn.train.service;

import com.joy.xxfy.informationaldxn.common.service.BaseService;
import com.joy.xxfy.informationaldxn.common.web.res.FileInfoRes;
import com.joy.xxfy.informationaldxn.file.domain.entity.SystemFileEntity;
import com.joy.xxfy.informationaldxn.file.domain.repository.SystemFileRepository;
import com.joy.xxfy.informationaldxn.file.enums.UploadModuleEnums;
import com.joy.xxfy.informationaldxn.publish.constant.StoreFilePathConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingPhotoEntity;
import com.joy.xxfy.informationaldxn.train.domain.repository.TrainingPhotoRepository;
import com.joy.xxfy.informationaldxn.train.domain.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class TrainingPhotoService extends BaseService {
    @Autowired
    private TrainingPhotoRepository trainingPhotoRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    public JoyResult upload(MultipartFile file, Long id) {
        TrainingEntity trainingEntity = trainingRepository.findAllById(id);
        if(trainingEntity == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NOT_EXIST);
        }
        // 保存文件:验证格式为图片
        JoyResult result = saveModuleFile(file, StoreFilePathConstant.TRAINING_PHOTO, true);
        if(result.getState().equals(Boolean.FALSE)){
            return result;
        }
        FileInfoRes fileInfo = (FileInfoRes)result.getData();
        // 装配信息
        TrainingPhotoEntity trainingPhoto = new TrainingPhotoEntity();
        // 存储路径
        trainingPhoto.setStorePath(fileInfo.getFilePath()+ File.separator + fileInfo.getFilename());
        // 文件名
        trainingPhoto.setFileName(fileInfo.getFilename());
        // 大小
        trainingPhoto.setFileSize(file.getSize());
        // 所属记录
        trainingPhoto.setTraining(trainingEntity);
        // save
        return JoyResult.buildSuccessResultWithData(trainingPhotoRepository.save(trainingPhoto));
    }

    public void download(Long id, HttpServletRequest req, HttpServletResponse resp) {
        TrainingPhotoEntity trainingPhoto = trainingPhotoRepository.findAllById(id);
        FileUtil.downloadFile(trainingPhoto.getFileName(),trainingPhoto.getStorePath(), req, resp);
    }
}
