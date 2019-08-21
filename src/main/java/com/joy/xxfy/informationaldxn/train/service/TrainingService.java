package com.joy.xxfy.informationaldxn.train.service;

import com.joy.xxfy.informationaldxn.produce.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffInjuryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffInjuryRepository;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffInjuryAddReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffInjuryGetListReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffInjuryUpdateReq;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingAttachmentEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.train.domain.entity.TrainingPhotoEntity;
import com.joy.xxfy.informationaldxn.train.domain.repository.TrainingAttachmentRepository;
import com.joy.xxfy.informationaldxn.train.domain.repository.TrainingPhotoRepository;
import com.joy.xxfy.informationaldxn.train.domain.repository.TrainingRepository;
import com.joy.xxfy.informationaldxn.train.web.req.TrainingAddReq;
import com.joy.xxfy.informationaldxn.train.web.req.TrainingGetListReq;
import com.joy.xxfy.informationaldxn.train.web.req.TrainingUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainingService {

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingPhotoRepository trainingPhotoRepository;

    @Autowired
    private TrainingAttachmentRepository trainingAttachmentRepository;

    /**
     * 添加
     */
    public JoyResult add(TrainingAddReq req) {
        TrainingEntity addTrainingInfo = new TrainingEntity();
        // 验证名称
        TrainingEntity checkInfo = trainingRepository.findAllByTrainingName(req.getTrainingName());
        if(checkInfo == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NAME_ALREADY_EXIST);
        }
        // 装配数据
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, addTrainingInfo);
        // 设置图片、附件
        req.getTrainingPhotoIds().forEach(photoId -> {
            TrainingPhotoEntity photo = trainingPhotoRepository.findAllById(photoId);
            if(photo != null){
                addTrainingInfo.getTrainingPhotos().add(photo);
            }
        });
        req.getTrainingPhotoIds().forEach(photoId -> {
            TrainingAttachmentEntity attachment = trainingAttachmentRepository.findAllById(photoId);
            if(attachment != null){
                addTrainingInfo.getTrainingAttachments().add(attachment);
            }
        });
        // save.
        return JoyResult.buildSuccessResultWithData(trainingRepository.save(addTrainingInfo));
    }

    /**
     * 改
     */
    public JoyResult update(TrainingUpdateReq req) {
        TrainingEntity trainingInfo = trainingRepository.findAllById(req.getId());
        if(trainingInfo == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NOT_EXIST);
        }
        // 验证名称
        TrainingEntity checkInfo = trainingRepository.findAllByTrainingNameAndIdNot(req.getTrainingName(), req.getId());
        if(checkInfo == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NAME_ALREADY_EXIST);
        }
        // 删除旧图片、附件信息
        trainingInfo.getTrainingAttachments().forEach(f -> f.setIsDelete(true));
        trainingInfo.getTrainingPhotos().forEach(f -> f.setIsDelete(true));
        TrainingEntity save = trainingRepository.save(trainingInfo);

        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, save);
        // 填入新的图片、附件信息
        // 设置图片、附件
        req.getTrainingPhotoIds().forEach(photoId -> {
            TrainingPhotoEntity photo = trainingPhotoRepository.findAllById(photoId);
            if(photo != null){
                save.getTrainingPhotos().add(photo);
            }
        });
        req.getTrainingPhotoIds().forEach(photoId -> {
            TrainingAttachmentEntity attachment = trainingAttachmentRepository.findAllById(photoId);
            if(attachment != null){
                save.getTrainingAttachments().add(attachment);
            }
        });
        // save.
        return JoyResult.buildSuccessResultWithData(trainingRepository.save(save));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        TrainingEntity trainingInfo = trainingRepository.findAllById(id);
        if(trainingInfo == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NOT_EXIST);
        }
        trainingInfo.setIsDelete(true);
        // 详细信息
        trainingInfo.getTrainingPhotos().forEach(d -> d.setIsDelete(true));
        trainingInfo.getTrainingAttachments().forEach(d -> d.setIsDelete(true));
        return JoyResult.buildSuccessResultWithData(trainingRepository.save(trainingInfo));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(trainingRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(TrainingGetListReq req) {
        return JoyResult.buildSuccessResultWithData(trainingRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(TrainingGetListReq req) {
        return JoyResult.buildSuccessResultWithData(trainingRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<TrainingEntity> getPredicates(TrainingGetListReq req){
        return (Specification<TrainingEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getTrainingName())){
                predicates.add(builder.like(root.get("trainingName"), "%" + req.getTrainingName() +"%"));
            }
            if(!StringUtil.isEmpty(req.getTrainingUsername())){
                predicates.add(builder.like(root.get("trainingUsername"), "%" + req.getTrainingUsername() +"%"));
            }
            if(req.getDepartmentId() != null){
                predicates.add(builder.equal(root.get("department").get("id"), req.getDepartmentId()));
            }

            // injury_time between
            if(req.getTrainingTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("trainingTime"), req.getTrainingTimeStart()));
            }
            if(req.getTrainingTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("trainingTime"), req.getTrainingTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
