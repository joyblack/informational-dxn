package com.joy.xxfy.informationaldxn.produce.service;

import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkDetailEntity;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.produce.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.produce.web.req.BackMiningFaceGetListReq;
import com.joy.xxfy.informationaldxn.produce.web.req.DrillWorkAddReq;
import com.joy.xxfy.informationaldxn.produce.web.req.DrillWorkGetListReq;
import com.joy.xxfy.informationaldxn.produce.web.req.DrillWorkUpdateReq;
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
        for (DrillWorkDetailEntity drillWorkDetailEntity : req.getDrillWorkDetail()) {
            drillWorkDetailEntity.setDrillWork(info);
        }
        // 已采长度：设计长度-已掘长度
        LogUtil.info("Last info is: {}", info);
        // save.
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.save(info));
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
        // 删除旧数据
        info.getDrillWorkDetail().forEach(d -> d.setIsDelete(true));
        DrillWorkEntity save = drillWorkRepository.save(info);
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, save);
        save.getDrillWorkDetail().forEach(d -> d.setDrillWork(save));

        // save.
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.save(save));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        DrillWorkEntity info = drillWorkRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        info.setIsDelete(true);
        // 详细信息
        info.getDrillWorkDetail().forEach(d -> d.setIsDelete(true));
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.save(info));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.findAllById(id));
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name) {
        return JoyResult.buildSuccessResultWithData(drillWorkRepository.findAllByDrillWorkName(name));
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
