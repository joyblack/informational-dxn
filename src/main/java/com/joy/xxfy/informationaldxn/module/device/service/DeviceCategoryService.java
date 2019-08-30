package com.joy.xxfy.informationaldxn.module.device.service;

import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceInfoEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceCategoryRepository;
import com.joy.xxfy.informationaldxn.module.device.domain.repository.DeviceInfoRepository;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceCategoryAddReq;
import com.joy.xxfy.informationaldxn.module.device.web.req.DeviceCategoryUpdateReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.DeviceCategoryConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.constant.SystemConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.project.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceCategoryService {

    @Autowired
    private DeviceCategoryRepository deviceCategoryRepository;

    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    /**
     * 新增
     */
    public JoyResult add(DeviceCategoryAddReq req, UserEntity loginUser) {
        DepartmentEntity belongCompany = loginUser.getCompany();

        // 检测父类型信息
        DeviceCategoryEntity parent = deviceCategoryRepository.findAllById(req.getParentId());
        if(!req.getParentId().equals(DeviceCategoryConstant.TOP_NODE_ID) && parent == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_PARENT_NOT_EXIST);
        }

        // 检测名字是否重复
        DeviceCategoryEntity checkRepeat = deviceCategoryRepository.findAllByBelongCompanyAndParentIdAndCategoryName(belongCompany, req.getParentId(), req.getCategoryName());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_ALREADY_EXIST);
        }

        // 修改父节点为拥有子节点的节点，不允许再作为类型而存在
        if(parent != null){
            parent.setParent(true);
            parent.setUpdateTime(new Date());
            deviceCategoryRepository.save(parent);
        }

        // 装配类型数据
        DeviceCategoryEntity category = new DeviceCategoryEntity();
        category.setParentId(req.getParentId());
        category.setParent(false);
        category.setCategoryName(req.getCategoryName());
        category.setBelongCompany(belongCompany);

        DeviceCategoryEntity save = deviceCategoryRepository.save(category);
        // 通过ID回写路径信息
        String path = getSuitPath(parent, save);
        save.setPath(path);
        return JoyResult.buildSuccessResultWithData(deviceCategoryRepository.save(save));
    }

    /**
     * 更新
     */
    public JoyResult update(DeviceCategoryUpdateReq req, UserEntity loginUser) {
        DepartmentEntity belongCompany = loginUser.getCompany();
        // 获取信息
        DeviceCategoryEntity info = deviceCategoryRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_NOT_EXIST);
        }
        // 检测名字是否重复
        DeviceCategoryEntity checkRepeat = deviceCategoryRepository.findAllByBelongCompanyAndParentIdAndCategoryName(info.getBelongCompany(), info.getParentId(), req.getCategoryName());
        if(checkRepeat != null && !checkRepeat.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_NAME_EXIST);
        }

        info.setCategoryName(req.getCategoryName());
        info.setUpdateTime(new Date());
        return JoyResult.buildSuccessResultWithData(deviceCategoryRepository.save(info));
    }

    /**
     * 获取信息
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(deviceCategoryRepository.findAllById(id));
    }

    /**
     * 删除信息
     */
    public JoyResult delete(Long id) {
        DeviceCategoryEntity info = deviceCategoryRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DEVICE_CATEGORY_NOT_EXIST);
        }
        DeviceInfoEntity check = deviceInfoRepository.findFirstByDeviceCategory(info);
        if(check != null){
            return JoyResult.buildFailedResult(Notice.DATA_IN_USED_CANT_BE_DELETE);
        }
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        deviceCategoryRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取子节点，一层
     */
    public JoyResult getChildren(Long parentId, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(deviceCategoryRepository.findAllByBelongCompanyAndParentId(loginUser.getCompany(), parentId));
    }

    // 获取树
    public JoyResult getTree(Long parentId, UserEntity loginUser) {
        DeviceCategoryEntity parent = deviceCategoryRepository.findAllById(parentId);
        String path = parent == null? SystemConstant.EMPTY_VALUE : parent.getPath();
        List<DeviceCategoryEntity> children = deviceCategoryRepository.findAllByBelongCompanyAndPathStartingWith(loginUser.getCompany(),path);
        return JoyResult.buildSuccessResultWithData(TreeUtil.getDeviceCategoryTree(children, parent == null ? 0 : parent.getId()));
    }

    /*
     * 拼接Path 1- 1-2-(if 2 is 1 child.)
     */
    private String getSuitPath(DeviceCategoryEntity parent, DeviceCategoryEntity child){
        if(parent == null){
            return child.getId() + DeviceCategoryConstant.PATH_SEPARATOR;
        }else{
            return parent.getPath()  + child.getId() +  DeviceCategoryConstant.PATH_SEPARATOR;
        }
    }


    // 获取父部门遍历树（包含自身）
    public JoyResult getParentNodes(Long id) {
        List<DeviceCategoryEntity> cs = new ArrayList<>();
        while(id != 0){
            DeviceCategoryEntity c = deviceCategoryRepository.findAllById(id);
            if(c == null){
                break;
            }
            cs.add(c);
            id = c.getParentId();
        }
        // 翻转
        Collections.reverse(cs);

        return JoyResult.buildSuccessResultWithData(cs);
    }

    // 获取父部门遍历树字符串（包含自身）
    public JoyResult getParentNodesJustIds(Long id) {
        List<DeviceCategoryEntity> cs = (List<DeviceCategoryEntity>) getParentNodes(id).getData();
        return JoyResult.buildSuccessResultWithData(cs.stream().map(d -> d.getId()).collect(Collectors.toList()));
    }


}
