package com.joy.xxfy.informationaldxn.module.system.service;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.RoleEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.RoleRepository;
import com.joy.xxfy.informationaldxn.module.system.web.req.RoleAddReq;
import com.joy.xxfy.informationaldxn.module.system.web.req.RoleGetListReq;
import com.joy.xxfy.informationaldxn.module.system.web.req.RoleUpdateReq;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
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
    private RoleRepository roleRepository;

    /**
     * add
     */
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

    /**
     * update
     */
    public JoyResult update(RoleUpdateReq req, UserEntity loginUser) {
        RoleEntity info = roleRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.ROLE_NOT_EXIST);
        }
        // 检测名称是否重复
        RoleEntity check = roleRepository.findFirstByBelongCompanyAndRoleName(loginUser.getCompany(), req.getRoleName());
        if(check != null && !check.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.ROLE_NAME_ALREADY_EXIST);
        }
        // 装配结果信息
        info.setRoleName(req.getRoleName());
        info.setPermissions(req.getPermissions());
        info.setUpdateTime(DateUtil.getDateJustYMD());
        return JoyResult.buildSuccessResultWithData(roleRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        RoleEntity info = roleRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.ROLE_NOT_EXIST);
        }
        try{
            roleRepository.deleteById(id);
        }catch (Exception e){
            return JoyResult.buildFailedResult(Notice.DATA_IN_USED_CANT_BE_DELETE);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }


    /**
     * 获取（通过ID）
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(roleRepository.findById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(RoleGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(roleRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(RoleGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(roleRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<RoleEntity> getPredicates(RoleGetListReq req,UserEntity loginUser){
        return (Specification<RoleEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getPermissions())){
                predicates.add(builder.like(root.get("permissions"), "%" + req.getPermissions() +"%"));
            }
            if(!StringUtil.isEmpty(req.getRoleName())){
                predicates.add(builder.like(root.get("roleName"), "%" + req.getRoleName() +"%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
