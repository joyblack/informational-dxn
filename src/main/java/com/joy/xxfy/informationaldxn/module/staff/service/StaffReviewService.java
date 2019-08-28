package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enums.ReviewStatusEnum;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.module.staff.web.req.ReviewReq;
import com.joy.xxfy.informationaldxn.module.staff.web.req.StaffEntryGetListReq;
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
public class StaffReviewService {
    @Autowired
    private StaffEntryRepository staffEntryRepository;

    /**
     * 获取分页数据，相比较员工列表，这里只选取处于审核状态以及审核不通过的员工信息
     */
    public JoyResult getPagerList(StaffEntryGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部数据，相比较员工列表，这里只选取处于审核状态以及审核不通过的员工信息
     */
    public JoyResult getAllList(StaffEntryGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffEntryEntity> getPredicates(StaffEntryGetListReq req){
        return (Specification<StaffEntryEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // username like
            if(!StringUtil.isEmpty(req.getUsername())){
                predicates.add(builder.like(root.get("staffPersonal").get("username"), "%" + req.getUsername() +"%"));
            }
            // phone like
            if(!StringUtil.isEmpty(req.getPhone())){
                predicates.add(builder.like(root.get("staffPersonal").get("phone"), "%" + req.getPhone() +"%"));
            }
            // idNumber like
            if(!StringUtil.isEmpty(req.getIdNumber())){
                predicates.add(builder.like(root.get("staffPersonal").get("idNumber"), "%" + req.getIdNumber() +"%"));
            }
            // nationality =
            if(!StringUtil.isEmpty(req.getNationality())){
                predicates.add(builder.equal(root.get("staffPersonal").get("nationality"), req.getNationality()));
            }
            // department =
            if(req.getDepartmentId() != null){
                predicates.add(builder.equal(root.get("department").get("id"), req.getDepartmentId()));
            }
            // education =
            if(req.getEducation() != null){
                predicates.add(builder.equal(root.get("staffPersonal").get("education"), req.getEducation()));
            }
            // sex =
            if(req.getSex() != null){
                predicates.add(builder.equal(root.get("staffPersonal").get("sex"), req.getSex()));
            }
            // WAIT AND NOT_PASS OR select someone.
            if(req.getReviewStatus() != null){
                predicates.add(builder.equal(root.get("reviewStatus"), req.getReviewStatus()));
            }else{
                predicates.add(builder.or(
                        builder.equal(root.get("reviewStatus"), ReviewStatusEnum.NOT_PASS),
                        builder.equal(root.get("reviewStatus"), ReviewStatusEnum.WAIT))
                );
            }

            // position =
            if(req.getPositionId() != null){
                predicates.add(builder.equal(root.get("position").get("id"), req.getPositionId()));
            }
            // birth between
            if(req.getBirthDateStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("staffPersonal").get("birthDate"), req.getBirthDateStart()));
            }
            if(req.getBirthDateEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("staffPersonal").get("birthDate"), req.getBirthDateEnd()));
            }
            // entry time
            if(req.getEntryTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("entryTime"), req.getEntryTimeStart()));
            }
            if(req.getEntryTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("entryTime"), req.getEntryTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    /**
     * 对员工入职信息进行审核
     */
    public JoyResult review(ReviewReq req, UserEntity loginUser) {
        // must be select review result.
        if(req.getReviewStatus().equals(ReviewStatusEnum.WAIT)){
            return JoyResult.buildFailedResult(Notice.STAFF_REVIEW_STATUS_MUST_BE_SELECT);
        }
        // 入职信息获取
        StaffEntryEntity entryInfo = staffEntryRepository.findAllById(req.getEntryId());
        if(entryInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_ENTRY_NOT_EXIST);
        }
        // 设置审核结果
        // 审核时间
        entryInfo.setReviewTime(new Date());
        // 状态
        entryInfo.setReviewStatus(req.getReviewStatus());
        // 审核备注信息
        entryInfo.setReviewRemarks(req.getReviewRemarks());
        // 设置审核人信息
        entryInfo.setReviewUser(loginUser);
        return JoyResult.buildSuccessResultWithData(staffEntryRepository.save(entryInfo));
    }
}
