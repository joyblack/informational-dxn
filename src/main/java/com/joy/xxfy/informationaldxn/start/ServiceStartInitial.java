package com.joy.xxfy.informationaldxn.start;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.enums.SwitchEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.constant.ResourceConstant;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.ResourceEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.SystemConfigEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.SystemConfigEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.ResourceRepository;
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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * 系统启动初始化：初始化数据库
 */
@Component
@Transactional
public class ServiceStartInitial implements ApplicationRunner {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceRepository resourceRepository;

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
        SystemConfigEntity c1 = systemConfigRepository.findFirstByConfigName(SystemConfigEnum.USER_DEFAULT_PASSWORD.name());
        LogUtil.info("============ 检测是否存在用户默认密码配置项 ============");
        if(c1 == null){
            LogUtil.info("不存在，开始创建...");
            SystemConfigEntity info = new SystemConfigEntity();
            info.setConfigName(SystemConfigEnum.USER_DEFAULT_PASSWORD.name());
            info.setConfigValue(defaultPassword);
            systemConfigRepository.save(info);
        }
        // 2.添加资源树加载开关配置项
        LogUtil.info("============ 检测是否存在资源加载开关配置项 ============ ");
        SystemConfigEntity c2 = systemConfigRepository.findFirstByConfigName(SystemConfigEnum.RESOURCE_TREE_SWITCH.name());
        if(c2 == null){
            LogUtil.info("不存在，创建资源加载开关配置项...");
            SystemConfigEntity info = new SystemConfigEntity();
            info.setConfigName(SystemConfigEnum.RESOURCE_TREE_SWITCH.name());
            // 初次加载的时候设置为开启
            info.setConfigValue(SwitchEnum.ON.name());
            systemConfigRepository.save(info);
        }
        // 3. 添加集团部门
        LogUtil.info("============ 检测是否存在集团部门，超级管理员 ============ ");
        DepartmentEntity superCompany = departmentRepository.findFirstByParentIdAndDepartmentName(DepartmentConstant.COMPANY_PARENT_NODE_ID, companyName);
        if(superCompany == null){
            LogUtil.info("不存在，创建集团部门:{}", companyName);
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
            LogUtil.info("不存在，创建超级管理员: {}", superAdminName);
            UserEntity superAdmin = new UserEntity();
            superAdmin.setCompany(superCompany);
            superAdmin.setLoginName(superAdminName);
            superAdmin.setPassword(MD5Util.encode(superAdminPassword));
            superAdmin.setDepartment(superCompany);
            superAdmin.setPhone("13535565497");
            superAdmin.setUsername(SystemConstant.SUPER_ADMIN_USER_NAME);
            superAdmin.setRemarks("This is a created by system super admin user, it belong to super company, don't delete it!");
            // 超管的访问权限：superAdmin.setPermissions();
            superAdmin.setUserType(UserTypeEnum.CP_ADMIN);
            superAdmin.setStatus(CommonStatusEnum.START);
            userRepository.save(superAdmin);
        }
        return JoyResult.buildSuccessResult("系统初始化完成...");
    }

    /**
     * 加载资源树
     */
    @Transactional
    public void loadResourceTree() throws Exception{
        /**
         * 检测是否需要重新加载资源树
         */
        LogUtil.info("============ 检测是否重载资源树 ============");
        SystemConfigEntity config = systemConfigRepository.findFirstByConfigName(SystemConfigEnum.RESOURCE_TREE_SWITCH.name());
        if(config.getConfigValue().equals(SwitchEnum.OFF.name())){
            LogUtil.info("资源树开关为关闭状态，跳过资源树更新...");
            return ;
        }
        LogUtil.info("资源树开关为开启状态，开始重载资源树...");
        InputStream inputStream = new ClassPathResource("resource.json").getStream();
        List<ResourceEntity> resources =  new ObjectMapper().readValue(inputStream,new TypeReference<List<ResourceEntity>>(){});

        System.out.println(resources.getClass());

        System.out.println(resources);
        for (ResourceEntity resource : resources) {
            addResource(resource ,null);
        }



        // 修改资源树开关为关闭
        config.setConfigValue(SwitchEnum.OFF.name());
        systemConfigRepository.save(config);

        LogUtil.info("资源树重载完成...");
    }


    private void addResource(ResourceEntity resource, ResourceEntity parentResource){
        if(parentResource == null){
            resource.setParentId(0L);
        }else{
            resource.setParentId(parentResource.getId());
        }
        resource.setId(null);
        resource.setPath("");
        ResourceEntity save = resourceRepository.save(resource);
        save.setPath(getSuitPath(parentResource, save));
        resourceRepository.save(resource);
        if(resource.getChildren() != null && resource.getChildren().size() > 0){
            for (ResourceEntity child : resource.getChildren()) {
                addResource(child, save);
            }
        }

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


    /**
     * 初始化操作
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        LogUtil.info("Environment init start...");
        start();
        loadResourceTree();
    }
}
