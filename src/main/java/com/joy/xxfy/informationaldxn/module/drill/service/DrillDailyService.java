package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.*;
import com.joy.xxfy.informationaldxn.module.drill.web.res.DrillDailyRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.StringUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class DrillDailyService {

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DrillDailyDetailRepository drillDailyDetailRepository;

    /**
     * 添加
     */
    public JoyResult add(DrillDailyAddReq req) {
        // 验证打钻工作信息是否存在
        DrillWorkEntity drillWorkInfo = drillWorkRepository.findAllById(req.getDrillWorkId());
        if(drillWorkInfo == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // 验证（打钻队伍）部门是否存在
        DepartmentEntity drillTeam = departmentRepository.findAllById(req.getDrillTeamId());
        if(drillTeam == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 验证是否重复的日报信息（打钻工作、日期、班次、打钻队伍联合唯一）
        DrillDailyEntity checkRepeat = drillDailyRepository.findAllByDrillWorkAndDrillTeamAndDailyTimeAndShifts(drillWorkInfo,
                drillTeam, req.getDailyTime(), req.getShifts());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_DETAIL_REPEAT);
        }
        // 装配信息
        DrillDailyEntity drillDailyEntity = new DrillDailyEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, drillDailyEntity);
        // 当日打钻总量
        drillDailyEntity.setTotalDoneLength(BigDecimal.ZERO);
        drillDailyEntity.setDrillTeam(drillTeam);
        drillDailyEntity.setDrillWork(drillWorkInfo);
        // 添加信息
        return JoyResult.buildSuccessResultWithData(drillDailyRepository.save(drillDailyEntity));
    }

    /**
     * 改:待定
     */
    public JoyResult update(DrillDailyUpdateReq req) {
        // 日报信息是否存在
        DrillDailyEntity drillDaily = drillDailyRepository.findAllById(req.getId());
        if(drillDaily == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 验证打钻工作信息是否存在
        DrillWorkEntity drillWorkInfo = drillWorkRepository.findAllById(req.getDrillWorkId());
        if(drillWorkInfo == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // 只放任人数和备注信息的修改
        drillDaily.setPeopleNumber(req.getPeopleNumber());
        drillDaily.setRemarks(req.getRemarks());
        // save.
        return JoyResult.buildSuccessResultWithData(drillDailyRepository.save(drillDaily));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        // 获取日报信息
        DrillDailyEntity info = drillDailyRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }

        // 删除该日报的打钻详情信息
        drillDailyDetailRepository.updateIsDeleteByDrillDaily(info, true);

        // 最后删除日报信息
        info.setIsDelete(true);
        drillDailyRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        DrillDailyRes res = new DrillDailyRes();
        DrillDailyEntity info = drillDailyRepository.findAllById(id);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 设置打钻详情信息
            res.setDrillDailyDetail(drillDailyDetailRepository.findAllByDrillDaily(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }


    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(DrillDailyGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drillDailyRepository.findAll(getPredicates(req, loginUser), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(DrillDailyGetListReq req, UserEntity loginUser) {
        return JoyResult.buildSuccessResultWithData(drillDailyRepository.findAll(getPredicates(req, loginUser)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<DrillDailyEntity> getPredicates(DrillDailyGetListReq req, UserEntity loginUser){
        return (Specification<DrillDailyEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("drillWork").get("belongCompany"), loginUser.getCompany()));
            if(req.getDrillWorkId() != null){
                predicates.add(builder.equal(root.get("drillWork").get("drillWorkId"), req.getDrillWorkId()));
            }
            // drill_time between
            if(req.getDailyTimeStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("dailyTime"), req.getDailyTimeStart()));
            }
            if(req.getDailyTimeEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("dailyTime"), req.getDailyTimeEnd()));
            }
            if(req.getShifts() != null){
                predicates.add(builder.equal(root.get("shifts"), req.getShifts()));
            }
            if(req.getDrillTeamId() != null){
                predicates.add(builder.equal(root.get("drillTeam").get("id"), req.getDrillTeamId()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
