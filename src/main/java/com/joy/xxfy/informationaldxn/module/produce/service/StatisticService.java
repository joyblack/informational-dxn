package com.joy.xxfy.informationaldxn.module.produce.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.repository.CmPlatformRepository;
import com.joy.xxfy.informationaldxn.module.common.enums.DailyShiftEnum;
import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.repository.ProduceCmDailyRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.StatisticOutputVo;
import com.joy.xxfy.informationaldxn.module.produce.web.res.GetTodayOutputRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.TransferValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 集团调度日报服务
 */
@Transactional
@Service
public class StatisticService extends BaseService {

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
    public JoyResult getTodayOutput(UserEntity loginUser) {
        // 当前时间
        // 格式化时间
        //Date now = DateUtil.getDateJustYMD(new Date());
        Date now = DateUtil.getDate(2019,8,24);
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
}
