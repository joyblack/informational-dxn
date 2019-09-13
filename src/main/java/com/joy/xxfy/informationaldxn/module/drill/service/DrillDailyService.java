package com.joy.xxfy.informationaldxn.module.drill.service;

import cn.hutool.core.date.DateTime;
import com.joy.xxfy.informationaldxn.module.common.domain.vo.DateVo;
import com.joy.xxfy.informationaldxn.module.common.enums.FillDailyStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.common.web.res.FillResultRes;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.*;
import com.joy.xxfy.informationaldxn.module.drill.web.res.DrillDailyRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.LongValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.exception.JoyException;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.*;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.*;

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


    @Autowired
    private DrillHoleRepository drillHoleRepository;

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
        DrillDailyEntity checkRepeat = drillDailyRepository.findFirstByDrillWorkAndDrillTeamAndDailyTimeAndShifts(drillWorkInfo,
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
     * 上报（批量）
     */
    public JoyResult batchSave(DrillDailySaveReq req) {
        /**
         * 获取钻孔工作信息
         */
        DrillWorkEntity drillWork = drillWorkRepository.findAllById(req.getDrillWorkId());
        if(drillWork == null){
            throw new JoyException(Notice.DRILL_WORK_NOT_EXIST);
        }

        /**
         * 更新删除后应该重置的数据
         * 1. 更新钻孔工作进度
         * 2. 更新钻孔进度
         */
        List<DrillDailyDetailEntity> drillDailyDetais = drillDailyDetailRepository.findAllByDrillWorkAndDailyTime(drillWork, req.getDailyTime());
        // 钻孔工作需要减少的已打长度
        BigDecimal totalLength = BigDecimal.ZERO;
        // 钻孔工作需要减少的已完成钻孔数
        long changedCompletedDrillHoleNumber = 0;
        for (DrillDailyDetailEntity detail : drillDailyDetais) {
            totalLength = totalLength.add(detail.getDoneLength());
            DrillHoleEntity drillHole = detail.getDrillHole();
            // 修改钻孔信息,将钻孔长度进行回溯(还原删除数据处理掉的长度)
            // 已打长度：钻孔已打长度 - 已打长度
            drillHole.setDoneLength(ComputeUtils.sub(drillHole.getDoneLength(), detail.getDoneLength()));
            // 剩余长度: 重新计算
            drillHole.setLeftLength(ComputeUtils.sub(drillHole.getTotalLength(), drillHole.getDoneLength()));
            // 钻孔进度
            drillHole.setProgress(RateUtil.compute(drillHole.getDoneLength(), drillHole.getTotalLength(),false));
            // 如果是钻孔工作已经是完成状态（成孔日期不为空）：
            // 1.修改成孔日期为空
            // 2.钻孔工作的已完成数 - 1，剩余数量 + 1（总-已）
            if(drillHole.getCompleteTime() != null){
                // 设置为空，现在肯定是未完成状态
                drillHole.setCompleteTime(null);
                changedCompletedDrillHoleNumber ++;
            }
            // 保存钻孔信息
            drillHoleRepository.save(drillHole);
        }

        /**
         * 处理钻孔工作的进度信息
         */
        drillWork.setTotalDoneLength(drillWork.getTotalDoneLength().subtract(totalLength));
        // 未打总量：钻孔总量 - 已打总量
        drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));
        // 进度
        drillWork.setProgress(RateUtil.compute(drillWork.getTotalDoneLength(), drillWork.getTotalLength(),false));
        // 已施工钻孔数进度
        drillWork.setCompletedDrillHoleNumber(drillWork.getCompletedDrillHoleNumber() - changedCompletedDrillHoleNumber);
        drillWork.setNotCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());

        /**
         * 删除旧数据
         * 1. 删除所有日报详情信息
         * 2. 删除日报信息
         */
        LogUtil.info("删除旧数据...");
        drillDailyDetailRepository.deleteAllByDrillDailyIn(drillDailyRepository.findAllByDrillWork(drillWork));
        drillDailyRepository.deleteAllByDrillWorkAndDailyTime(drillWork, req.getDailyTime());

        /**
         * 写入数据
         * 1. 日报信息
         * 2. 日报详情信息
         */
        for (DrillDailySaveItem daily : req.getItems()) {
            // 验证（打钻队伍）部门是否存在
            DepartmentEntity drillTeam = departmentRepository.findAllById(daily.getDrillTeamId());
            if(drillTeam == null){
                return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
            }
            // 验证是否重复的日报信息（打钻工作、日期、班次、打钻队伍联合唯一）
            DrillDailyEntity checkRepeat = drillDailyRepository.findFirstByDrillWorkAndDrillTeamAndDailyTimeAndShifts(drillWork,
                    drillTeam, req.getDailyTime(), daily.getShifts());
            if(checkRepeat != null){
                throw new JoyException(Notice.DAILY_ALREADY_EXIST);
            }

            /**
             * 添加日报信息
             */
            DrillDailyEntity drillDaily = new DrillDailyEntity();
            drillDaily.setDrillWork(drillWork);
            drillDaily.setDailyTime(req.getDailyTime());
            drillDaily.setShifts(daily.getShifts());
            drillDaily.setDrillTeam(drillTeam);
            drillDaily.setPeopleNumber(TransferValueUtil.get(daily.getPeopleNumber()));
            // 总长为其子元素叠加的总和，即钻孔的长度总和
            drillDaily.setTotalDoneLength(BigDecimal.ZERO);
            Optional<BigDecimal> reduce = daily.getItems().stream().map(DrillDailySaveItemItem::getDoneLength).reduce(BigDecimal::add);
            reduce.ifPresent(drillDaily::setTotalDoneLength);
            drillDaily.setRemarks(daily.getRemarks());
            drillDaily = drillDailyRepository.save(drillDaily);

            /**
             * 日报详情信息
             * 这里需要注意的是保证钻孔ID是该工作面下面的。
             */
            for (DrillDailySaveItemItem item : daily.getItems()) {
                // 钻孔信息
                DrillHoleEntity drillHole = drillHoleRepository.findAllById(item.getDrillHoleId());
                if(drillHole == null){
                    return JoyResult.buildFailedResult(Notice.DRILL_HOLE_NOT_EXIST);
                }
                /**
                 * 验证是否是重复的记录：（一个日报仅允许添加一条钻孔信息，这样的信息只允许存在一条）
                 * 日报固定的情况下：班次、日期、队伍都固定了。
                 */
                DrillDailyDetailEntity exist = drillDailyDetailRepository.findFirstByDrillDailyAndDrillHole(drillDaily, drillHole);
                if(exist != null){
                    throw new JoyException(Notice.DAILY_ALREADY_EXIST);
                }
                /**
                 * 若当前提交的长度大于钻孔的剩余长度，不合法
                 */
                if(more(item.getDoneLength(),drillHole.getLeftLength())){
                    throw new JoyException(Notice.SET_LENGTH_MORE_LEFT_LENGTH + ":" + ResultDataConstant.MESSAGE_LEFT_LENGTH + drillHole.getLeftLength());
                }

                /**
                 * 修改钻孔信息
                 */
                drillHole.setDoneLength(drillHole.getDoneLength().add(item.getDoneLength()));
                drillHole.setLeftLength(drillHole.getTotalLength().subtract(drillHole.getDoneLength()));
                drillHole.setProgress(RateUtil.compute(drillHole.getDoneLength(), drillHole.getTotalLength(),false));
                /**
                 * 已完成的处理：
                 * 1. 设置成孔日期参数为日报时间；
                 * 2. 钻孔工作已施工钻孔数、剩余数修改；
                 */
                if(equal(drillHole.getDoneLength(), drillHole.getTotalLength())){
                    drillHole.setCompleteTime(drillDaily.getDailyTime());
                    drillWork.setCompletedDrillHoleNumber(drillWork.getCompletedDrillHoleNumber() + LongValueConstant.ONE);
                    drillWork.setNotCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());
                }
                /**
                 * 钻孔工作信息修改
                 */
                drillWork.setTotalDoneLength(drillWork.getTotalDoneLength().add(item.getDoneLength()));
                drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));
                drillWork.setProgress(RateUtil.compute(drillWork.getTotalDoneLength(), drillWork.getTotalLength(),false));

                /**
                 * 持久化钻孔日报详情信息
                 */
                DrillDailyDetailEntity detail = new DrillDailyDetailEntity();
                detail.setOrderNumber(item.getOrderNumber());
                detail.setDrillHole(drillHole);
                detail.setRemarks(item.getRemarks());
                detail.setDrillDaily(drillDaily);
                detail.setDoneLength(item.getDoneLength());
                drillDailyDetailRepository.save(detail);
            }
        }
        /**
         * 最后保存工作信息
         */
        drillWorkRepository.save(drillWork);

        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_ADD_SUCCESS);
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
        drillDailyDetailRepository.deleteAllByDrillDaily(info);

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
                predicates.add(builder.equal(root.get("drillWork").get("id"), req.getDrillWorkId()));
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

    public JoyResult getMonthFillStatus(TimeReq req, UserEntity loginUser) {
        Date start = DateUtil.getMonthFirstDay(req.getTime());
        Date end = DateUtil.getMonthLastDay(req.getTime());
        Set<DateVo> allFillDate = drillDailyRepository.findAllFillDate(start, end, loginUser.getCompany());
        List<FillResultRes> result = new ArrayList<>();
        while(DateUtil.compare(start, end) <= 0){
            FillResultRes res = new FillResultRes();
            res.setDate(DateUtil.format(start));
            // 查询本日是否存在日报信息
            if(allFillDate.contains(new DateVo(start))){
                res.setInfo(FillDailyStatusEnum.FILL_YES);
                allFillDate.remove(start);
            }else{
                res.setInfo(FillDailyStatusEnum.FILL_NO);
            }
            start = DateUtil.addDay(start, 1);
            result.add(res);
        }
        return JoyResult.buildSuccessResultWithData(result);
    }

}
