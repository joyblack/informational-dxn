package com.joy.xxfy.informationaldxn.file.service;

import com.joy.xxfy.informationaldxn.file.domain.entity.SystemFileEntity;
import com.joy.xxfy.informationaldxn.file.domain.repository.SystemFileRepository;
import com.joy.xxfy.informationaldxn.file.enums.UploadModuleEnums;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
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
public class FileService {
    @Value("${file.path}")
    private String storeDictionary;

    private final static String FILE_SPLIT = "&&";

    @Autowired
    private SystemFileRepository systemFileRepository;



    public JoyResult upload(MultipartFile file, String module) {
        SystemFileEntity systemFileEntity = new SystemFileEntity();
        try{
            // 设置实体: upload_module
            systemFileEntity.setUploadModule(UploadModuleEnums.valueOf(module));
        }catch (Exception e){
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_MODULE_ERROR);
        }

        // 处理上传文件
        // 检查文件
        String name = file.getOriginalFilename();
        String[] names = name.split("\\.");
        if (null == names || names.length == 0) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_ERROR);
        }
        // uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 保存文件名 uuid.extension.
        String fileName = uuid + "." + names[1];
        // 保存路径：公共存储目录+ Path
        String[] filePaths = storeDictionary.split(FILE_SPLIT);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : filePaths) {
            stringBuffer.append(s + File.separator);
        }
        // E:/file/STAFF_ID_NUMBER_PHOTO
        String fileFinalPath = stringBuffer.toString() + systemFileEntity.getUploadModule().name();
        // 设置实体: storePath
        // E:/file/STAFF_ID_NUMBER_PHOTO/1.jpg
        systemFileEntity.setStorePath(fileFinalPath + File.separator + fileName);
        // 保存上传文件
        try {
            FileUtil.save(file.getBytes(), fileFinalPath, fileName);
            // 文件名
            systemFileEntity.setFileName(fileName);
            // 大小
            systemFileEntity.setFileSize(file.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_ERROR);
        }
        // save
        return JoyResult.buildSuccessResultWithData(systemFileRepository.save(systemFileEntity));
    }



    public void download(Long id, HttpServletRequest req, HttpServletResponse resp) {
        SystemFileEntity file = systemFileRepository.findAllById(id);
        // JOY_NEXT: This name maybe write be the third party? Now just set with file name.
        FileUtil.downloadFile(file.getFileName(),file.getStorePath(), req, resp);
    }
}
