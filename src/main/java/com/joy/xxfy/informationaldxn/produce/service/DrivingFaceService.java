package com.joy.xxfy.informationaldxn.produce.service;

import com.joy.xxfy.informationaldxn.produce.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.produce.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.produce.web.req.DrivingFaceAddReq;
import com.joy.xxfy.informationaldxn.produce.web.req.DrivingFaceGetListReq;
import com.joy.xxfy.informationaldxn.produce.web.req.DrivingFaceUpdateReq;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateOperationUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffBlacklistEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffBlacklistAddReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffBlacklistGetListReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffBlacklistUpdateReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DrivingFaceService {

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    /**
     * 添加
     */
    public JoyResult add(DrivingFaceAddReq req) {
        // 验证名称是否重复
        DrivingFaceEntity checkInfo = drivingFaceRepository.findAllByDrivingFaceName(req.getDrivingFaceName());
        if(checkInfo != null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NAME_ALREADY_EXIST);
        }
        // copy properties
        DrivingFaceEntity drivingFaceInfo = new DrivingFaceEntity();
        JoyBeanUtil.copyPropertiesIgnoreTargetNotNullProperties(req, drivingFaceInfo);
        // 长度计算：设计长度-已掘长度
        drivingFaceInfo.setLeftLength(req.getTotalLength().subtract(req.getDoneLength()));
        LogUtil.info("Last drive face info is: {}", drivingFaceInfo);
        // save.
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.save(drivingFaceInfo));
    }

    /**
     * 改
     */
    public JoyResult update(DrivingFaceUpdateReq req) {
        DrivingFaceEntity drivingFaceInfo = drivingFaceRepository.findAllById(req.getId());
        if(drivingFaceInfo == null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NOT_EXIST);
        }
        // 名称合法
        DrivingFaceEntity checkRepeat = drivingFaceRepository.findAllByDrivingFaceNameAndIdNot(req.getDrivingFaceName(), req.getId());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NAME_ALREADY_EXIST);
        }
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, drivingFaceInfo);
        // save.
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.save(drivingFaceInfo));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        DrivingFaceEntity drivingFaceInfo = drivingFaceRepository.findAllById(id);
        if(drivingFaceInfo == null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NOT_EXIST);
        }
        // 若有日报信息不允许删除[CHANGE]
        // ....

        drivingFaceInfo.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.save(drivingFaceInfo));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAllById(id));
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name) {
        // get older
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAllByDrivingFaceName(name));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrivingFaceGetListReq req) {
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrivingFaceGetListReq req) {
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrivingFaceEntity> getPredicates(DrivingFaceGetListReq req){
        return (Specification<DrivingFaceEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // driving_face_name like
            if(!StringUtil.isEmpty(req.getDrivingFaceName())){
                predicates.add(builder.like(root.get("drivingFaceName"), "%" + req.getDrivingFaceName() +"%"));
            }
            // start_time between
            if(req.getStartTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("startTime"), req.getStartTimeStart()));
            }
            if(req.getStartTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("startTime"), req.getStartTimeEnd()));
            }
            // rock_character = ?
            if(req.getRockCharacter() != null){
                predicates.add(builder.equal(root.get("rockCharacter"), req.getRockCharacter()));
            }
            // support_method = ?
            if(req.getSupportMethod() != null){
                predicates.add(builder.equal(root.get("supportMethod"), req.getSupportMethod()));
            }
            // cross_section_type = ?
            if(req.getCrossSectionType() != null){
                predicates.add(builder.equal(root.get("crossSectionType"), req.getCrossSectionType()));
            }
            if(req.getDrivingTechnologyType() != null){
                predicates.add(builder.equal(root.get("drivingTechnologyType"), req.getDrivingTechnologyType()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
