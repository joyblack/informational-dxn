package com.joy.xxfy.informationaldxn.module.staff.service;

import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.identity.IdNumberUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffInjuryEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.enetiy.StaffPersonalEntity;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffInjuryRepository;
import com.joy.xxfy.informationaldxn.module.staff.domain.repository.StaffPersonalRepository;
import com.joy.xxfy.informationaldxn.module.staff.web.req.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaffInjuryService {

    @Autowired
    private StaffInjuryRepository staffInjuryRepository;

    @Autowired
    private StaffPersonalRepository staffPersonalRepository;

    /**
     * 添加
     */
    public JoyResult add(StaffInjuryAddReq req) {
        StaffInjuryEntity injuryInfo = new StaffInjuryEntity();
         // 验证身份证
        if(!IdNumberUtil.isIDNumber((req.getIdNumber()))){
            return JoyResult.buildFailedResult(Notice.IDENTITY_NUMBER_ERROR);
        }
        // 个人信息
        StaffPersonalEntity personalInfo = staffPersonalRepository.findAllByIdNumber(req.getIdNumber());
        if(personalInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_PERSONAL_INFO_NOT_EXIST);
        }else {
            injuryInfo.setStaffPersonal(personalInfo);
        }
        // 工伤原因
        injuryInfo.setInjuryReasons(req.getInjuryReasons());
        // 主治医院
        injuryInfo.setHospital(req.getHospital());
        // 医治时间
        injuryInfo.setTreatTime(req.getTreatTime());
        // 工伤描述
        injuryInfo.setInjuryDescribes(req.getInjuryDescribes());
        // 工伤时间
        injuryInfo.setInjuryTime(req.getInjuryTime());
        // 备注信息
        injuryInfo.setRemarks(req.getRemarks());
        LogUtil.info("Injury info is：{}", injuryInfo);
        // save.
        return JoyResult.buildSuccessResultWithData(staffInjuryRepository.save(injuryInfo));
    }

    /**
     * 改
     */
    public JoyResult update(StaffInjuryUpdateReq req) {
        StaffInjuryEntity injuryInfo = staffInjuryRepository.findAllById(req.getId());
        if(injuryInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_INJURY_INFO_NOT_EXIST);
        }
        // 主治医院
        injuryInfo.setHospital(req.getHospital());
        // 医治时间
        injuryInfo.setTreatTime(req.getTreatTime());
        // 工伤描述
        injuryInfo.setInjuryDescribes(req.getInjuryDescribes());
        // 工伤时间
        injuryInfo.setInjuryTime(req.getInjuryTime());
        // 备注信息
        injuryInfo.setRemarks(req.getRemarks());
        LogUtil.info("New Injury info is：{}", injuryInfo);
        // save.
        return JoyResult.buildSuccessResultWithData(staffInjuryRepository.save(injuryInfo));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        StaffInjuryEntity injuryInfo = staffInjuryRepository.findAllById(id);
        if(injuryInfo == null){
            return JoyResult.buildFailedResult(Notice.STAFF_INJURY_INFO_NOT_EXIST);
        }
        injuryInfo.setIsDelete(true);
        return JoyResult.buildSuccessResultWithData(staffInjuryRepository.save(injuryInfo));
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        // get older
        return JoyResult.buildSuccessResultWithData(staffInjuryRepository.findAllById(id));
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(StaffInjuryGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffInjuryRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(StaffInjuryGetListReq req) {
        return JoyResult.buildSuccessResultWithData(staffInjuryRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StaffInjuryEntity> getPredicates(StaffInjuryGetListReq req){
        return (Specification<StaffInjuryEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // username like
            if(!StringUtil.isEmpty(req.getUsername())){
                predicates.add(builder.like(root.get("staffPersonal").get("username"), "%" + req.getUsername() +"%"));
            }
            // idNumber like
            if(!StringUtil.isEmpty(req.getIdNumber())){
                predicates.add(builder.like(root.get("staffPersonal").get("idNumber"), "%" + req.getIdNumber() +"%"));
            }
            // phone like
            if(!StringUtil.isEmpty(req.getPhone())){
                predicates.add(builder.like(root.get("staffPersonal").get("phone"), "%" + req.getPhone() +"%"));
            }

            // injury_reasons like
            if(req.getInjuryReasons() != null){
                predicates.add(builder.like(root.get("injuryReasons"), "%" + req.getInjuryReasons() + "%"));
            }
            // injury_time between
            if(req.getInjuryTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("injuryTime"), req.getInjuryTimeStart()));
            }
            if(req.getInjuryTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("injuryTime"), req.getInjuryTimeEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
