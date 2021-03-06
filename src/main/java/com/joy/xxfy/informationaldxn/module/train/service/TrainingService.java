package com.joy.xxfy.informationaldxn.module.train.service;

import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.module.train.domain.entity.TrainingEntity;
import com.joy.xxfy.informationaldxn.module.train.domain.repository.TrainingAttachmentRepository;
import com.joy.xxfy.informationaldxn.module.train.domain.repository.TrainingPhotoRepository;
import com.joy.xxfy.informationaldxn.module.train.domain.repository.TrainingRepository;
import com.joy.xxfy.informationaldxn.module.train.web.req.TrainingAddReq;
import com.joy.xxfy.informationaldxn.module.train.web.req.TrainingGetListReq;
import com.joy.xxfy.informationaldxn.module.train.web.req.TrainingUpdateReq;
import com.joy.xxfy.informationaldxn.module.train.web.res.TrainingRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 添加
     */
    public JoyResult add(TrainingAddReq req) {
        TrainingEntity addTrainingInfo = new TrainingEntity();
//        // 验证名称
//        TrainingEntity checkInfo = trainingRepository.findFirstByTrainingName(req.getTrainingName());
//        if(checkInfo != null){
//            return JoyResult.buildFailedResult(Notice.TRAINING_NAME_ALREADY_EXIST);
//        }
        // 验证煤矿信息
        DepartmentEntity companyEntity = departmentRepository.findAllById(req.getCompanyId());
        if(companyEntity == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }
        // 验证部门信息
        DepartmentEntity departmentEntity = departmentRepository.findAllById(req.getDepartmentId());
        if(departmentEntity == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, addTrainingInfo);
        addTrainingInfo.setCompany(companyEntity);
        addTrainingInfo.setDepartment(departmentEntity);
        TrainingEntity saveResult = trainingRepository.save(addTrainingInfo);
        // 设置返回结果，此时虽然没有关联的文件信息，但是还是要标准返回格式
        TrainingRes res = new TrainingRes();
        JoyBeanUtil.copyProperties(saveResult, res);
        // save.
        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 改
     */
    public JoyResult update(TrainingUpdateReq req) {
        TrainingEntity trainingInfo = trainingRepository.findAllById(req.getId());
        if(trainingInfo == null){
            return JoyResult.buildFailedResult(Notice.TRAINING_NOT_EXIST);
        }
//        // 验证名称
//        TrainingEntity checkInfo = trainingRepository.findFirstByTrainingNameAndIdNot(req.getTrainingName(), req.getId());
//        if(checkInfo != null){
//            return JoyResult.buildFailedResult(Notice.TRAINING_NAME_ALREADY_EXIST);
//        }
        // 验证煤矿信息
        DepartmentEntity companyEntity = departmentRepository.findAllById(req.getCompanyId());
        if(companyEntity == null){
            return JoyResult.buildFailedResult(Notice.COMPANY_NOT_EXIST);
        }
        // 验证部门信息
        DepartmentEntity departmentEntity = departmentRepository.findAllById(req.getDepartmentId());
        if(departmentEntity == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, trainingInfo);
        trainingInfo.setDepartment(departmentEntity);
        trainingInfo.setUpdateTime(new Date());
        // save.
        return JoyResult.buildSuccessResultWithData(trainingRepository.save(trainingInfo));
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
        // 删除培训相关文件
        trainingPhotoRepository.updateIsDeleteByTraining(trainingInfo,true);
        trainingAttachmentRepository.updateIsDeleteByTraining(trainingInfo, true);
        trainingRepository.save(trainingInfo);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        TrainingRes res = new TrainingRes();
        TrainingEntity trainingEntity = trainingRepository.findAllById(id);
        if(trainingEntity != null){
            JoyBeanUtil.copyProperties(trainingEntity, res);
            res.setTrainingAttachments(trainingAttachmentRepository.findAllByTraining(trainingEntity));
            res.setTrainingPhotos(trainingPhotoRepository.findAllByTraining(trainingEntity));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 获取数据(name)
     */
    public JoyResult getByName(String name) {
        TrainingRes res = new TrainingRes();
        TrainingEntity trainingEntity = trainingRepository.findFirstByTrainingName(name);
        if(trainingEntity != null){
            JoyBeanUtil.copyProperties(trainingEntity, res);
            res.setTrainingAttachments(trainingAttachmentRepository.findAllByTraining(trainingEntity));
            res.setTrainingPhotos(trainingPhotoRepository.findAllByTraining(trainingEntity));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
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
            if(req.getCompanyId() != null){
                predicates.add(builder.equal(root.get("company").get("id"), req.getCompanyId()));
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
