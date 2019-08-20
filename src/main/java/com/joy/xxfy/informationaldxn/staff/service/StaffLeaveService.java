package com.joy.xxfy.informationaldxn.staff.service;

import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.*;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffBlacklistEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffEntryEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffLeaveEntity;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffBlacklistRepository;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffEntryRepository;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffLeaveRepository;
import com.joy.xxfy.informationaldxn.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.staff.domain.template.StaffTemplate;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffLeaveAddReq;
import com.joy.xxfy.informationaldxn.staff.web.req.StaffLeaveGetListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StaffLeaveService {

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffLeaveRepository staffLeaveRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private StaffBlacklistRepository staffBlacklistRepository;

    @Value("${system.config.staff-black-list-over-month}")
    private Integer overMonth;

    /**
     * 添加员工入职信息
     */
    public JoyResult add(StaffLeaveAddReq req) {
        List<Long> entryIds = req.getEntries();
        // 获取员工个人信息
        String idNumber = req.getIdNumber();
        StaffPersonalEntity personalInfo = staffPersonalRepository.findAllByIdNumber(idNumber);
        if(personalInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_PERSONAL_INFO_NOT_EXIST);
        }
        // 一个职位一个职位的处理，不要批处理
        List<StaffEntryEntity> entries = new ArrayList<>();
        for (Long entryId : entryIds) {
            entries.add(staffEntryRepository.findAllById(entryId));
        }
        // 是否执行了离职操作
        boolean executeLeaveOpt = false;
        for (StaffEntryEntity entry : entries) {
            if(entry == null){
                continue;
            }
            executeLeaveOpt = true;
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
            staffLeaveRepository.save(leaveInfo);
        }
        // 若是最后一个职位，则自动加入黑名单。
        if(executeLeaveOpt == true){
            // 检测员工是否还有职位信息
            List<StaffEntryEntity> entities = staffEntryRepository.findAllByStaffPersonal(personalInfo);
            // 若没有职位，开始加入黑名单
            if(entities.size() == 0){
                // 查找离职信息表中员工最后一条离职的信息
                StaffLeaveEntity lastLeaveInfo = staffLeaveRepository.findFirstByStaffPersonalOrderByCreateTimeDesc(personalInfo);
                if(lastLeaveInfo != null){
                    LogUtil.info("The last leave job info: {}", lastLeaveInfo);
                    // 加入黑名单
                    StaffBlacklistEntity staffBlacklistInfo = new StaffBlacklistEntity();
                    // 黑名单信息设置
                    // 公司：即最后一个离职所在公司
                    staffBlacklistInfo.setCompany(lastLeaveInfo.getCompany());
                    // 解禁时间，取项目时间配置，此处可考虑写入系统配置表
                    staffBlacklistInfo.setOverTime(DateOperationUtil.addMonth(new Date(), overMonth));
                    // 原因
                    staffBlacklistInfo.setBlacklistReasons(StaffTemplate.LEAVE_THEN_ADD_IN_BLACKLIST_REASONS);
                    // 个人信息
                    staffBlacklistInfo.setStaffPersonal(personalInfo);
                    // 备注
                    staffBlacklistInfo.setRemarks(StaffTemplate.SYSTEM_OPERATE_REMARK);
                    LogUtil.info("Generated blacklist info is : {}", staffBlacklistInfo);
                    staffBlacklistRepository.save(staffBlacklistInfo);
                }else{
                    LogUtil.info("Can't fina leave job info.");
                }
            }
        }

        // Delete staff entry information.
        return JoyResult.buildSuccessResult("Success.");
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
