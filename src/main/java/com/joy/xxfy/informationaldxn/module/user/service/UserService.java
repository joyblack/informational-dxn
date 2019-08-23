package com.joy.xxfy.informationaldxn.module.user.service;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.common.web.req.TestReq;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.MD5Util;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.user.domain.repository.UserRepository;
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

    public JoyResult add(UserEntity user) {
        /**
         * 检测密码是否一致
         */
        if(!StringUtil.equals(user.getPassword(),user.getAffirmPassword())){
            return JoyResult.buildFailedResult(Notice.PASSWORD_AFFIRM_ERROR);
        }
        /**
         * 校验登录名
         */
        UserEntity checkUser = userRepository.findAllByLoginName(user.getLoginName());;
        if(null != checkUser){
            return JoyResult.buildFailedResult(Notice.LOGIN_NAME_ALREADY_EXIST);
        }
        // 密码
        user.setPassword(MD5Util.encode(user.getPassword()));
        // 保存数据
        UserEntity save = userRepository.save(user);
        save.setPassword(null);
        return JoyResult.buildSuccessResultWithData(save);
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
        dbUser.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(userRepository.save(dbUser));
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
