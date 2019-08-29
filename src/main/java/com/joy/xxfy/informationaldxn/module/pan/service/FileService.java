package com.joy.xxfy.informationaldxn.module.pan.service;

import com.joy.xxfy.informationaldxn.module.common.web.res.FileInfoRes;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.pan.domain.constant.FileConstant;
import com.joy.xxfy.informationaldxn.module.pan.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.pan.domain.repository.FileRepository;
import com.joy.xxfy.informationaldxn.module.pan.web.req.MkdirReq;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingPhotoEntity;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.user.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.publish.constant.StoreFilePathConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Transactional
@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 创建文件夹
     */
    public JoyResult mkdir(MkdirReq req, UserEntity loginUser) {
        // 获取父文件夹信息
        Long parentId = req.getParentId();
        FileEntity parentFolder = fileRepository.findAllByIdAndIsFolder(parentId, true);
        if(parentFolder == null && !parentId.equals(FileConstant.TOP_NODE_ID)){
            return JoyResult.buildFailedResult(Notice.PAN_PARENT_FOLDER_NOT_EXIST);
        }
        // 若是公共模块，则需要提交所属公司ID
        DepartmentEntity belongCompany = loginUser.getCompany();
        if(req.getBelongCompanyId() != null){
            belongCompany = departmentRepository.findAllById(req.getBelongCompanyId());
        }
        if(belongCompany == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }

        // 设置文件夹信息
        FileEntity folder = new FileEntity();
        // 所属平台/公司
        folder.setBelongCompany(belongCompany);
        // 创建者
        folder.setCreateUser(loginUser);
        // 父文件夹ID
        folder.setParentId(req.getParentId());
        // 原始名称
        folder.setNewFileName(req.getFileName());
        // 文件名称
        folder.setFileName(req.getFileName());
        // 文件大小
        folder.setFileSize(FileConstant.FILE_SIZE);
        // 文件格式
        folder.setFileType(null);
        // 文件路径 p_path/name
        folder.setPath(parentFolder==null? (FileConstant.PATH_SEPARATE + req.getFileName()) : (parentFolder.getPath() + FileConstant.PATH_SEPARATE + req.getFileName()));
        // 存储路径
        folder.setStorePath(null);
        // 权限类型:以父文件夹的为准，若不存在，则以请求的参数为准
        folder.setPermissionType(parentFolder == null? parentFolder.getPermissionType(): req.getPermissionType());
        // 是否为文件夹
        folder.setIsFolder(true);
        return JoyResult.buildSuccessResultWithData(fileRepository.save(parentFolder));
    }


//    /**
//     * 上传资料
//     */
//    public JoyResult upload(MultipartFile file, Long parentId) {
//        TrainingEntity trainingEntity = trainingRepository.findAllById(id);
//        if(trainingEntity == null){
//            return JoyResult.buildFailedResult(Notice.TRAINING_NOT_EXIST);
//        }
//        // 保存文件:验证格式为图片
//        JoyResult result = saveModuleFile(file, StoreFilePathConstant.TRAINING_PHOTO, true);
//        if(result.getState().equals(Boolean.FALSE)){
//            return result;
//        }
//        FileInfoRes fileInfo = (FileInfoRes)result.getData();
//        // 装配信息
//        TrainingPhotoEntity trainingPhoto = new TrainingPhotoEntity();
//        // 存储路径
//        trainingPhoto.setStorePath(fileInfo.getFilePath()+ File.separator + fileInfo.getFilename());
//        // 文件名
//        trainingPhoto.setFileName(fileInfo.getFilename());
//        // 大小
//        trainingPhoto.setFileSize(file.getSize());
//        // 所属记录
//        trainingPhoto.setTraining(trainingEntity);
//        // 原始名字
//        trainingPhoto.setOriginalName(file.getOriginalFilename());
//        // save
//        return JoyResult.buildSuccessResultWithData(trainingPhotoRepository.save(trainingPhoto));
//    }

    /**
     * 下载文件
     */
    public void download(Long id, HttpServletRequest req, HttpServletResponse resp) {
        FileEntity file = fileRepository.findAllById(id);
        FileUtil.downloadFile(file.getFileName(),file.getStorePath(), req, resp);
    }
}
