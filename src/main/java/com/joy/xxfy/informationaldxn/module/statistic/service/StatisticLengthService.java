package com.joy.xxfy.informationaldxn.module.statistic.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.web.res.GetThisMonthLengthRes;
import com.joy.xxfy.informationaldxn.module.statistic.web.res.GetTodayLengthRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.TransferValueUtil;
import com.joy.xxfy.informationaldxn.publish.utils.format.AntVFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatisticLengthService {
    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    /**
     * 统计今日进尺：回采 + 掘进
     */
    public JoyResult getToday(UserEntity loginUser) {
        Date now = DateUtil.getDateJustYMD(new Date());
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 按班次统计
         */

        List<KeyAndValueVo<DailyShiftEnum, BigDecimal>> sd = drivingDailyRepository.statisticTodayLengthGroupByShifts(belongCompany, now);
        List<KeyAndValueVo<DailyShiftEnum, BigDecimal>> sm = backMiningDailyRepository.statisticTodayLengthGroupByShifts(belongCompany, now);

        /**
         * 返回结果
         */
        List<GetTodayLengthRes> res = new ArrayList<>();
        /**
         * 第一个数组元素代表今日累计掘进数据
         */
        GetTodayLengthRes driving = new GetTodayLengthRes();
        for (KeyAndValueVo<DailyShiftEnum, BigDecimal> vo : sd) {
            if(vo.getKey().equals(DailyShiftEnum.MORNING)){
                driving.setMorningLength(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.NOON)){
                driving.setNoonLength(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.EVENING)){
                driving.setEveningLength(TransferValueUtil.get(vo.getValue()));
            }
        }
        // 和即为今日的数据
        driving.setTodayLength(driving.getMorningLength().add(driving.getNoonLength()).add(driving.getEveningLength()));
        driving.setName("掘进");
        res.add(driving);

        /**
         * 第二个数组元素代表今日回采数据
         */
        GetTodayLengthRes mining = new GetTodayLengthRes();
        for (KeyAndValueVo<DailyShiftEnum, BigDecimal> vo : sm) {
            if(vo.getKey().equals(DailyShiftEnum.MORNING)){
                mining.setMorningLength(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.NOON)){
                mining.setNoonLength(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.EVENING)){
                mining.setEveningLength(TransferValueUtil.get(vo.getValue()));
            }
        }
        // 和即为今日的数据
        mining.setTodayLength(mining.getMorningLength().add(mining.getNoonLength()).add(mining.getEveningLength()));
        mining.setName("回采");
        res.add(mining);
        return JoyResult.buildSuccessResultWithData(res);
    }


    /**
     * 最近15日掘进回采趋势
     */
    public JoyResult getNear15Day(UserEntity loginUser) {
        // 开始时间、截止时间
        Date end = DateUtil.getDateJustYMD(new Date());
        Date start = DateUtil.addDay(end, -15);
        DepartmentEntity belongCompany = loginUser.getCompany();

        /**
         * 分别统计掘进、回采产煤
         */
        List<KeyAndValueVo<String, BigDecimal>> driving = drivingDailyRepository.statisticEveryDayLengthByTimeZone(belongCompany, start, end);
        List<KeyAndValueVo<String, BigDecimal>> mining = backMiningDailyRepository.statisticEveryDayLengthByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatNear15DayOutput(driving, mining,start));
    }


    /**
     * 本月掘进回采汇总
     */
    public JoyResult getThisMonth(UserEntity loginUser) {
        // 开始时间、截止时间
        Date now = new Date();
        Date start = DateUtil.getMonthFirstDay(now);
        Date end = DateUtil.getMonthLastDay(now);
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 掘进、回采
         */
        SingleValueVo<BigDecimal> driving = drivingDailyRepository.statisticThisMonthLength(belongCompany, start, end);
        SingleValueVo<BigDecimal> mining = backMiningDailyRepository.statisticThisMonthLength(belongCompany, start, end);

        /**
         * 返回结果
         */
        GetThisMonthLengthRes res = new GetThisMonthLengthRes();
        res.setDrivingLength(TransferValueUtil.get(driving.getValue()));
        res.setMiningLength(TransferValueUtil.get(mining.getValue()));
        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 月度汇总
     */
    public JoyResult getEveryMonth(UserEntity loginUser) {
        // 开始时间、截止时间
        Date now = new Date();
        Date start = DateUtil.getDateYearStart(now);
        Date end = DateUtil.getDateYearEnd(now);
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 分别统计月度掘进、回采进尺
         */
        List<KeyAndValueVo<Integer, BigDecimal>> driving = drivingDailyRepository.statisticEveryMonthLengthByTimeZone(belongCompany, start, end);
        List<KeyAndValueVo<Integer, BigDecimal>> mining = backMiningDailyRepository.statisticEveryMonthLengthByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatEveryMonthLength(driving, mining));
    }

}
