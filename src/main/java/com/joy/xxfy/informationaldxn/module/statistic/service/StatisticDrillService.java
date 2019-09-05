package com.joy.xxfy.informationaldxn.module.statistic.service;

import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.web.res.NameValueRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.KeyAndValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.domain.vo.SingleValueVo;
import com.joy.xxfy.informationaldxn.module.statistic.web.res.GetTodayLengthRes;
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
public class StatisticDrillService {
    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    /**
     * 今日累计打钻
     */
    public JoyResult getToday(UserEntity loginUser) {
        Date now = DateUtil.getDateJustYMD(new Date());
        DepartmentEntity belongCompany = loginUser.getCompany();
        /**
         * 按班次统计
         */
        List<KeyAndValueVo<DailyShiftEnum, BigDecimal>> vos = drillDailyRepository.statisticLengthGroupByShifts(belongCompany, now);

        /**
         * 返回结果
         */
        GetTodayLengthRes res = new GetTodayLengthRes();

        for (KeyAndValueVo<DailyShiftEnum, BigDecimal> vo : vos) {
            if(vo.getKey().equals(DailyShiftEnum.MORNING)){
                res.setMorningLength(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.NOON)){
                res.setNoonLength(TransferValueUtil.get(vo.getValue()));
            }
            if(vo.getKey().equals(DailyShiftEnum.EVENING)){
                res.setEveningLength(TransferValueUtil.get(vo.getValue()));
            }
        }
        // 和即为今日的数据
        res.setTodayLength(res.getMorningLength().add(res.getNoonLength()).add(res.getEveningLength()));
        res.setName("打钻");
        return JoyResult.buildSuccessResultWithData(res);
    }


    /**
     * 最近15日打钻
     */
    public JoyResult getNear15Day(UserEntity loginUser) {
        // 开始时间、截止时间
        Date end = DateUtil.getDateJustYMD(new Date());
        Date start = DateUtil.addDay(end, -15);
        DepartmentEntity belongCompany = loginUser.getCompany();
        List<KeyAndValueVo<String,BigDecimal>> drill = drillDailyRepository.statisticEveryDayLengthByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatNear15DayLength(drill,start));
    }


    /**
     * 本月打钻汇总
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
        SingleValueVo<BigDecimal> drill = drillDailyRepository.statisticThisMonthLength(belongCompany, start, end);
        /**
         * 返回结果
         */
        NameValueRes<BigDecimal> res = new NameValueRes<>();
        res.setName("本月累计打钻");
        res.setValue(TransferValueUtil.get(drill.getValue()));
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
        List<KeyAndValueVo<Integer,BigDecimal>> drill = drillDailyRepository.statisticEveryMonthLengthByTimeZone(belongCompany, start, end);
        return JoyResult.buildSuccessResultWithData(AntVFormatUtil.formatDrillEveryMonth(drill));
    }
}
