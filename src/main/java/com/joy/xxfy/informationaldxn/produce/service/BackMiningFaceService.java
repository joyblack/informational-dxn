package com.joy.xxfy.informationaldxn.produce.service;

import com.joy.xxfy.informationaldxn.produce.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.produce.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.produce.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.produce.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.produce.web.req.*;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackMiningFaceService {

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    /**
     * 添加
     */
    public JoyResult add(BackMiningFaceAddReq req) {
        // 验证名称是否重复
        BackMiningFaceEntity backMiningFaceName = backMiningFaceRepository.findAllByBackMiningFaceName(req.getBackMiningFaceName());
        if(backMiningFaceName != null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NAME_ALREADY_EXIST);
        }
        // copy properties
        BackMiningFaceEntity info = new BackMiningFaceEntity();
        JoyBeanUtil.copyPropertiesIgnoreTargetNotNullProperties(req, info);
        // 已采长度：设计长度-已掘长度
        LogUtil.info("Last back mining face info is: {}", info);
        // save.
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.save(info));
    }

    /**
     * 改
     */
    public JoyResult update(BackMiningFaceUpdateReq req) {
        // 只允许修改解禁时间以及原因
        BackMiningFaceEntity info = backMiningFaceRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NOT_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        // save.
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        BackMiningFaceEntity info = backMiningFaceRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NOT_EXIST);
        }
        // 若有日报信息不允许删除[CHANGE]
        // ....
        info.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.save(info));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BackMiningFaceGetListReq req) {
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BackMiningFaceGetListReq req) {
        return JoyResult.buildSuccessResultWithData(backMiningFaceRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<BackMiningFaceEntity> getPredicates(BackMiningFaceGetListReq req){
        return (Specification<BackMiningFaceEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // driving_face_name like
            if(!StringUtil.isEmpty(req.getBackMiningFaceName())){
                predicates.add(builder.like(root.get("backMiningFaceName"), "%" + req.getBackMiningFaceName() +"%"));
            }
            // start_time between
            if(req.getStartTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("startTime"), req.getStartTimeStart()));
            }
            if(req.getStartTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("startTime"), req.getStartTimeEnd()));
            }
            if(req.getVentilationMode() != null){
                predicates.add(builder.equal(root.get("ventilationMode"), req.getVentilationMode()));
            }
            if(req.getBackMiningMode() != null){
                predicates.add(builder.equal(root.get("backMiningMode"), req.getBackMiningMode()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
