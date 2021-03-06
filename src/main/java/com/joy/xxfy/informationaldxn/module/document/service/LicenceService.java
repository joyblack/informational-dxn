package com.joy.xxfy.informationaldxn.module.document.service;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.document.domain.defaults.LicenceDefault;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.LicenceEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.LicenceTypeEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.repository.LicenceRepository;
import com.joy.xxfy.informationaldxn.module.document.web.req.*;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class LicenceService {
    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 添加
     */
    public JoyResult add(LicenceAddReq req, UserEntity loginUser) {
        // 装配数据
        LicenceEntity info = new LicenceEntity();

        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);

        // 获取所属平台信息
        DepartmentEntity belongCompany = departmentRepository.findAllById(req.getBelongCompanyId());
        if(belongCompany == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }

        // 仅支持对煤矿平台添加证书
        if(belongCompany.getDepartmentType().equals(DepartmentTypeEnum.CP_GROUP)){
            return JoyResult.buildFailedResult(Notice.PERMISSION_ONLY_SUPPORT_CM);
        }
        // 检查证件信息是否已经存在
        LicenceEntity check = licenceRepository.findFirstByBelongCompanyAndLicenceType(belongCompany, info.getLicenceType());
        if(check != null){
            return JoyResult.buildFailedResult(Notice.LICENCE_ALREADY_EXIST);
        }

        // 装配其他数据
        // == 发证机关：取决于提交的类型，已经固定2个机关
        if(info.getLicenceType().equals(LicenceTypeEnum.LICENCE_SAFE_PRODUCE)){
            info.setIssueOffice(LicenceDefault.ISSUE_OFFICE_OF_SAFE_TYPE);
        }else{
            info.setIssueOffice(LicenceDefault.ISSUE_OFFICE_OF_MINING_TYPE);
        }
        // == 提示天数：若未设置有效期超时前提示天数，使用默认值
        if(info.getTipDays() == null){
            info.setTipDays(LicenceDefault.TIP_DAYS);
        }
        // == 开始提示日期
        info.setTipStartTime(DateUtil.addDay(info.getExpiryTime(), - info.getTipDays().intValue()));
        // == 设置所属平台
        info.setBelongCompany(belongCompany);
        // save.
        return JoyResult.buildSuccessResultWithData(licenceRepository.save(info));
    }

    /**
     * 改
     */
    public JoyResult update(LicenceUpdateReq req, UserEntity loginUser) {
        LicenceEntity info = licenceRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.LICENCE_NOT_EXIST);
        }
        DepartmentEntity belongCompany = departmentRepository.findAllById(req.getBelongCompanyId());
        if(belongCompany == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }
        // 检查证件信息是否已经存在
        LicenceEntity check = licenceRepository.findFirstByBelongCompanyAndLicenceType(belongCompany, req.getLicenceType());

        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        // 获取所属平台信息
        System.out.println(info);
        System.out.println(check);
        if(check != null && !check.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.LICENCE_ALREADY_EXIST);
        }
        // 装配其他数据
        // == 发证机关：取决于提交的类型，已经固定2个机关
        if(info.getLicenceType().equals(LicenceTypeEnum.LICENCE_SAFE_PRODUCE)){
            info.setIssueOffice(LicenceDefault.ISSUE_OFFICE_OF_SAFE_TYPE);
        }else{
            info.setIssueOffice(LicenceDefault.ISSUE_OFFICE_OF_MINING_TYPE);
        }
        // == 提示天数：若未设置有效期超时前提示天数，使用默认值
        if(info.getTipDays() == null){
            info.setTipDays(LicenceDefault.TIP_DAYS);
        }
        // == 开始提示日期
        info.setTipStartTime(DateUtil.addDay(info.getExpiryTime(), - info.getTipDays().intValue()));
        // == 设置所属平台
        info.setBelongCompany(belongCompany);
        // save.
        System.out.println("====================================");
        return JoyResult.buildSuccessResultWithData(licenceRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        // 信息是否存在
        LicenceEntity info = licenceRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.LICENCE_NOT_EXIST);
        }
        // 删除
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        licenceRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据：通过账户信息提取所有证照（其实就是两种）
     */
    public JoyResult getMyLicence(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.findAllByBelongCompany(loginUser.getCompany()));
    }

    /**
     * 获取数据（通过账户信息、证照类型获取证照）
     */
    public JoyResult getMyLicenceByLicenceType(UserEntity loginUser, LicenceTypeEnum licenceType) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.findFirstByBelongCompanyAndLicenceType(loginUser.getCompany(),licenceType));
    }

    /**
     * 获取数据（通过平台信息、证照类型获取证照）
     */
    public JoyResult getByBelongCompanyAndLicenceType(LicenceAddReq req) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.findFirstByBelongCompanyAndLicenceType(departmentRepository.findAllById(req.getBelongCompanyId()),req.getLicenceType()));

    }


    /**
     * 获取数据（通过ID）
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.findAllById(id));
    }

    /**
     * 获取（通过所属平台信息）
     */
    public JoyResult getByBelongCompany(Long belongCompanyId, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.findAllByBelongCompany(departmentRepository.findAllById(belongCompanyId)));
    }

    public JoyResult getApproachNum(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.getApproach(new Date()).size());
    }

    public JoyResult getApproach(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(licenceRepository.getApproach(new Date()));
    }



}
