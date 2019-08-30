package com.joy.xxfy.informationaldxn.module.driving.service;

import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingFaceAddReq;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingFaceGetListReq;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingFaceUpdateReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
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
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DrivingFaceService {

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    /**
     * 添加
     */
    public JoyResult add(DrivingFaceAddReq req, UserEntity loginUser) {
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
        drivingFaceInfo.setBelongCompany(loginUser.getCompany());
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.save(drivingFaceInfo));
    }

    /**
     * 改
     */
    public JoyResult update(DrivingFaceUpdateReq req, UserEntity loginUser) {
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
        drivingFaceInfo.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.save(drivingFaceInfo));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        DrivingFaceEntity drivingFaceInfo = drivingFaceRepository.findAllById(id);
        if(drivingFaceInfo == null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NOT_EXIST);
        }
        // 已添加日报的工作面无法删除（必须将日报删除了，才能删工作面）
        List<DrivingDailyEntity> dailies = drivingDailyRepository.findAllByDrivingFace(drivingFaceInfo);
        if(dailies.size() > 0){
            return JoyResult.buildFailedResult(Notice.DAILY_EXIST_CANT_DELETE);
        }
        drivingFaceInfo.setUpdateTime(new Date());
        drivingFaceInfo.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.save(drivingFaceInfo));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        // get older
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAllById(id));
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name, UserEntity loginUser) {
        // get older
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAllByBelongCompanyAndDrivingFaceNameContaining(loginUser.getCompany(), name));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrivingFaceGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAll(getPredicates(req,loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrivingFaceGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drivingFaceRepository.findAll(getPredicates(req,loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrivingFaceEntity> getPredicates(DrivingFaceGetListReq req, UserEntity loginUser){
        return (Specification<DrivingFaceEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // belong
            predicates.add(builder.equal(root.get("belongCompany"),loginUser.getCompany()));

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
