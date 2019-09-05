package com.joy.xxfy.informationaldxn.module.system.service;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.UserRepository;
import com.joy.xxfy.informationaldxn.module.system.web.req.UserAddReq;
import com.joy.xxfy.informationaldxn.module.system.web.req.UserUpdateReq;
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
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public JoyResult add(UserAddReq req) {
        /**
         * 检测密码是否一致
         */
        if(!StringUtil.equals(req.getPassword(),req.getAffirmPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_AFFIRM_ERROR);
        }
        /**
         * 校验登录名
         */
        UserEntity checkUser = userRepository.findAllByLoginName(req.getLoginName());;
        if(null != checkUser){
            return JoyResult.buildFailedResult(Notice.LOGIN_NAME_ALREADY_EXIST);
        }
        // 获取所属部门
        DepartmentEntity department = departmentRepository.findAllById(req.getDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 公司
        DepartmentEntity company = getCompanyByDepartment(department);
        if(company == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }

        UserEntity user = new UserEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, user);
        // 用户类型
        if(department.getDepartmentType().equals(DepartmentTypeEnum.CP_GROUP)){
            user.setUserType(UserTypeEnum.CP_ADMIN);
        }else{
            user.setUserType(UserTypeEnum.CM_ADMIN);
        }
        user.setDepartment(department);
        // 获取所属公司信息
        user.setCompany(getCompanyByDepartment(department));
        // 密码
        user.setPassword(MD5Util.encode(user.getPassword()));
        // 保存数据
        UserEntity save = userRepository.save(user);
        return JoyResult.buildSuccessResultWithData(save);
    }

    /**
     * get
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(userRepository.findById(id));
    }

    /**
     * 通过部门获取公司信息
     */
    public DepartmentEntity getCompanyByDepartment(DepartmentEntity departmentEntity){
        String[] split = departmentEntity.getPath().split(DepartmentConstant.DEPARTMENT_PATH_SEPARATOR);
        return departmentRepository.findAllById(Long.valueOf(split[0]));
    }

    /**
     * update
     */
    public JoyResult add(UserUpdateReq req) {
        UserEntity info = userRepository.findAllById(req.getId());
        /**
         * 检测密码是否一致
         */
        if(!StringUtil.equals(req.getPassword(),req.getAffirmPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_AFFIRM_ERROR);
        }
        /**
         * 校验登录名
         */
        UserEntity checkUser = userRepository.findAllByLoginName(req.getLoginName());;
        if(null != checkUser && !info.getId().equals(checkUser.getId())){
            return JoyResult.buildFailedResult(Notice.LOGIN_NAME_ALREADY_EXIST);
        }
        // 获取所属部门
        DepartmentEntity department = departmentRepository.findAllById(req.getDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 公司
        DepartmentEntity company = getCompanyByDepartment(department);
        if(company == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }

        // 用户类型
        if(department.getDepartmentType().equals(DepartmentTypeEnum.CP_GROUP)){
            info.setUserType(UserTypeEnum.CP_ADMIN);
        }else{
            info.setUserType(UserTypeEnum.CM_ADMIN);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setDepartment(department);
        // 获取所属公司信息
        info.setCompany(getCompanyByDepartment(department));
        // 密码
        info.setPassword(MD5Util.encode(info.getPassword()));
        // 保存数据
        UserEntity save = userRepository.save(info);
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
     * 获取分页数据
     */
    public JoyResult getPagerList(BasePageReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(userRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BasePageReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(userRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<UserEntity> getPredicates(BasePageReq req, UserEntity loginUser){
        return (Specification<UserEntity>) (root, query, builder) -> {
            List<Predicate> predicates1 = new ArrayList<>();
            // 非集团账户只能看见自己平台的用户信息
            if(!loginUser.getUserType().equals(UserTypeEnum.CP_ADMIN)){
                predicates1.add(builder.equal(root.get("company"),loginUser.getCompany()));
            }

            List<Predicate> predicates2 = new ArrayList<>();
            // loginName
            if(req.getSearch() != null){
                predicates2.add(builder.like(root.get("loginName"), "%" + req.getSearch() + "%"));
                predicates2.add(builder.like(root.get("username"), "%" + req.getSearch() + "%"));
            }
            List<Predicate> result = new ArrayList<>();
            Predicate or = builder.or(predicates1.toArray(new Predicate[predicates1.size()]));
            Predicate and = builder.and(predicates2.toArray(new Predicate[predicates2.size()]));
            result.add(or);
            result.add(and);
            return builder.or(result.toArray(new Predicate[result.size()]));
        };
    }


}
