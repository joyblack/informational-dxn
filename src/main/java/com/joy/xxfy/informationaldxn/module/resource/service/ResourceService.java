package com.joy.xxfy.informationaldxn.module.resource.service;

import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.department.web.req.DepartmentAddReq;
import com.joy.xxfy.informationaldxn.module.department.web.req.DepartmentUpdateReq;
import com.joy.xxfy.informationaldxn.module.resource.domain.constant.ResourceConstant;
import com.joy.xxfy.informationaldxn.module.resource.domain.defaults.ResourceDefault;
import com.joy.xxfy.informationaldxn.module.resource.domain.entity.ResourceEntity;
import com.joy.xxfy.informationaldxn.module.resource.domain.repository.ResourceRepository;
import com.joy.xxfy.informationaldxn.module.resource.web.req.ResourceAddReq;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.user.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.publish.constant.DepartmentConstant;
import com.joy.xxfy.informationaldxn.publish.constant.SystemConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResourceService extends BaseService {

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * 新增
     */
    public JoyResult add(ResourceAddReq req) {
        // check parent dept info.
        ResourceEntity parent = resourceRepository.findAllById(req.getParentId());
        if(!req.getParentId().equals(DepartmentConstant.COMPANY_PARENT_NODE_ID) && parent == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_PARENT_NOT_EXIST);
        }
        // check same name on common level.
        DepartmentEntity checkDepartment = resourceRepository.findAllByParentIdAndResourceName(req.getParentId(), req.getResourceName());
        if(checkDepartment != null){
            return JoyResult.buildFailedResult(Notice.RESOURCE_NAME_ALREADY_EXIST);
        }
        // set new entity
        ResourceEntity info = new ResourceEntity();
        // == 父节点ID
        info.setParentId(req.getParentId());
        // == 临时路径，之后刷新
        info.setPath(ResourceDefault.PATH);
        // == 资源别称
        info.setResourceAliasName(req.getResourceAliasName());
        // == 资源名称
        info.setResourceName(req.getResourceName());
        // == 资源类型
        info.setResourceType(req.getResourceType());
        info.setResourceUrl(req.getResourceUrl());
        // save
        ResourceEntity save = resourceRepository.save(info);
        // update path
        save.setPath(getSuitPath(parent, save));
        return JoyResult.buildSuccessResultWithData(resourceRepository.save(save));
    }

    /**
     * 获取
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(resourceRepository.findAllById(id));
    }


    /**
     * 获取部门子节点，一层
     */
    public JoyResult getChildren(Long parentId) {
        return JoyResult.buildSuccessResultWithData(resourceRepository.findAllByParentId(parentId));
    }

    // 获取树
    public JoyResult getTree(Long parentId,UserEntity loginUser) {
        ResourceEntity resourceEntity = resourceRepository.findAllById(parentId);
        String path = resourceEntity == null? ResourceDefault.PATH : resourceEntity.getPath();
        List<DepartmentEntity> children = resourceRepository.findAllByPathStartingWith(path);
        return JoyResult.buildSuccessResultWithData(TreeUtil.getDeptTree(children, resourceEntity == null ? 0 : resourceEntity.getId()));
    }

    /*
     * 拼接Path 1- 1-2-(if 2 is 1 child.)
     */
    private String getSuitPath(ResourceEntity parent, ResourceEntity child){
        if(parent == null){
            return child.getId() + ResourceConstant.PATH_SEPARATOR;
        }else{
            return parent.getPath()  + child.getId() +   ResourceConstant.PATH_SEPARATOR;
        }
    }

}
