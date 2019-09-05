package com.joy.xxfy.informationaldxn.module.device.service;

import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.defaults.DeviceInfoDefault;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceMaintainEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceCategoryRepository;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceInfoRepository;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceMaintainRepository;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoAddReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoChangeStatusReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoGetListReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceInfoUpdateReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DeviceInfoService extends BaseService {
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    private DeviceMaintainRepository deviceMaintainRepository;

    /**
     * 添加
     */
    public JoyResult add(DeviceInfoAddReq req, UserEntity loginUser) {
        DepartmentEntity belongCompany = loginUser.getCompany();
        // 获取设备类型
        DeviceCategoryEntity category = deviceCategoryRepository.findAllById(req.getDeviceCategoryId());
        if(category == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_NOT_EXIST);
        }
        // 设备数量
        Long deviceCount = req.getDeviceCount();
        String nowDeviceName = req.getDeviceName();
        int orderNumber = 1;
        List<DeviceInfoEntity> devices = new ArrayList<>();
        for (long i = 0; i < deviceCount; i++){
            DeviceInfoEntity check = deviceInfoRepository.findFirstByBelongCompanyAndDeviceName(belongCompany, nowDeviceName);
            while(check != null){
                orderNumber ++;
                // 为1则无需添加后缀信息
                nowDeviceName = req.getDeviceName() + DeviceInfoDefault.DEVICE_NAME_SEPARATE + orderNumber;
                check = deviceInfoRepository.findFirstByBelongCompanyAndDeviceName(belongCompany, nowDeviceName);
            }
            DeviceInfoEntity device = new DeviceInfoEntity();
            // 设备类型
            device.setDeviceCategory(category);
            // 复制所有信息
            JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, device);
            // 显示名称
            device.setDeviceName(nowDeviceName);
            // 所属平台
            device.setBelongCompany(belongCompany);
            // 维保次数
            device.setMaintainNumber(DeviceInfoDefault.maintainNumber);
            // 超时前提示天数
            if(device.getTipDays() == null){
                device.setTipDays(DeviceInfoDefault.tipDays);
            }
            // 设备状态
            device.setDeviceStatus(DeviceInfoDefault.deviceStatus);

            // 下次保养日期，取决于是否同时填写保养间隔时间、最近保养时间
            // 填写了下次保养日期，那么开始提示日期也会生效。
            if(device.getBeforeMaintainTime()!= null && device.getMaintainIntervalTime() != null){
                device.setNextMaintainTime(DateUtil.addDay(device.getBeforeMaintainTime(),device.getMaintainIntervalTime().intValue()));
                device.setTipStartTime(DateUtil.addDay(device.getNextMaintainTime(), - device.getTipDays().intValue()));
            }else{
                // 置空，此条目也不会进入统计范畴。
                device.setTipStartTime(null);
            }
            deviceInfoRepository.save(device);
        }
        // save.
        return JoyResult.buildSuccessResultWithData(req);
    }

    /**
     * 改
     */
    public JoyResult update(DeviceInfoUpdateReq req, UserEntity loginUser) {
        // 信息是否存在
        DeviceInfoEntity info = deviceInfoRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_INFO_NOT_EXIST);
        }
        // 获取设备类型
        DeviceCategoryEntity category = deviceCategoryRepository.findAllById(req.getDeviceCategoryId());
        if(category == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_NOT_EXIST);
        }
        // 获取
        // 名字是否有变化
        String deviceName = req.getDeviceName();
        int orderNumber = 1;
        if(!req.getDeviceName().equals(info.getDeviceName())){
            DeviceInfoEntity check = deviceInfoRepository.findFirstByBelongCompanyAndDeviceName(info.getBelongCompany(), req.getDeviceName());
            if(check != null){
                orderNumber ++;
                // 为0则无需添加后缀信息
                deviceName = req.getDeviceName() + DeviceInfoDefault.DEVICE_NAME_SEPARATE + orderNumber;
                check = deviceInfoRepository.findFirstByBelongCompanyAndDeviceName(info.getBelongCompany(), deviceName);
            }
        }

        // copy
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        // 名字
        info.setDeviceName(deviceName);
        // 类别
        info.setDeviceCategory(category);
        // 修改时间
        info.setUpdateTime(new Date());
        if(info.getTipDays() == null){
            info.setTipDays(DeviceInfoDefault.tipDays);
        }
        // 下次保养日期，取决于是否同时填写保养间隔时间、最近保养时间
        if(info.getBeforeMaintainTime()!= null && info.getMaintainIntervalTime() != null){
            info.setNextMaintainTime(DateUtil.addDay(info.getBeforeMaintainTime(),info.getMaintainIntervalTime().intValue()));
            info.setTipStartTime(DateUtil.addDay(info.getNextMaintainTime(), - info.getTipDays().intValue()));
        }else{
            // 置空，此条目也不会进入统计范畴。
            info.setTipStartTime(null);
        }
        // save.
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        DeviceInfoEntity info = deviceInfoRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_INFO_NOT_EXIST);
        }
        // 是否处于正在使用状态，或者把这些信息也给删除了？
        DeviceMaintainEntity maintainInfo = deviceMaintainRepository.findFirstByDeviceInfo(info);
        if(maintainInfo != null){
            return JoyResult.buildFailedResult(Notice.DATA_IN_USED_CANT_BE_DELETE);
        }
        // 删除
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        deviceInfoRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DeviceInfoGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DeviceInfoGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.findAll(getPredicates(req, loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DeviceInfoEntity> getPredicates(DeviceInfoGetListReq req, UserEntity loginUser){
        return (Specification<DeviceInfoEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("belongCompany"),loginUser.getCompany()));

            // 设备名称
            if(!StringUtil.isEmpty(req.getDeviceName())){
                predicates.add(builder.like(root.get("deviceName"), "%" + req.getDeviceName() +"%"));
            }
            // 设备分类
            if(req.getDeviceCategoryId() != null){
                predicates.add(builder.equal(root.get("deviceCategory").get("id"), req.getDeviceCategoryId()));
            }
            // 规格型号
            if(!StringUtil.isEmpty(req.getDeviceModel())){
                predicates.add(builder.like(root.get("deviceModel"), "%" + req.getDeviceModel() +"%"));
            }
            // 生产厂家
            if(!StringUtil.isEmpty(req.getManufacture())){
                predicates.add(builder.like(root.get("manufacture"), "%" + req.getManufacture() +"%"));
            }
            // 设备编号
            if(!StringUtil.isEmpty(req.getDeviceCode())){
                predicates.add(builder.like(root.get("deviceCode"), "%" + req.getDeviceCode() +"%"));
            }
            // 出厂日期 time_between
            if(req.getManufactureTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("manufactureTime"), req.getManufactureTimeStart()));
            }
            if(req.getManufactureTimeEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("manufactureTime"), req.getManufactureTimeEnd()));
            }
            // 使用地点
            if(!StringUtil.isEmpty(req.getUsePlace())){
                predicates.add(builder.like(root.get("usePlace"), "%" + req.getUsePlace() +"%"));
            }
            // 状态
            if(req.getDeviceStatus() != null){
                predicates.add(builder.equal(root.get("deviceStatus"), req.getDeviceStatus()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


    /**
     * 变更设备状态
     */
    public JoyResult changeDeviceStatus(DeviceInfoChangeStatusReq req, UserEntity loginUser) {
        List<DeviceInfoEntity> devices = new ArrayList<>();
        // 获取每一条信息
        for (Long id : req.getIds()) {
            DeviceInfoEntity info = deviceInfoRepository.findAllById(id);
            if(info != null){
                devices.add(info);
            }
        }
        if(devices.size() == 0){
            return JoyResult.buildFailedResult(Notice.DEVICE_INFO_NOT_EXIST);
        }
        // 修改状态
        // 当前时间
        Date now = new Date();
        for (DeviceInfoEntity device : devices) {
            device.setDeviceStatus(req.getDeviceStatus());
            device.setUpdateTime(new Date());
        }
        // 保存所有信息
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.saveAll(devices));
    }

    public JoyResult whetherExistSameNameDevice(DeviceInfoAddReq req, UserEntity loginUser) {
        DeviceInfoEntity check = deviceInfoRepository.findFirstByBelongCompanyAndDeviceName(loginUser.getCompany(), req.getDeviceName());
        if(check != null){
            return JoyResult.buildSuccessResultWithData(check, ResultDataConstant.SAME_NAME_DEVICE_EXIST);
        }else{
            return JoyResult.buildFailedResult(Notice.DEVICE_INFO_NAME_NOT_EXIST);
        }
    }

    public JoyResult getApproachNum(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.getApproach(new Date()).size());
    }

    public JoyResult getApproach(UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceInfoRepository.getApproach(new Date()));
    }
}
