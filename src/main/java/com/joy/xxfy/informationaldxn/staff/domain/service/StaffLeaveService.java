package com.joy.xxfy.informationaldxn.staff.domain.service;

import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.PhoneUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffLeaveRepository;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffLeaveAddReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffLeaveGetListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaffLeaveService {

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffLeaveRepository staffLeaveRepository;

    /**
     * 添加员工入职信息
     */
    public JoyResult add(StaffLeaveAddReq req) {
        List<Long> entryIds = req.getEntries();
        LogUtil.info("入职信息ID列表：{}", entryIds);
        // 一个职位一个职位的处理，不要批处理
        List<StaffEntryEntity> entries = new ArrayList<>();
        for (Long entryId : entryIds) {
            entries.add(staffEntryRepository.findAllById(entryId));
        }

        for (StaffEntryEntity entry : entries) {
            if(entry == null){
                continue;
            }
            // 建立离职信息
            StaffLeaveEntity leaveInfo = new StaffLeaveEntity();
            // 离职时间
            leaveInfo.setLeaveTime(req.getLeaveTime());
            // 离职类型
            leaveInfo.setLeaveType(req.getLeaveType());
            leaveInfo.setCompany(entry.getCompany());
            leaveInfo.setPosition(entry.getPosition());
            leaveInfo.setStaffPersonal(entry.getStaffPersonal());
            leaveInfo.setDepartment(entry.getDepartment());
            // 删除入职信息
            LogUtil.info("Delete entry info is: {} ", entry);
            entry.setIsDelete(true);
            staffEntryRepository.save(entry);
            // 新建离职信息
            StaffLeaveEntity save = staffLeaveRepository.save(leaveInfo);
            LogUtil.info("New leave info is: {}", save);
        }
        // Delete staff entry information.
        return JoyResult.buildSuccessResult("操作成功");
    }

//    public JoyResult update(StaffEntryEntity entry) {
//
//    }

    public JoyResult delete(Long id) {
        StaffLeaveEntity dbEntry = staffLeaveRepository.findAllById(id);
        if(dbEntry == null){
            return JoyResult.buildFailedResult(Notice.STAFF_LEAVE_NOT_EXIST);
        }
        dbEntry.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(staffLeaveRepository.save(dbEntry));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffLeaveRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(StaffLeaveGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffLeaveRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(StaffLeaveGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffLeaveRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffLeaveEntity> getPredicates(StaffLeaveGetListReq req){
        return (Specification<StaffLeaveEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // username like
            if(!StringUtil.isEmpty(req.getUsername())){
                predicates.add(builder.like(root.get("staffPersonal").get("username"), "%" + req.getUsername() +"%"));
            }
            // insured
            if(req.getInsured() != null){
                predicates.add(builder.equal(root.get("staffPersonal").get("insured"), req.getInsured()));
            }
            // leave_type =
            if(req.getLeaveType() != null){
                predicates.add(builder.equal(root.get("leaveType"), req.getLeaveType()));
            }
            // company =
            if(req.getCompanyId() != null){
                predicates.add(builder.equal(root.get("company").get("id"), req.getCompanyId()));
            }
            // department =
            if(req.getDepartmentId() != null){
                predicates.add(builder.equal(root.get("department").get("id"), req.getDepartmentId()));
            }
            // position =
            if(req.getPositionId() != null){
                predicates.add(builder.equal(root.get("position").get("id"), req.getPositionId()));
            }
            // leave_time between
            if(req.getLeaveTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("leaveTime"), req.getLeaveTimeStart()));
            }
            if(req.getLeaveTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("leaveTime"), req.getLeaveTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
