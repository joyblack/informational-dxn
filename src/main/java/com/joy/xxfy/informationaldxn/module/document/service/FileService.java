package com.joy.xxfy.informationaldxn.module.document.service;

import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.common.web.res.FileInfoRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.document.domain.constant.FileConstant;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.repository.FileRepository;
import com.joy.xxfy.informationaldxn.module.document.web.req.FileGetListReq;
import com.joy.xxfy.informationaldxn.module.document.web.req.GetTreeReq;
import com.joy.xxfy.informationaldxn.module.document.web.req.MkdirReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.constant.StoreFilePathConstant;
import com.joy.xxfy.informationaldxn.publish.constant.SystemConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class FileService extends BaseService {
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
        FileEntity parentFolder = fileRepository.findFirstByIdAndIsFolder(parentId, true);
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
        // 判断是否重名
        FileEntity same = fileRepository.findFirstByPermissionTypeAndBelongCompanyAndParentIdAndFileName(req.getPermissionType(),belongCompany, parentId, req.getFileName());
        if(same != null){
            return JoyResult.buildFailedResult(Notice.PAN_FILE_NAME_ALREADY_EXIST);
        }

        // 设置文件夹信息
        FileEntity folder = new FileEntity();
        // 所属平台/公司
        folder.setBelongCompany(belongCompany);
        // 创建者
        folder.setCreateUser(loginUser);
        // 父文件夹ID
        folder.setParentId(req.getParentId());
        // 新名称
        folder.setNewFileName(req.getFileName());
        // 文件名称
        folder.setFileName(req.getFileName());
        // 文件大小
        folder.setFileSize(null);
        // 文件格式
        folder.setFileType(null);
        // 文件路径 p_path/name
        folder.setPath(parentFolder==null? (FileConstant.PATH_SEPARATE + req.getFileName()) : (parentFolder.getPath() + FileConstant.PATH_SEPARATE + req.getFileName()));
        // 存储路径
        folder.setStorePath("");
        // 权限类型:以父文件夹的为准，若不存在，则以请求的参数为准
        folder.setPermissionType(parentFolder == null? req.getPermissionType():parentFolder.getPermissionType());

        if(!folder.getPermissionType().equals(PermissionTypeEnum.PER_PUBLIC) && !folder.getPermissionType().equals(PermissionTypeEnum.PER_PRIVATE)){
            return JoyResult.buildFailedResult(Notice.NOT_SUPPORT_THIS_PERMISSION_TYPE);
        }
        // 是否为文件夹
        folder.setIsFolder(true);
        // 创建者
        folder.setCreateUser(loginUser);
        return JoyResult.buildSuccessResultWithData(fileRepository.save(folder));
    }


    /**
     * 上传资料
     */
    public JoyResult upload(MultipartFile file, Long parentId, UserEntity loginUser) {
        // 获取父文件夹信息
        FileEntity parentFolder = fileRepository.findFirstByIdAndIsFolder(parentId, true);
        if(parentFolder == null){
            return JoyResult.buildFailedResult(Notice.PAN_PARENT_FOLDER_NOT_EXIST);
        }
        // 所属模块继承父文件夹
        DepartmentEntity belongCompany = parentFolder.getBelongCompany();

        // 设置存储目录
        // 模块名+一层目录：pan_public/所属公司IDor用户ID
        String moduleName = "";
        if(parentFolder.getPermissionType().equals(PermissionTypeEnum.PER_PRIVATE)){
            moduleName += StoreFilePathConstant.PAN_PRIVATE + File.separator + belongCompany.getId();
        }else if(parentFolder.getPermissionType().equals(PermissionTypeEnum.PER_PUBLIC)){
            moduleName += StoreFilePathConstant.PAN_PUBLIC + File.separator + belongCompany.getId();
        }else{
            return JoyResult.buildFailedResult(Notice.NOT_SUPPORT_THIS_PERMISSION_TYPE);
        }
        // 保存文件
        JoyResult result = saveModuleFile(file, moduleName, false);
        if(result.getState().equals(Boolean.FALSE)){
            return result;
        }
        FileInfoRes fileInfo = (FileInfoRes)result.getData();
        // 设置文件信息
        FileEntity fileEntity = new FileEntity();
        // 所属平台/公司
        fileEntity.setBelongCompany(belongCompany);
        // 创建者
        fileEntity.setCreateUser(loginUser);
        // 父文件夹ID
        fileEntity.setParentId(parentFolder.getId());
        // 新名称
        fileEntity.setNewFileName(fileInfo.getFilename());
        // 文件名称
        fileEntity.setFileName(file.getOriginalFilename());
        // 文件大小
        fileEntity.setFileSize(file.getSize());
        // 文件格式
        fileEntity.setFileType(FileUtil.getFileExtension(fileEntity.getFileName()));
        // 文件路径 p_path/name
        fileEntity.setPath(parentFolder.getPath() + FileConstant.PATH_SEPARATE + fileEntity.getFileName());
        // 存储路径
        fileEntity.setStorePath(fileInfo.getFilePath());
        // 权限类型:以父文件夹的为准
        fileEntity.setPermissionType(parentFolder.getPermissionType());
        // 是否为文件夹
        fileEntity.setIsFolder(false);
        // 创建者
        fileEntity.setCreateUser(loginUser);
        // 上传时间
        fileEntity.setUpdateTime(DateUtil.getDateJustYMD());
        // save
        return JoyResult.buildSuccessResultWithData(fileRepository.save(fileEntity));
    }

    /**
     * 下载文件
     */
    public void download(Long id, HttpServletRequest req, HttpServletResponse resp) {
        FileEntity file = fileRepository.findAllById(id);
        FileUtil.downloadFile(file.getFileName(),file.getStorePath(), req, resp);
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser){
        FileEntity info = fileRepository.findAllById(id);

        if(info == null){
            return JoyResult.buildFailedResult(Notice.PAN_FILE_NOT_EXIST);
        }
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        fileRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取
     */
    public JoyResult get(Long id, UserEntity loginUser){
        return JoyResult.buildSuccessResultWithData(fileRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(FileGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(fileRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(FileGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(fileRepository.findAll(getPredicates(req,loginUser)));
    }

    // 获取具体某个平台/集团的树
    public JoyResult getTree(GetTreeReq req, UserEntity loginUser){
        DepartmentEntity company = departmentRepository.findAllById(req.getBelongCompanyId());
        FileEntity topFile = fileRepository.findAllById(req.getId());
        String path = topFile == null? SystemConstant.EMPTY_VALUE : topFile.getPath();
        List<FileEntity> children = fileRepository.findAllByPathStartingWithAndBelongCompanyAndIsFolderAndPermissionType(path, company, true,req.getPermissionType());
        return JoyResult.buildSuccessResultWithData(TreeUtil.getFileTree(children, topFile == null? 0L:topFile.getId()));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<FileEntity> getPredicates(FileGetListReq req, UserEntity loginUser){
        return (Specification<FileEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 必要条件
            // 父文件夹ID
            predicates.add(builder.equal(root.get("parentId"), req.getParentId()));
            // 权限类型：私有 or 公开
            predicates.add(builder.equal(root.get("permissionType"), req.getPermissionType()));
            // 只返回文件列表
            predicates.add(builder.equal(root.get("isFolder"), false));
            // 若为私有，则还要添加该用户的所属信息，定位私有文件
            if(req.getPermissionType().equals(PermissionTypeEnum.PER_PRIVATE)){
                predicates.add(builder.equal(root.get("createUser"), loginUser));
            }
            // 所属平台/公司
            if(req.getBelongCompanyId() != null){
                predicates.add(builder.equal(root.get("belongCompany").get("id"), req.getBelongCompanyId()));
            }
            // 区分文件（夹）
            if(req.getIsFolder() != null){
                predicates.add(builder.equal(root.get("isFolder"), req.getIsFolder()));
            }
            // 文件名称
            if(!StringUtil.isEmpty(req.getFileName())){
                predicates.add(builder.like(root.get("fileName"), "%" + req.getFileName() +"%"));
            }

            // injury_time between
            if(req.getCreateTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("createTime"), req.getCreateTimeStart()));
            }
            if(req.getCreateTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("createTime"), req.getCreateTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }




}
