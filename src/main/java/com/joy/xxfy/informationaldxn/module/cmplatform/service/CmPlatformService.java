package com.joy.xxfy.informationaldxn.module.cmplatform.service;

import com.joy.xxfy.informationaldxn.module.cmplatform.domain.entity.CmPlatformEntity;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.repository.CmPlatformRepository;
import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.AddCmPlatformReq;
import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.GetCmPlatformListReq;
import com.joy.xxfy.informationaldxn.module.cmplatform.web.req.UpdateCmPlatformReq;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePermissionReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.ChangePasswordReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.SystemConfigEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.SystemConfigRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.UserRepository;
import com.joy.xxfy.informationaldxn.publish.constant.DepartmentConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.*;
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
public class CmPlatformService extends BaseService {
    @Autowired
    private CmPlatformRepository cmPlatformRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    public JoyResult add(AddCmPlatformReq addCmPlatformReq,UserEntity loginUser) {
        // 检测煤矿名称是否重复.
        CmPlatformEntity check = cmPlatformRepository.findFirstByCmName(addCmPlatformReq.getCmName());
        if(check != null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NAME_ALREADY_EXIST);
        }
        // 检测登录名称是否重复
        UserEntity checkUser = userRepository.findFirstByLoginName(addCmPlatformReq.getLoginName());
        if(checkUser != null){
            return JoyResult.buildFailedResult(Notice.LOGIN_NAME_ALREADY_EXIST);
        }

        //检查电话号码是否合法,这里没有对电话号码进行唯一性验证。
        // 若后期需要对电话号码进行限制，考虑不要讲此电话作为user的phone，或者强制非法操作。
        if(addCmPlatformReq.getPhone() != null ){
            if(!PhoneUtil.isMobile(addCmPlatformReq.getPhone())){
                return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
            }
        }
        // 创建部门
        DepartmentEntity department = createCmPlatformDepartment(addCmPlatformReq);
        DepartmentEntity departmentEntity = departmentRepository.save(department);
        // 路径信息更新
        department.setPath(departmentEntity.getId() + DepartmentConstant.DEPARTMENT_PATH_SEPARATOR);
        departmentRepository.save(department);
        LogUtil.info("插入后的部门信息为：{}", departmentEntity);

        // 创建用户
        UserEntity user = createCmPlatformUser(addCmPlatformReq, department);
        UserEntity userEntity = userRepository.save(user);
        LogUtil.info("插入后的用户信息为：{}", userEntity);

