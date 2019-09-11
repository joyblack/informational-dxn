package com.joy.xxfy.informationaldxn.module.system.service;

import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.system.domain.constant.ResourceConstant;
import com.joy.xxfy.informationaldxn.module.system.domain.defaults.ResourceDefault;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.ResourceEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.ResourceRepository;
import com.joy.xxfy.informationaldxn.module.system.web.req.ResourceAddReq;
import com.joy.xxfy.informationaldxn.module.system.web.req.ResourceUpdateReq;
import com.joy.xxfy.informationaldxn.publish.constant.DepartmentConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.project.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        ResourceEntity check = resourceRepository.findFirstByParentIdAndResourceName(req.getParentId(), req.getResourceName());
        if(check != null){
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
        // == 限制用户的类型
        info.setUserType(req.getUserType());
        // save
        ResourceEntity save = resourceRepository.save(info);
        // update path
        save.setPath(getSuitPath(parent, save));
        return JoyResult.buildSuccessResultWithData(resourceRepository.save(save));
    }

    /**
     * 修改
     */
    public JoyResult update(ResourceUpdateReq req) {
        ResourceEntity info = resourceRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.RESOURCE_NOT_EXIST);
        }
        // check same name on common level.
        ResourceEntity check = resourceRepository.findFirstByParentIdAndResourceNameAndIdNot(info.getParentId(), req.getResourceName(),info.getId());
        if(check != null){
            return JoyResult.buildFailedResult(Notice.RESOURCE_NAME_ALREADY_EXIST);
        }
        // == 资源别称
        info.setResourceAliasName(req.getResourceAliasName());
        // == 资源名称
        info.setResourceName(req.getResourceName());
        // == 资源类型
        info.setResourceType(req.getResourceType());
        info.setResourceUrl(req.getResourceUrl());
        // save
        return JoyResult.buildSuccessResultWithData(resourceRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        ResourceEntity info = resourceRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.RESOURCE_NOT_EXIST);
        }
        info.setIsDelete(true);
        info.setUpdateTime(new Date());
        resourceRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
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
    public JoyResult getTree(Long parentId) {
        ResourceEntity resourceEntity = resourceRepository.findAllById(parentId);
        String path = resourceEntity == null? ResourceDefault.PATH : resourceEntity.getPath();
        List<ResourceEntity> children = resourceRepository.findAllByPathStartingWith(path);
        return JoyResult.buildSuccessResultWithData(TreeUtil.getResourcesTree(children, resourceEntity == null ? 0 : resourceEntity.getId()));
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
