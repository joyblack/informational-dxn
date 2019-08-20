package com.joy.xxfy.informationaldxn.staff.domain.service;

import cn.hutool.core.date.DateUtil;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.DateOperationUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.staff.domain.enetiy.*;
import com.joy.xxfy.informationaldxn.staff.domain.repository.*;
import com.joy.xxfy.informationaldxn.staff.web.req.*;
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
public class StaffBlacklistService {

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    @Autowired
    private StaffBlacklistRepository staffBlacklistRepository;

    @Autowired
    private StaffEntryRepository staffEntryRepository;

    @Autowired
    private StaffLeaveRepository staffLeaveRepository;

    @Value("${system.config.staff-black-list-over-month}")
    private Integer overMonth;

    /**
     * 添加
     */
    public JoyResult add(StaffBlacklistAddReq req) {
        StaffBlacklistEntity staffBlacklistInfo = new StaffBlacklistEntity();
         // 验证身份证
        if(!IdNumberUtil.isIDNumber((req.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        // 个人信息验证
        StaffPersonalEntity personalInfo = staffPersonalRepository.findAllByIdNumber(req.getIdNumber());
        if(personalInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_PERSONAL_INFO_NOT_EXIST);
        }
        // 查找黑名单中是否存在该员工
        StaffBlacklistEntity checkResult = staffBlacklistRepository.findAllByStaffPersonal(personalInfo);
        if(checkResult != null){
            return JoyResult.buildFailedResult(Notice.STAFF_BLACKLIST_ALREADY_EXIST);
        }
        // 查找员工是否在职，若在职，不允许添加黑名单
        List<StaffEntryEntity> allByStaffPersonal = staffEntryRepository.findAllByStaffPersonal(personalInfo);
        if(allByStaffPersonal.size() > 0){
            return JoyResult.buildFailedResult(Notice.STAFF_STILL_ENTRY);
        }
        // 查找离职信息表中员工最后一条离职的信息
        StaffLeaveEntity lastLeaveInfo = staffLeaveRepository.findFirstByStaffPersonalOrderByCreateTimeDesc(personalInfo);
        if(lastLeaveInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_LEAVE_NOT_EXIST);
        }
        // 黑名单信息设置
        // 公司：即最后一个离职所在公司
        staffBlacklistInfo.setCompany(lastLeaveInfo.getCompany());
        // 解禁时间
        // 若为设置解禁时间，取项目时间配置，此处可考虑写入系统配置表
        if(req.getOverTime() != null){
            staffBlacklistInfo.setOverTime(req.getOverTime());
        }else{
            staffBlacklistInfo.setOverTime(DateOperationUtil.addMonth(new Date(), overMonth));
        }
        // 原因
        staffBlacklistInfo.setBlacklistReasons(req.getBlacklistReasons());
        // 个人信息
        staffBlacklistInfo.setStaffPersonal(personalInfo);
        // 备注
        staffBlacklistInfo.setRemarks(req.getRemarks());
        // save.
        return JoyResult.buildSuccessResultWithData(staffBlacklistRepository.save(staffBlacklistInfo));
    }

    /**
     * 改
     */
    public JoyResult update(StaffBlacklistUpdateReq req) {
        // 只允许修改解禁时间以及原因
        StaffBlacklistEntity blackListInfo = staffBlacklistRepository.findAllById(req.getId());
        if(blackListInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_BLACKLIST_NOT_EXIST);
        }
        // 解禁时间
        // 若为设置解禁时间，取项目时间配置，此处可考虑写入系统配置表
        if(req.getOverTime() != null){
            blackListInfo.setOverTime(req.getOverTime());
        }
        // 原因
        blackListInfo.setBlacklistReasons(req.getBlacklistReasons());
        // 备注
        blackListInfo.setRemarks(req.getRemarks());
        LogUtil.info("New Injury info is：{}", blackListInfo);
        // save.
        return JoyResult.buildSuccessResultWithData(staffBlacklistRepository.save(blackListInfo));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        StaffBlacklistEntity blackListInfo = staffBlacklistRepository.findAllById(id);
        if(blackListInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_BLACKLIST_NOT_EXIST);
        }
        blackListInfo.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(staffBlacklistRepository.save(blackListInfo));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffBlacklistRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(StaffBlacklistGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffBlacklistRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(StaffBlacklistGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffBlacklistRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffBlacklistEntity> getPredicates(StaffBlacklistGetListReq req){
        return (Specification<StaffBlacklistEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // username like
            if(!StringUtil.isEmpty(req.getUsername())){
                predicates.add(builder.like(root.get("staffPersonal").get("username"), "%" + req.getUsername() +"%"));
            }
            // idNumber like
            if(!StringUtil.isEmpty(req.getIdNumber())){
                predicates.add(builder.like(root.get("staffPersonal").get("idNumber"), "%" + req.getIdNumber() +"%"));
            }
            // blacklist_reasons like
            if(req.getBlacklistReasons() != null){
                predicates.add(builder.equal(root.get("blacklistReasons"), req.getBlacklistReasons()));
            }
            // injury_time between
            if(req.getOverTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("overTime"), req.getOverTimeStart()));
            }
            if(req.getOverTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("overTime"), req.getOverTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