        // 创建平台信息
        CmPlatformEntity cmPlatform = new CmPlatformEntity();
        // 平台名称
        cmPlatform.setCmName(addCmPlatformReq.getCmName());
        // 备注信息
        cmPlatform.setRemarks(addCmPlatformReq.getRemarks());
        // 状态
        cmPlatform.setStatus(CommonStatusEnum.START);
        // 对应的账户ID
        cmPlatform.setUser(userEntity);
        CmPlatformEntity save = cmPlatformRepository.save(cmPlatform);
        // 返回扁平化数据供前端展示
        return JoyResult.buildSuccessResultWithData(save);
    }

    /**
     * update
     */
    public JoyResult update(UpdateCmPlatformReq req,UserEntity loginUser) {
        // 检查电话号码是否合法,这里没有对电话号码进行唯一性验证。
        // 若后期需要对电话号码进行限制，考虑不要讲此电话作为user的phone，或者强制非法操作。
        if(req.getPhone() != null ){
            if(!PhoneUtil.isMobile(req.getPhone())){
                return JoyResult.buildFailedResult(Notice.PHONE_ERROR);
            }
        }
        // get older
        CmPlatformEntity cmPlatform = cmPlatformRepository.findAllById(req.getId());
        if(cmPlatform == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }

        CmPlatformEntity checkData = cmPlatformRepository.findFirstByCmNameAndIdNot(req.getCmName(), req.getId());
        if(checkData != null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NAME_ALREADY_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, cmPlatform);
        // 用户、部门修改，关注负责人以及部门名称
        UserEntity user = cmPlatform.getUser();
        user.setId(cmPlatform.getUser().getId());
        user.setUpdateTime(new Date());

        DepartmentEntity department = cmPlatform.getUser().getDepartment();
        department.setDepartmentName(cmPlatform.getCmName());
        // 管理员姓名
        if(req.getUsername() != null){
            department.setResponseUser(req.getUsername());
            user.setUsername(req.getUsername());
        }
        // 联系电话
        if(req.getPhone() != null){
            department.setPhone(req.getPhone());
            user.setPhone(req.getPhone());
        }
        // 登陆名称
        if(req.getLoginName() != null){
            user.setLoginName(req.getLoginName());
        }
        // 返回更新后的扁平化数据
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.save(cmPlatform));
    }

    public JoyResult delete(Long id,UserEntity loginUser) {
        // get older
        CmPlatformEntity cmPlatform = cmPlatformRepository.findAllById(id);
        if(cmPlatform == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 删除
        cmPlatform.setIsDelete(true);
        cmPlatform.getUser().setIsDelete(true);
        cmPlatform.getUser().getDepartment().setIsDelete(true);
        // 测试只删除cm
        cmPlatformRepository.save(cmPlatform);

        return JoyResult.buildSuccessResult("删除成功");
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id,UserEntity loginUser) {
        // get older
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getAllList(GetCmPlatformListReq req,UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取全部数据
     */
    public JoyResult getPagerList(GetCmPlatformListReq req,UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.findAll(getPredicates(req),JpaPagerUtil.getPageable(req)));
    }

    private Specification<CmPlatformEntity> getPredicates(GetCmPlatformListReq req){
        return (Specification<CmPlatformEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getLoginName())){
                predicates.add(builder.like(root.get("user").get("loginName"),"%" + req.getLoginName() + "%"));
            }
            if(!StringUtil.isEmpty(req.getCmName())){
                predicates.add(builder.like(root.get("cmName"),"%" + req.getCmName() + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 禁用
     */
    public JoyResult disable(Long id, UserEntity loginUser) {
        // get older
        CmPlatformEntity cmPlatformEntity = cmPlatformRepository.findAllById(id);
        if(cmPlatformEntity == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        cmPlatformEntity.setStatus(CommonStatusEnum.STOP);
        cmPlatformEntity.getUser().setStatus(CommonStatusEnum.STOP);
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.save(cmPlatformEntity));
    }

    /**
     * 启用
     */
    public JoyResult enable(Long id, UserEntity loginUser) {
        // get older
        CmPlatformEntity cmPlatformEntity = cmPlatformRepository.findAllById(id);
        if(cmPlatformEntity == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        cmPlatformEntity.setStatus(CommonStatusEnum.START);
        cmPlatformEntity.getUser().setStatus(CommonStatusEnum.START);
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.save(cmPlatformEntity));
    }

    /**
     * 重置密码
     */
    public JoyResult resetPassword(Long id, UserEntity loginUser) {
        CmPlatformEntity cmPlatform = cmPlatformRepository.findAllById(id);
        if(cmPlatform == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        // 提取配置密码
        String password = getSuitPassword(null);
        LogUtil.info("The config password is: {}", password);
        cmPlatform.getUser().setPassword(MD5Util.encode(password));
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.save(cmPlatform));
    }

    /**
     * 修改密码
     */
    public JoyResult changePassword(ChangePasswordReq req,UserEntity loginUser) {
        // 检测合法性
        if(!StringUtil.equals(req.getNewPassword(), req.getAffirmPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_AFFIRM_ERROR);
        }
        // Get older
        CmPlatformEntity cmPlatform = cmPlatformRepository.findAllById(req.getId());
        if(cmPlatform == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }

        // 验证密码是否正确
        UserEntity user = cmPlatform.getUser();
        String oldPassword = MD5Util.encode(req.getPassword());
        if(! StringUtil.equals(oldPassword, user.getPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_ERROR);
        }
        // 修改用户密码
        LogUtil.info("将用户密码修改为: {}", req.getNewPassword());
        user.setPassword(MD5Util.encode(req.getNewPassword()));
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.save(cmPlatform));
    }


    /**
     * 设置权限
     */
    public JoyResult updatePermission(BasePermissionReq req,UserEntity loginUser) {
        // get older
        CmPlatformEntity cmPlatform = cmPlatformRepository.findAllById(req.getId());
        if(cmPlatform == null){
            return JoyResult.buildFailedResult(Notice.CM_PLATFORM_NOT_EXIST);
        }
        String permissions = "";
        if(req.getPermission() != null && req.getPermission().size() > 0){
            permissions = PermissionUtil.transToString(req.getPermission());
        }
        LogUtil.info("The generated permissions string is: {}", permissions);
        // update to db.
        cmPlatform.getUser().setPermissions(permissions);
        return JoyResult.buildSuccessResultWithData(cmPlatformRepository.save(cmPlatform));
    }



    /*********************************************/
    /**
     * 创建煤矿平台对应的部门信息
     */
    private DepartmentEntity createCmPlatformDepartment(AddCmPlatformReq req){
        DepartmentEntity department = new DepartmentEntity();
        department.setPath("");
        department.setCode("");
        // 名称设置为平台名称
        department.setDepartmentName(req.getCmName());
        // 父ID设置为0
        department.setParentId(DepartmentConstant.COMPANY_PARENT_NODE_ID);
        // 联系电话和生成的用户保持一致
        department.setPhone(req.getPhone());
        // 负责人为管理员的姓名
        department.setResponseUser(req.getUsername());
        // 部门类型为煤矿平台类型
        department.setDepartmentType(DepartmentTypeEnum.CM_PLATFORM);
        // 设置path
        return department;
    }

    /**
     * 创建煤矿平台对应的用户（管理员）信息
     */
    private UserEntity createCmPlatformUser(AddCmPlatformReq req, DepartmentEntity department){
        UserEntity user =  new UserEntity();
        user.setPassword(MD5Util.encode(getSuitPassword(req.getPassword())));
        // 身份证不设置
        user.setIdNumber("");
        // 登录名为请求数据设置
        user.setLoginName(req.getLoginName());
        // 名字设置为adminName指定的名称
        user.setUsername(req.getUsername());
        // phone
        user.setPhone(req.getPhone());
        // 类型：煤矿平台管理员
        user.setUserType(UserTypeEnum.CM_ADMIN);
        // 部门
        user.setDepartment(department);
        // 公司：同部门
        user.setCompany(department);
        return user;
    }


    /**
     * 获取应该设置的密码
     * recommendPassword为推荐的密码
     */
    private String getSuitPassword(String recommendPassword){
        if(StringUtil.isNotEmpty(recommendPassword)){
            return recommendPassword;
        }else{
            // 获取密码
            return systemConfigRepository.findFirstByConfigName(SystemConfigEnum.USER_DEFAULT_PASSWORD.name()).getConfigValue();
        }
    }




}
