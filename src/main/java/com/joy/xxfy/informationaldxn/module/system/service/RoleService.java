package com.joy.xxfy.informationaldxn.module.system.service;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.common.web.req.TestReq;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.RoleEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.RoleRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.UserRepository;
import com.joy.xxfy.informationaldxn.module.system.web.req.RoleAddReq;
import com.joy.xxfy.informationaldxn.publish.constant.DepartmentConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.MD5Util;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public JoyResult add(RoleAddReq req, UserEntity loginUser) {
        DepartmentEntity company = loginUser.getCompany();
        /**
         * 是否重名
         */
        RoleEntity check = roleRepository.findFirstByBelongCompanyAndRoleName(company, req.getRoleName());
        if(check != null){
            return JoyResult.buildFailedResult(Notice.ROLE_NAME_ALREADY_EXIST);
        }

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setBelongCompany(company);
        roleEntity.setPermissions(req.getPermissions());
        roleEntity.setRoleName(req.getRoleName());
        return JoyResult.buildSuccessResultWithData(roleRepository.save(roleEntity));
    }

    // 通过部门获取公司信息
    public DepartmentEntity getCompanyByDepartment(DepartmentEntity departmentEntity){
        String[] split = departmentEntity.getPath().split(DepartmentConstant.DEPARTMENT_PATH_SEPARATOR);
        return departmentRepository.findAllById(Long.valueOf(split[0]));
    }

    public JoyResult update(UserEntity user) {
        // 密码
        if(user.getPassword() != null){
            user.setPassword(MD5Util.encode(user.getPassword()));
        }
        UserEntity dbUser = userRepository.findAllById(user.getId());
        if(dbUser == null){
            return JoyResult.buildFailedResult(Notice.USER_NOT_EXIST);
        }
        // 拷贝属性到oldUser之上
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(user, dbUser);
        UserEntity save = userRepository.save(dbUser);
        // 置空密码
        save.setPassword(null);
        return JoyResult.buildSuccessResultWithData(save);
    }

    public JoyResult delete(Long id) {
        UserEntity dbUser = userRepository.findAllById(id);
        if(dbUser == null){
            return JoyResult.buildFailedResult(Notice.USER_NOT_EXIST);
        }
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return JoyResult.buildFailedResult(Notice.DATA_IN_USED_CANT_BE_DELETE);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }


    /**
     * 一次性获取
     */
    public JoyResult getAllList(BasePageReq req) {
        // select * from all_user where login_name and user_name like...
        return JoyResult.buildSuccessResultWithData(userRepository.findAll((Specification<UserEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getSearch())){
                predicates.add(builder.or(
                   builder.like(root.get("loginName"), "%" + req.getSearch() + "%"),
                   builder.like(root.get("username"),"%" + req.getSearch() + "%")
                ));
            }
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            return query.getRestriction();
        }));
    }

    /**
     * 分页获取
     */
    public JoyResult getPagerList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(userRepository.findAll((Specification<UserEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getSearch())){
                predicates.add(builder.or(
                        builder.like(root.get("loginName"), "%" + req.getSearch() + "%"),
                        builder.like(root.get("username"),"%" + req.getSearch() + "%")
                ));
            }
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            return query.getRestriction();
        },JpaPagerUtil.getPageable(req)));
    }


    public JoyResult test(TestReq req){
        int recordNum = req.getTimes();
        while(recordNum -- > 0){
            UserEntity user = new UserEntity();
            user.setPassword("123456");
            user.setAffirmPassword("123456");
            user.setIdNumber("522401199401025931");
            user.setLoginName("zhaoyi" + recordNum);
            user.setPhone("13535565497");
            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setId(1L);
            user.setDepartment(departmentEntity);
            System.out.println(user);
            System.out.println(add(user));
        }
        return JoyResult.buildSuccessResult("完成测试...");
    }

    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(userRepository.findById(id));
    }
}
