package com.joy.xxfy.informationaldxn.start;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonStatusEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.SystemConfigEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.SystemConfigEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.SystemConfigRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.UserRepository;
import com.joy.xxfy.informationaldxn.publish.constant.DepartmentConstant;
import com.joy.xxfy.informationaldxn.publish.constant.SystemConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 系统启动初始化：初始化数据库
 */
@Component
public class ServiceStartInitial implements ApplicationRunner {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    /* 用户默认密码 */
    @Value("${system.config.user-default-password}")
    private String defaultPassword;

    /* 集团公司名称 */
    @Value("${system.initial.company-name}")
    private String companyName;

    /* 超管账户 */
    @Value("${system.initial.super-admin-default-name}")
    private String superAdminName;

    /* 超管默认密码 */
    @Value("${system.initial.super-admin-default-password}")
    private String superAdminPassword;

    public JoyResult start(){
        // == 配置默认
        // 1.添加默认密码配置项,判断是否存在
        SystemConfigEntity config1 = systemConfigRepository.findAllByConfigName(SystemConfigEnum.USER_DEFAULT_PASSWORD.name());
        if(config1 == null){
            LogUtil.info("Now I will create a config: DEFAULT_PASSWORD");
            SystemConfigEntity info = new SystemConfigEntity();
            info.setConfigName(SystemConfigEnum.USER_DEFAULT_PASSWORD.name());
            info.setConfigValue(defaultPassword);
            systemConfigRepository.save(info);
        }
        // 2. 添加集团部门
        DepartmentEntity superCompany = departmentRepository.findAllByParentIdAndDepartmentName(DepartmentConstant.COMPANY_PARENT_NODE_ID, companyName);
        if(superCompany == null){
            LogUtil.info("Now I will create super company: {}", companyName);
            superCompany = new DepartmentEntity();
            superCompany.setDepartmentName(companyName);
            superCompany.setDepartmentType(DepartmentTypeEnum.CP_GROUP);
            superCompany.setCode("SUPER-COMPANY");
            superCompany.setParentId(DepartmentConstant.COMPANY_PARENT_NODE_ID);
            superCompany.setPath("");
            DepartmentEntity save = departmentRepository.save(superCompany);
            save.setPath(save.getId() + DepartmentConstant.DEPARTMENT_PATH_SEPARATOR);
            departmentRepository.save(save);

            // 3.添加超管，并将其配置为集团公司成员
            LogUtil.info("Now I will create super admin: {}", superAdminName);
            UserEntity superAdmin = new UserEntity();
            superAdmin.setCompany(superCompany);
            superAdmin.setLoginName(superAdminName);
            superAdmin.setPassword(MD5Util.encode(superAdminPassword));
            superAdmin.setDepartment(superCompany);
            superAdmin.setPhone("13535565497");
            superAdmin.setUsername(SystemConstant.SUPER_ADMIN_USER_NAME);
            superAdmin.setRemarks("This is a created by sysytem super admin user, it belong to super company, don't delete it!");
            // 超管的访问权限：superAdmin.setPermissions();
            superAdmin.setUserType(UserTypeEnum.CP_ADMIN);
            superAdmin.setStatus(CommonStatusEnum.START);
            userRepository.save(superAdmin);
        }
        return JoyResult.buildSuccessResult("Environment init completed......");
    }


    /**
     * 初始化操作
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        LogUtil.info("Environment init start...");
        start();
    }
}
