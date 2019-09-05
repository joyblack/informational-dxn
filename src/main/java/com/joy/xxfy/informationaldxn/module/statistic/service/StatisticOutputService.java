package com.joy.xxfy.informationaldxn.module.statistic.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.web.res.GetThisMonthOutputRes;
import com.joy.xxfy.informationaldxn.module.statistic.web.res.GetTodayOutputRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.TransferValueUtil;
import com.joy.xxfy.informationaldxn.publish.utils.format.AntVFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StatisticOutputService {
    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;


    /**
     * 统计今日产煤：回采 + 掘进
     */
    public JoyResult getToday(UserEntity loginUser) {
        Date now = DateUtil.getDateJustYMD(new Date());
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 获取今日统计
         */
        SingleValueVo<BigDecimal> v1 = drivingDailyRepository.statisticTodayOutput(belongCompany, now);
        SingleValueVo<BigDecimal> v11 = backMiningDailyRepository.statisticTodayOutput(belongCompany, now);

        /**
         * 班次统计
         */
        List<KeyAndValueVo<DailyShiftEnum, BigDecimal>> v2 = drivingDailyRepository.statisticTodayOutputGroupByShifts(belongCompany, now);
        List<KeyAndValueVo<DailyShiftEnum, BigDecimal>> v22 = backMiningDailyRepository.statisticTodayOutputGroupByShifts(belongCompany, now);

        /**
         * 返回结果
         */
        GetTodayOutputRes res = new GetTodayOutputRes();
        // 今日产煤
        res.setTodayOutput(TransferValueUtil.get(v1.getValue()).add(TransferValueUtil.get(v11.getValue())));
        /**
         * 早中晚班
         */
        for (KeyAndValueVo<DailyShiftEnum, BigDecimal> vo : v2) {
            if(vo.getKey().equals(DailyShiftEnum.MORNING)){
                res.setMorningOutput(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.NOON)){
                res.setNoonOutput(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.EVENING)){
                res.setEveningOutput(TransferValueUtil.get(vo.getValue()));
            }
        }
        for (KeyAndValueVo<DailyShiftEnum, BigDecimal> vo : v22) {
            if(vo.getKey().equals(DailyShiftEnum.MORNING)){
                res.setMorningOutput(res.getMorningOutput().add(TransferValueUtil.get(vo.getValue())));
            }
            if(vo.getKey().equals(DailyShiftEnum.NOON)){
                res.setNoonOutput(res.getNoonOutput().add(TransferValueUtil.get(vo.getValue())));
            }
            if(vo.getKey().equals(DailyShiftEnum.EVENING)){
                res.setEveningOutput(res.getEveningOutput().add(TransferValueUtil.get(vo.getValue())));
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
        SingleValueVo<BigDecimal> driving = drivingDailyRepository.statisticThisMonthOutput(belongCompany, start, end);
        SingleValueVo<BigDecimal> mining = backMiningDailyRepository.statisticThisMonthOutput(belongCompany, start, end);

        /**
         * 返回结果
         */
        GetThisMonthOutputRes res = new GetThisMonthOutputRes();
        res.setDrivingOutput(TransferValueUtil.get(driving.getValue()));
        res.setMiningOutput(TransferValueUtil.get(mining.getValue()));
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
        List<KeyAndValueVo<String, BigDecimal>> driving = drivingDailyRepository.statisticEveryDayOutputByTimeZone(belongCompany, start, end);
        List<KeyAndValueVo<String, BigDecimal>> mining = backMiningDailyRepository.statisticEveryDayOutputByTimeZone(belongCompany, start, end);
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
        List<KeyAndValueVo<Integer, BigDecimal>> driving = drivingDailyRepository.statisticEveryMonthOutputByTimeZone(belongCompany, start, end);
        List<KeyAndValueVo<Integer, BigDecimal>> mining = backMiningDailyRepository.statisticEveryMonthOutputByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatEveryMonthOutput(driving, mining));
    }

}
