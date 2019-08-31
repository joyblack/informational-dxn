package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.common.web.res.FileInfoRes;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.*;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.PersonalStatusEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.*;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.constant.StoreFilePathConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.FileUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service
public class StaffPersonalService extends BaseService {
    @Autowired
    private StaffPersonalOneInchPhotoRepository staffPersonalOneInchPhotoRepository;

    @Autowired
    private StaffPersonalIdentityPhotoRepository staffPersonalIdentityPhotoRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffLeaveRepository staffLeaveRepository;

    @Autowired
    private StaffBlacklistRepository staffBlacklistRepository;

    /**
     * 通过身份证获取个人信息
     */
    public JoyResult getPersonalByIdNumber(String idNumber) {
        return JoyResult.buildSuccessResultWithData(staffPersonalRepository.findAllByIdNumber(idNumber));
    }

    /**
     * 通过ID获取个人信息
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(staffPersonalRepository.findAllById(id));
    }

    /**
     * 根据身份证获取内部信息
     */
    public JoyResult explainIdNumber(String idNumber) {
        if(!IdNumberUtil.isIDNumber(idNumber)){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put(ResultDataConstant.BIRTH_DATE,IdNumberUtil.getBirthday(idNumber));
        data.put(ResultDataConstant.SEX,IdNumberUtil.getSex(idNumber).ordinal());
        return JoyResult.buildSuccessResultWithData(data);
    }

    public JoyResult getByUsername(String username) {
        return JoyResult.buildSuccessResultWithData(staffPersonalRepository.findAllByUsername(username));
    }

    public JoyResult uploadIdentityPhoto(MultipartFile file) {
        // 保存文件:验证格式为图片
        JoyResult result = saveModuleFile(file, StoreFilePathConstant.TRAINING_PHOTO, true);
        if(result.getState().equals(Boolean.FALSE)){
            return result;
        }
        FileInfoRes fileInfo = (FileInfoRes)result.getData();
        // 装配信息
        StaffPersonalIdentityPhotoEntity info = new StaffPersonalIdentityPhotoEntity();
        // 存储路径
        info.setStorePath(fileInfo.getFilePath()+ File.separator + fileInfo.getFilename());
        // 文件名
        info.setFileName(fileInfo.getFilename());
        // 大小
        info.setFileSize(file.getSize());
        // save
        return JoyResult.buildSuccessResultWithData(staffPersonalIdentityPhotoRepository.save(info));
    }

    public JoyResult uploadOneInchPhoto(MultipartFile file) {
        // 保存文件:验证格式为图片
        JoyResult result = saveModuleFile(file, StoreFilePathConstant.TRAINING_PHOTO, true);
        if(result.getState().equals(Boolean.FALSE)){
            return result;
        }
        FileInfoRes fileInfo = (FileInfoRes)result.getData();
        // 装配信息
        StaffPersonalOneInchPhotoEntity info = new StaffPersonalOneInchPhotoEntity();
        // 存储路径
        info.setStorePath(fileInfo.getFilePath()+ File.separator + fileInfo.getFilename());
        // 文件名
        info.setFileName(fileInfo.getFilename());
        // 大小
        info.setFileSize(file.getSize());
        // save
        return JoyResult.buildSuccessResultWithData(staffPersonalOneInchPhotoRepository.save(info));
    }


    public void downloadOneInchPhoto(Long id, HttpServletRequest req, HttpServletResponse resp) {
        StaffPersonalOneInchPhotoEntity info = staffPersonalOneInchPhotoRepository.findAllById(id);
        FileUtil.downloadFile(info.getFileName(),info.getStorePath(), req, resp);
    }

    public void downloadIdentityPhoto(Long id, HttpServletRequest req, HttpServletResponse resp) {
        StaffPersonalIdentityPhotoEntity info = staffPersonalIdentityPhotoRepository.findAllById(id);
        FileUtil.downloadFile(info.getFileName(),info.getStorePath(), req, resp);
    }

    /**
     * 通过身份证获取员工的状态
     */
    public JoyResult getStatusByIdNumber(String idNumber) {
        if(!IdNumberUtil.isIDNumber(idNumber)){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        // 是否在系统不存在
        StaffPersonalEntity personal = staffPersonalRepository.findAllByIdNumber(idNumber);
        if(personal == null){
            return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.NEVER);
        }
        // 入职状态
        List<StaffEntryEntity> entryInfo = staffEntryRepository.findAllByStaffPersonalAndReviewStatus(personal, ReviewStatusEnum.PASS);
        if(entryInfo.size() > 0){
            return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.INCUMBENCY);
        }
        // 待审核
        List<StaffEntryEntity> waitEntryInfo = staffEntryRepository.findAllByStaffPersonalAndReviewStatus(personal, ReviewStatusEnum.PASS);
        if(waitEntryInfo.size() > 0){
            return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.REVIEW_WAIT);
        }
        // 审核未通过
        List<StaffEntryEntity> notPassEntryInfo = staffEntryRepository.findAllByStaffPersonalAndReviewStatus(personal, ReviewStatusEnum.NOT_PASS);
        if(notPassEntryInfo.size() > 0){
            return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.REVIEW_NOT_PASS);
        }

        // 黑名单
        StaffBlacklistEntity staffPersonal = staffBlacklistRepository.findFirstByStaffPersonal(personal);
        if(staffPersonal != null){
            return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.BLACKLIST);
        }
        // 离职状态
        StaffLeaveEntity leaveInfo = staffLeaveRepository.findFirstByStaffPersonalOrderByCreateTimeDesc(personal);
        if(leaveInfo != null){
            return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.LEAVE);
        }

        return JoyResult.buildSuccessResultWithData(PersonalStatusEnum.NEVER);
    }
}
