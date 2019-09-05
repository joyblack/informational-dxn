package com.joy.xxfy.informationaldxn.module.device.service;

import com.joy.xxfy.informationaldxn.module.common.enums.LimitUserTypeEnum;
import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceMaintainEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.MaintainStatusEnum;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceCategoryRepository;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceInfoRepository;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceMaintainRepository;
import com.joy.xxfy.informationaldxn.module.device.web.req.*;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.permission.constant.ResourceIdConfig;
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
public class DeviceMaintainService extends BaseService {
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    @Autowired
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    private DeviceMaintainRepository deviceMaintainRepository;

    /**
     * 添加
     */
    public JoyResult add(DeviceMaintainAddReq req, UserEntity loginUser) {
        if(!hasPermission(loginUser, ResourceIdConfig.DEVICE_MAINTAIN_ADD, LimitUserTypeEnum.CM_ADMIN)){
            return JoyResult.buildFailedResult(Notice.PERMISSION_FORBIDDEN);
        }
        // 设备信息是否存在
        DeviceInfoEntity deviceInfo = deviceInfoRepository.findAllById(req.getDeviceInfoId());
        if(deviceInfo == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_INFO_NOT_EXIST);
        }
        DeviceMaintainEntity deviceMaintainInfo = new DeviceMaintainEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, deviceMaintainInfo);
        // 更新设备的修改时间
        deviceInfo.setUpdateTime(new Date());
        // 若是维保完成，设置为在用状态，维保次数 + 1
        // 若是维保未完成，设置为维保状态；
        if(req.getMaintainStatus().equals(MaintainStatusEnum.COMPLETE)){
            deviceInfo.setMaintainNumber(deviceInfo.getMaintainNumber() + 1);
            deviceInfo.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_USING);
        }else{
            deviceInfo.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_MAINTAINING);
        }
        // 更新最近一次维保时间
        deviceInfo.setBeforeMaintainTime(req.getMaintainTime());
        // 下次保养日期，取决于是否同时填写保养间隔时间、最近保养时间
        // 更新下次提示的时间
        if(deviceInfo.getBeforeMaintainTime()!= null && deviceInfo.getMaintainIntervalTime() != null){
            deviceInfo.setNextMaintainTime(DateUtil.addDay(deviceInfo.getBeforeMaintainTime(),deviceInfo.getMaintainIntervalTime().intValue()));
            deviceInfo.setTipStartTime(DateUtil.addDay(deviceInfo.getNextMaintainTime(), - deviceInfo.getTipDays().intValue()));
        }else{
            // 置空，此条目也不会进入统计范畴。
            deviceInfo.setTipStartTime(null);
        }
        deviceMaintainInfo.setDeviceInfo(deviceInfo);
        // 将所有关于该设备的旧未完成的记录更新为已完成
        deviceMaintainRepository.updateCompletedByDeviceInfo(deviceInfo, MaintainStatusEnum.COMPLETE);
        // 插入新纪录
        DeviceMaintainEntity save = deviceMaintainRepository.save(deviceMaintainInfo);
        return JoyResult.buildSuccessResultWithData(save);
    }

    /**
     * 改
     */
    public JoyResult update(DeviceMaintainUpdateReq req, UserEntity loginUser) {
        if(!hasPermission(loginUser, ResourceIdConfig.DEVICE_MAINTAIN_UPDATE, LimitUserTypeEnum.CM_ADMIN)){
            return JoyResult.buildFailedResult(Notice.PERMISSION_FORBIDDEN);
        }
        // 设备信息是否存在
        DeviceMaintainEntity maintainInfo = deviceMaintainRepository.findAllById(req.getId());
        if(maintainInfo == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_MAINTAIN_NOT_EXIST);
        }
        // 关联的设备信息
        DeviceInfoEntity deviceInfo = maintainInfo.getDeviceInfo();
        // 获取设备最新一条维保信息，若当前修改的不是最新记录，则无需修改关联设备的信息。
        // 用户可能只是修改一条维保记录的状态而已。
        DeviceMaintainEntity newestMaintainInfo = deviceMaintainRepository.findFirstByDeviceInfoOrderByMaintainTimeDesc(deviceInfo);
        if(newestMaintainInfo.getId().equals(maintainInfo.getId())){
            // 更新设备的修改时间
            deviceInfo.setUpdateTime(new Date());
            // 若是维保完成，设置为在用状态，维保次数+1
            // 若是维保未完成，设置为维保状态；
            if(req.getMaintainStatus().equals(MaintainStatusEnum.COMPLETE)){
                deviceInfo.setMaintainNumber(deviceInfo.getMaintainNumber() + 1);
                deviceInfo.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_USING);
            }else{
                deviceInfo.setDeviceStatus(DeviceStatusEnum.DEVICE_STATUS_MAINTAINING);
            }
            // 更新最近一次维保时间
            deviceInfo.setBeforeMaintainTime(req.getMaintainTime());
            // 下次保养日期，取决于是否同时填写保养间隔时间、最近保养时间
            // 更新下次提示维保的时间
            if(deviceInfo.getBeforeMaintainTime()!= null && deviceInfo.getMaintainIntervalTime() != null){
                deviceInfo.setNextMaintainTime(DateUtil.addDay(deviceInfo.getBeforeMaintainTime(),deviceInfo.getMaintainIntervalTime().intValue()));
                deviceInfo.setTipStartTime(DateUtil.addDay(deviceInfo.getNextMaintainTime(), - deviceInfo.getTipDays().intValue()));
            }else{
                // 置空，此条目也不会进入统计范畴。
                deviceInfo.setTipStartTime(null);
            }
        }
        // 拷贝修改信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, maintainInfo);
        // 修改
        return JoyResult.buildSuccessResultWithData(deviceMaintainRepository.save(maintainInfo));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        if(!hasPermission(loginUser, ResourceIdConfig.DEVICE_MAINTAIN_DELETE, LimitUserTypeEnum.CM_ADMIN)){
            return JoyResult.buildFailedResult(Notice.PERMISSION_FORBIDDEN);
        }
        DeviceMaintainEntity info = deviceMaintainRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_MAINTAIN_NOT_EXIST);
        }
        // 删除
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        deviceMaintainRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceMaintainRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DeviceMaintainGetListReq req, UserEntity loginUser) {
        if(!hasPermission(loginUser, ResourceIdConfig.DEVICE_INFO_GET_LIST, LimitUserTypeEnum.CM_ADMIN)){
            return JoyResult.buildFailedResult(Notice.PERMISSION_FORBIDDEN);
        }
        return JoyResult.buildSuccessResultWithData(deviceMaintainRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DeviceMaintainGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceMaintainRepository.findAll(getPredicates(req, loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DeviceMaintainEntity> getPredicates(DeviceMaintainGetListReq req, UserEntity loginUser){
        return (Specification<DeviceMaintainEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("deviceInfo").get("belongCompany"),loginUser.getCompany()));
            // 设备名称
            if(!StringUtil.isEmpty(req.getDeviceName())){
                predicates.add(builder.like(root.get("deviceInfo").get("deviceName"), "%" + req.getDeviceName() +"%"));
            }
            // 设备分类
            if(req.getDeviceCategoryId() != null){
                predicates.add(builder.equal(root.get("deviceInfo").get("deviceCategory").get("id"), req.getDeviceCategoryId()));
            }
            // 规格型号
            if(!StringUtil.isEmpty(req.getDeviceModel())){
                predicates.add(builder.like(root.get("deviceInfo").get("deviceModel"), "%" + req.getDeviceModel() +"%"));
            }
            // 生产厂家
            if(!StringUtil.isEmpty(req.getManufacture())){
                predicates.add(builder.like(root.get("deviceInfo").get("manufacture"), "%" + req.getManufacture() +"%"));
            }
            // 设备编号
            if(!StringUtil.isEmpty(req.getDeviceCode())){
                predicates.add(builder.like(root.get("deviceInfo").get("deviceCode"), "%" + req.getDeviceCode() +"%"));
            }
            // 出厂日期 time_between
            if(req.getMaintainTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("maintainTime"), req.getMaintainTimeStart()));
            }
            if(req.getMaintainTimeEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("maintainTime"), req.getMaintainTimeEnd()));
            }
            // 维保类型
            if(req.getMaintainType() != null){
                predicates.add(builder.equal(root.get("maintainType"), req.getMaintainType()));
            }
            // 维保情况
            if(req.getMaintainStatus() != null){
                predicates.add(builder.equal(root.get("maintainStatus"), req.getMaintainStatus()));
            }
            // 状态
            if(req.getMaintainPeople() != null){
                predicates.add(builder.like(root.get("maintainPeople"), "%" + req.getMaintainPeople() +"%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
