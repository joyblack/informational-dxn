package com.joy.xxfy.informationaldxn.module.statistic.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.repository.CmPlatformRepository;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.IkAndBvVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SkAndBvVo;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.repository.ProduceCmDailyRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo;
import com.joy.xxfy.informationaldxn.module.produce.web.res.GetThisMonthOutputRes;
import com.joy.xxfy.informationaldxn.module.produce.web.res.GetTodayOutputRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.TransferValueUtil;
import com.joy.xxfy.informationaldxn.publish.utils.format.AntVFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatisticOutputService {

    @Autowired
    private ProduceCmDailyRepository produceCmDailyRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    @Autowired
    private CmPlatformRepository cmPlatformRepository;


    /**
     * 统计今日产煤：回采 + 掘进
     */
    public JoyResult getToday(UserEntity loginUser) {
        Date now = DateUtil.getDateJustYMD(new Date());
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 获取今日统计
         */
        StatisticOutputVo v1 = drivingDailyRepository.statisticTodayOutput(belongCompany, now);
        StatisticOutputVo v11 = backMiningDailyRepository.statisticTodayOutput(belongCompany, now);

        /**
         * 班次统计
         */
        List<StatisticOutputVo> v2 = drivingDailyRepository.statisticTodayOutputGroupByShifts(belongCompany, now);
        List<StatisticOutputVo> v22 = backMiningDailyRepository.statisticTodayOutputGroupByShifts(belongCompany, now);

        /**
         * 返回结果
         */
        GetTodayOutputRes res = new GetTodayOutputRes();
        // 今日产煤
        res.setTodayOutput(TransferValueUtil.get(v1.getOutput()).add(TransferValueUtil.get(v11.getOutput())));
        /**
         * 早中晚班
         */
        for (StatisticOutputVo vo : v2) {
            if(vo.getShifts().equals(DailyShiftEnum.MORNING)){
                res.setMorningOutput(TransferValueUtil.get(vo.getOutput()));
            }
            if(vo.getShifts().equals(DailyShiftEnum.NOON)){
                res.setNoonOutput(TransferValueUtil.get(vo.getOutput()));
            }
            if(vo.getShifts().equals(DailyShiftEnum.EVENING)){
                res.setEveningOutput(TransferValueUtil.get(vo.getOutput()));
            }
        }
        for (StatisticOutputVo vo : v22) {
            if(vo.getShifts().equals(DailyShiftEnum.MORNING)){
                res.setMorningOutput(res.getMorningOutput().add(TransferValueUtil.get(vo.getOutput())));
            }
            if(vo.getShifts().equals(DailyShiftEnum.NOON)){
                res.setNoonOutput(res.getNoonOutput().add(TransferValueUtil.get(vo.getOutput())));
            }
            if(vo.getShifts().equals(DailyShiftEnum.EVENING)){
                res.setEveningOutput(res.getEveningOutput().add(TransferValueUtil.get(vo.getOutput())));
            }
        }

        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 统计本月产煤
     */
    public JoyResult getThisMonth(UserEntity loginUser) {
        // 开始时间、截止时间
        Date now = new Date();
        Date start = DateUtil.getMonthFirstDay(now);
        Date end = DateUtil.getMonthLastDay(now);
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 掘进、回采月产煤
         */
        StatisticOutputVo driving = drivingDailyRepository.statisticThisMonthOutput(belongCompany, start, end);
        StatisticOutputVo mining = backMiningDailyRepository.statisticThisMonthOutput(belongCompany, start, end);

        /**
         * 返回结果
         */
        GetThisMonthOutputRes res = new GetThisMonthOutputRes();
        res.setDrivingOutput(TransferValueUtil.get(driving.getOutput()));
        res.setMiningOutput(TransferValueUtil.get(mining.getOutput()));
        // 汇总
        res.setTotalOutput(res.getDrivingOutput().add(res.getMiningOutput()));
        return JoyResult.buildSuccessResultWithData(res);
    }


    /**
     * 最近15日产煤趋势
     */
    public JoyResult getNear15Day(UserEntity loginUser) {
        // 开始时间、截止时间
        Date end = DateUtil.getDateJustYMD(new Date());
        Date start = DateUtil.addDay(end, -15);
        DepartmentEntity belongCompany = loginUser.getCompany();

        /**
         * 分别统计掘进、回采产煤
         */
        List<SkAndBvVo> driving = drivingDailyRepository.statisticEveryDayOutputByTimeZone(belongCompany, start, end);
        List<SkAndBvVo> mining = backMiningDailyRepository.statisticEveryDayOutputByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatNear15DayOutput(driving, mining,start));
    }


    /**
     * 月度产煤趋势
     */
    public JoyResult getEveryMonth(UserEntity loginUser) {
        // 开始时间、截止时间
        Date now = new Date();
        Date start = DateUtil.getDateYearStart(now);
        Date end = DateUtil.getDateYearEnd(now);
        DepartmentEntity belongCompany = loginUser.getCompany();

        /**
         * 分别统计掘进、回采产煤
         */
        List<IkAndBvVo> driving = drivingDailyRepository.statisticEveryMonthOutputByTimeZone(belongCompany, start, end);
        List<IkAndBvVo> mining = backMiningDailyRepository.statisticEveryMonthOutputByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatEveryMonthOutput(driving, mining));
    }

}
