package com.joy.xxfy.informationaldxn.module.train.service;

import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.common.web.res.FileInfoRes;
import com.joy.xxfy.informationaldxn.publish.constant.StoreFilePathConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingAttachmentEntity;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.module.train.domain.repository.TrainingAttachmentRepository;
import com.joy.xxfy.informationaldxn.module.train.domain.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Service
public class TrainingAttachmentService extends BaseService {
    @Autowired
    private TrainingAttachmentRepository trainingAttachmentRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    public JoyResult upload(MultipartFile file, Long id) {
        TrainingEntity trainingEntity = trainingRepository.findAllById(id);
        if(trainingEntity == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NOT_EXIST);
        }
        // 保存文件
        JoyResult result = saveModuleFile(file, StoreFilePathConstant.TRAINING_ATTACHMENT, false);
        if(result.getState().equals(Boolean.FALSE)){
            return result;
        }
        FileInfoRes fileInfo = (FileInfoRes)result.getData();
        // 装配信息
        TrainingAttachmentEntity trainingAttachment = new TrainingAttachmentEntity();
        // 存储路径
        trainingAttachment.setStorePath(fileInfo.getFilePath()+ File.separator + fileInfo.getFilename());
        // 文件名
        trainingAttachment.setFileName(fileInfo.getFilename());
        // 大小
        trainingAttachment.setFileSize(file.getSize());
        // 所属记录
        trainingAttachment.setTraining(trainingEntity);
        // save
        return JoyResult.buildSuccessResultWithData(trainingAttachmentRepository.save(trainingAttachment));
    }

    public void download(Long id, HttpServletRequest req, HttpServletResponse resp) {
        TrainingAttachmentEntity trainingAttachment = trainingAttachmentRepository.findAllById(id);
        FileUtil.downloadFile(trainingAttachment.getFileName(),trainingAttachment.getStorePath(), req, resp);
    }
}
