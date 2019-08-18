package com.joy.xxfy.informationaldxn.publish.utils.project;

import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
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
//
//    /**
//     * 将资源列表转化为树结构
//     */
//    public  static List<Resources> getResourcesTree(List<Resources> list, Long pid){
//        List<Resources> result =  new ArrayList<>();
//        List<Resources> temp;
//        for(Resources entity : list){
//            if(entity.getParentId().equals(pid)){
//                Resources scopeMode = new Resources();
//                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
//                temp = getResourcesTree(list,entity.getId());
//                if(temp.size() > 0){
//                    scopeMode.setChildren(temp);
//                }
//                result.add(scopeMode);
//            }
//        }
//        return result;
//    }
}
