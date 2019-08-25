package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkGetListReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillWorkUpdateReq;
import com.joy.xxfy.informationaldxn.module.drill.web.res.DrillWorkRes;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class DrillWorkService {

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillHoleRepository drillHoleRepository;

    /**
     * 添加
     */
    public JoyResult add(DrillWorkAddReq req) {
        // 验证名称是否重复
        DrillWorkEntity drillWorkInfo = drillWorkRepository.findAllByDrillWorkName(req.getDrillWorkName());
        if(drillWorkInfo != null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NAME_ALREADY_EXIST);
        }
        // copy properties
        DrillWorkEntity info = new DrillWorkEntity();
        JoyBeanUtil.copyPropertiesIgnoreTargetNotNullProperties(req, info);
        LogUtil.info("Last info is: {}", info);

        DrillWorkEntity save = drillWorkRepository.save(info);
        // set return data.
        DrillWorkRes result = new DrillWorkRes();
        JoyBeanUtil.copyProperties(save, result);
        return JoyResult.buildSuccessResultWithData(result);
    }

    /**
     * 改
     */
    public JoyResult update(DrillWorkUpdateReq req) {
        DrillWorkEntity info = drillWorkRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // 名称合法
        DrillWorkEntity checkRepeat = drillWorkRepository.findAllByDrillWorkNameAndIdNot(req.getDrillWorkName(), req.getId());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NAME_ALREADY_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        DrillWorkEntity save = drillWorkRepository.save(info);
        // 装配返回信息
        DrillWorkRes result = new DrillWorkRes();
        JoyBeanUtil.copyProperties(save, result);
        result.setDrillHole(drillHoleRepository.findAllByDrillWork(save));
        // save.
        return JoyResult.buildSuccessResultWithData(result);
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        DrillWorkEntity info = drillWorkRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // 先删除详细工作的信息
        drillHoleRepository.updateIsDeleteByDrillWork(info, true);
        // 再删除工作信息
        info.setIsDelete(true);
        drillWorkRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        DrillWorkRes res = new DrillWorkRes();
        DrillWorkEntity info = drillWorkRepository.findAllById(id);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 获取详细信息
            res.setDrillHole(drillHoleRepository.findAllByDrillWork(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name) {
        DrillWorkRes res = new DrillWorkRes();
        DrillWorkEntity info = drillWorkRepository.findAllByDrillWorkName(name);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 获取详细信息
            res.setDrillHole(drillHoleRepository.findAllByDrillWork(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrillWorkGetListReq req) {
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrillWorkGetListReq req) {
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrillWorkEntity> getPredicates(DrillWorkGetListReq req){
        return (Specification<DrillWorkEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // name like
            if(!StringUtil.isEmpty(req.getDrillWorkName())){
                predicates.add(builder.like(root.get("drillWorkName"), "%" + req.getDrillWorkName() +"%"));
            }
            // drill_time between
            if(req.getDrillTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("drillTime"), req.getDrillTimeStart()));
            }
            if(req.getDrillTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("drillTime"), req.getDrillTimeEnd()));
            }
            if(req.getDrillCategory() != null){
                predicates.add(builder.equal(root.get("drillCategory"), req.getDrillCategory()));
            }
            if(req.getDrillRockCharacter() != null){
                predicates.add(builder.equal(root.get("drillRockCharacter"), req.getDrillRockCharacter()));
            }
            if(req.getDrillType() != null){
                predicates.add(builder.equal(root.get("drillType"), req.getDrillType()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
