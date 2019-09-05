package com.joy.xxfy.informationaldxn.publish.utils.project;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.device.domain.entity.DeviceCategoryEntity;
import com.joy.xxfy.informationaldxn.module.document.domain.entity.FileEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.ResourceEntity;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
    /**
     * 将文件列表转化为树结构
     */
    public  static List<FileEntity> getFileTree(List<FileEntity> list, Long pid){
        List<FileEntity> result =  new ArrayList<>();
        List<FileEntity> temp;
        for(FileEntity entity : list){
            if(entity.getParentId().equals(pid)){
                FileEntity scopeMode = new FileEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getFileTree(list,entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }

    /**
     * 将部门列表转化为部门树结构
     */
    public  static List<DepartmentEntity> getDeptTree(List<DepartmentEntity> list, Long pid){
        List<DepartmentEntity> result =  new ArrayList<>();
        List<DepartmentEntity> temp;
        for(DepartmentEntity entity : list){
            if(entity.getParentId().equals(pid)){
                DepartmentEntity scopeMode = new DepartmentEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getDeptTree(list,entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }

    /**
     * 将设备类型列表转化为部门树结构
     */
    public  static List<DeviceCategoryEntity> getDeviceCategoryTree(List<DeviceCategoryEntity> list, Long pid){
        List<DeviceCategoryEntity> result =  new ArrayList<>();
        List<DeviceCategoryEntity> temp;
        for(DeviceCategoryEntity entity : list){
            if(entity.getParentId().equals(pid)){
                DeviceCategoryEntity scopeMode = new DeviceCategoryEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getDeviceCategoryTree(list,entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }

    /**
     * 将资源列表转化为树结构
     */
    public  static List<ResourceEntity> getResourcesTree(List<ResourceEntity> list, Long pid){
        List<ResourceEntity> result =  new ArrayList<>();
        List<ResourceEntity> temp;
        for(ResourceEntity entity : list){
            if(entity.getParentId().equals(pid)){
                ResourceEntity scopeMode = new ResourceEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getResourcesTree(list,entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }
}
