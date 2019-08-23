package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillHoleAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillHoleUpdateReq;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
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
public class DrillHoleService {

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillHoleRepository drillHoleRepository;

    /**
     * 添加
     */
    public JoyResult add(DrillHoleAddReq req) {
        // 获取工作信息
        DrillWorkEntity drillWork = drillWorkRepository.findAllById(req.getDrillWorkId());
        if(drillWork == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }

        // 添加信息
        DrillHoleEntity drillHoleEntity = new DrillHoleEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, drillHoleEntity);
        // 设置工作信息
        drillHoleEntity.setDrillWork(drillWork);
        // save
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.save(drillHoleEntity));
    }

    /**
     * 改
     */
    public JoyResult update(DrillHoleUpdateReq req) {
        DrillHoleEntity info = drillHoleRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_DETAIL_NOT_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        // save.
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        DrillHoleEntity info = drillHoleRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_DETAIL_NOT_EXIST);
        }
        info.setIsDelete(true);
        drillHoleRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAllById(id));
    }

    /**
     * 获取数据(通过钻孔工作ID)
     */
    public JoyResult getAllByDrillWorkId(Long id) {

        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAllByDrillWorkId(id));
    }




    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAll(getPredicates(req)));
    }

    /**
     * 暂时无条件获取
     */
    private Specification<DrillHoleEntity> getPredicates(BasePageReq req){
        return (Specification<DrillHoleEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
