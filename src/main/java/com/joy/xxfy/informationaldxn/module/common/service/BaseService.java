package com.joy.xxfy.informationaldxn.module.common.service;

import com.joy.xxfy.informationaldxn.module.common.enums.LimitUserTypeEnum;
import com.joy.xxfy.informationaldxn.module.common.web.res.FileInfoRes;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Transactional
@Service
public class BaseService {
    // 所有文件存储的顶层目录
    @Value("${system.upload.store-path}")
    private String storePath;

    @Value("${system.upload.image-format}")
    private String imageFormat;

    // 配置文件中配置的顶层目录的分隔符
    private final static String FILE_SPLIT = "&&";

    /**
     * 存储文件
     */
    public JoyResult saveModuleFile(MultipartFile file, String moduleName, boolean isImage){
        String name = file.getOriginalFilename();
        String[] names = name.split("\\.");
        if (null == names || names.length == 0) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_ERROR);
        }
        // 验证图片类型
        if (isImage && !FileUtil.fileNamePostfixCheck(imageFormat, names[names.length - 1])) {
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_TYPE_ERROR);
        }
        // uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 保存文件名 uuid.ext.
        String filename = uuid + "." + names[1];
        // 替换为符合当前操作系统的路径分隔符顶层路径
        String[] filePaths = storePath.split(FILE_SPLIT);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : filePaths) {
            stringBuffer.append(s + File.separator);
        }
        // 配置路径/StoreFilePathConstant静态配置
        String fileFinalPath = stringBuffer.toString() + moduleName;
        // 保存上传文件
        try {
            FileUtil.save(file.getBytes(), fileFinalPath, filename);
            // 设置返回数据
            FileInfoRes res = new FileInfoRes();
            res.setFilename(filename);
            res.setFilePath(fileFinalPath);
            return JoyResult.buildSuccessResultWithData(res);
        } catch (IOException e) {
            e.printStackTrace();
            return JoyResult.buildFailedResult(Notice.UPLOAD_FILE_ERROR);
        }
    }

    /**
     * 检测用户是否拥有访问权限
     */
    public boolean hasPermission(UserEntity user, Long resourceId, LimitUserTypeEnum limitUserType){
        // 若需要限制类型的用户访问，要先验证是否符合角色类型
        // 目前平台有两大类: 集团、煤矿平台
        if(!limitUserType.equals(LimitUserTypeEnum.ALL) && !user.getUserType().name().equals(limitUserType.name())){
            return false;
        }
        // 为空代表拥有所有权限
        if(StringUtil.isEmpty(user.getPermissions())){
            return true;
        }
        if(Arrays.asList(user.getPermissions().split("-")).contains(resourceId.toString())){
            return true;
        }
        return false;
    }
}
