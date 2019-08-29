package com.joy.xxfy.informationaldxn.module.pan.service;

import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.pan.domain.constant.FileConstant;
import com.joy.xxfy.informationaldxn.module.pan.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.pan.domain.repository.FileRepository;
import com.joy.xxfy.informationaldxn.module.pan.web.req.MkdirReq;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        DepartmentEntity belongCompany = loginUser.getCompany();
        if(req.getBelongCompanyId() != null){
            belongCompany = departmentRepository.findAllById(req.getBelongCompanyId());
        }

        // 设置文件夹信息
        FileEntity folder = new FileEntity();
        folder.setBelongCompany(belongCompany);

        return JoyResult.buildSuccessResultWithData(parentFolder);
    }
}
