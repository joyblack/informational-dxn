package com.joy.xxfy.informationaldxn.department.service;

import com.joy.xxfy.informationaldxn.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.department.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.publish.constant.SystemConstant;
import com.joy.xxfy.informationaldxn.publish.constant.TestConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.SqlUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.TreeUtil;
import com.joy.xxfy.informationaldxn.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.user.domain.enums.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 测试
     */
    public JoyResult test(Integer times) {
        int i = 0;
        while(++ i <= times){
            DepartmentEntity d = new DepartmentEntity();
            d.setDepartmentName("测试部门" + i);
            d.setDepartmentType(DepartmentTypeEnum.CM_PLATFORM);
            d.setPhone(TestConstant.PHONE);
            d.setResponseUser("赵义");
            d.setParentId(0L);
            d.setCode("01");
            System.out.println(add(d));
        }
        return JoyResult.buildSuccessResult("插入测试数据成功");
    }

    /**
     * 新增
     */
    public JoyResult add(DepartmentEntity department) {
        // check parent dept info.
        DepartmentEntity parent = departmentRepository.findAllById(department.getParentId());
        if(!department.getParentId().equals(SystemConstant.TOP_NODE_ID)){
            if(parent == null){
                return JoyResult.buildFailedResult(Notice.DEPARTMENT_PARENT_NOT_EXIST);
            }
        }
        // check same name on common level.
        DepartmentEntity checkDepartment = departmentRepository.findAllByParentIdAndDepartmentName(department.getParentId(), department.getDepartmentName());
        if(checkDepartment != null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NAME_ALREADY_EXIST);
        }
        DepartmentEntity save = departmentRepository.save(department);
        // Set path
        save.setPath(getSuitPath(parent, department));
        LogUtil.info("Joy: Repeat update the department [path]:");
        return JoyResult.buildSuccessResultWithData(departmentRepository.save(department));
    }

    /**
     * 更新
     */
    public JoyResult update(DepartmentEntity department) {
        // 获取部门信息
        DepartmentEntity oldDept = departmentRepository.findAllById(department.getId());
        if(oldDept == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // check parent dept info.
        DepartmentEntity parent = departmentRepository.findAllById(department.getParentId());
        if(!department.getParentId().equals(SystemConstant.TOP_NODE_ID)){
            if(parent == null){
                return JoyResult.buildFailedResult(Notice.DEPARTMENT_PARENT_NOT_EXIST);
            }
        }
        // 检查是否有重名部门
        DepartmentEntity checkDepartment = departmentRepository.findAllByParentIdAndDepartmentNameAndIdNot(department.getParentId(), department.getDepartmentName(),department.getId());
        if(checkDepartment != null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NAME_ALREADY_EXIST);
        }
        // 将数据库的数据对应拷贝到目标对象的空值属性上，其余的保持目标属性的不变。
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(department, oldDept);
        DepartmentEntity save = departmentRepository.save(oldDept);
        // 更新所有相关节点的路径
        String oldPath = save.getPath();
        String newPath = getSuitPath(parent, department);
        save.setPath(newPath);
        departmentRepository.updateAllDepartmentPath(oldPath,newPath);
        // 更新所有的之前的路径开头的数据
        return JoyResult.buildSuccessResultWithData(save);
    }

    /**
     * 获取部门信息
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(departmentRepository.findAllById(id));
    }

    /**
     * 删除部门信息
     */
    public JoyResult delete(Long id) {
        DepartmentEntity oldDept = departmentRepository.findAllById(id);
        if(oldDept == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        oldDept.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(departmentRepository.save(oldDept));
    }

    /**
     * 获取部门子节点，一层
     */
    public JoyResult getChildren(Long parentId) {
        return JoyResult.buildSuccessResultWithData(departmentRepository.findAllByParentId(parentId));
    }

    // 获取部门树
    public JoyResult getTree(Long parentId) {
        DepartmentEntity department = departmentRepository.findAllById(parentId);
        String path = department == null? SystemConstant.EMPTY_VALUE : department.getPath();
        List<DepartmentEntity> children = departmentRepository.findAllByPathLike(SqlUtil.getLikeString(path));
        return JoyResult.buildSuccessResultWithData(TreeUtil.getDeptTree(children, department == null ? 0 : department.getId()));
    }

    /*
     * 拼接Path 1- 1-2-(if 2 is 1 child.)
     */
    private String getSuitPath(DepartmentEntity parent, DepartmentEntity child){
        if(parent == null){
            return child.getId() + SystemConstant.DEPARTMENT_PATH_SEPARATOR;
        }else{
            return parent.getPath()  + child.getId() + SystemConstant.DEPARTMENT_PATH_SEPARATOR;
        }
    }

    // 获取当前用户权限范围内所能展示的公司/平台列表
    public JoyResult getCompanyList(UserEntity user) {
        // SESSION : 打开注释
        // 集团用户可以管理所有煤矿平台，煤矿平台只能管理自己所属的平台
//        List<DepartmentEntity> companyList = new ArrayList<>();
//        if(user.getUserType().equals(UserTypeEnum.CM_ADMIN) || user.getUserType().equals(UserTypeEnum.CM_COMMON)){
//            companyList.add(user.getDepartment());
//        }else{// 集团用户获取所有
//            companyList = departmentRepository.findAllByParentId(0L);
//        }
        return JoyResult.buildSuccessResultWithData(departmentRepository.findAllByParentId(0L));
    }
}
