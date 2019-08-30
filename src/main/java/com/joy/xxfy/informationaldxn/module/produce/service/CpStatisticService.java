package com.joy.xxfy.informationaldxn.module.produce.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.entity.CmPlatformEntity;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.repository.CmPlatformRepository;
import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.entity.ProduceCmDailyEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.repository.ProduceCmDailyRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.*;
import com.joy.xxfy.informationaldxn.module.produce.web.res.CpStatisticRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.CompareUtil;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 煤矿生产日报服务
 */
@Transactional
@Service
public class CpStatisticService {

    @Autowired
    private ProduceCmDailyRepository produceCmDailyRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private DrivingDailyDetailRepository drivingDailyDetailRepository;


    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    @Autowired
    private BackMiningDailyDetailRepository backMiningDailyDetailRepository;

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    @Autowired
    private CmPlatformRepository cmPlatformRepository;

    /**
     * 获取煤矿生产日报服务的所有统计数据
     */
    public JoyResult getData(UserEntity loginUser, Date time) {
        LogUtil.info("Now user type is : {}", loginUser.getUserType());
        // 必须是集团账户
        if(!loginUser.getUserType().equals(UserTypeEnum.CP_ADMIN)){
            return JoyResult.buildFailedResult(Notice.PERMISSION_FORBIDDEN);
        }
        // 返回结果
        List<CpStatisticRes> result = new ArrayList<>();
        // 依次处理每一个煤矿的数据
        List<CmPlatformEntity> cms = cmPlatformRepository.findAll();
        for (CmPlatformEntity cm : cms) {
            // 返回结果的子元素
            CpStatisticRes res = new CpStatisticRes();

            // == 填充平台名字
            res.setCmPlatformName(cm.getCmName());

            // == 填充统计数据
            // 子元素的子元素：统计数据部分
            List<CpStatisticVo> statisticData = new ArrayList<>();
            // 获取关联的公司信息
            DepartmentEntity company = cm.getUser().getDepartment();
            // 获取单个平台的回采统计信息
            List<BackMiningStatisticVo> backMiningData = getBackMiningData(company, time);
            // 掘进统计信息
            List<DrivingStatisticVo> drivingData = getDrivingData(company, time);
            // 钻孔统计信息
            List<DrillStatisticVo> drillData = getDrillData(company, time);
            int max = CompareUtil.getMaxNumber(backMiningData.size(), drivingData.size(), drillData.size());
            // 填充统计数据, 注意获取到的数据的最后一条是统计
            for (int i = 0; i < max - 1; i++) {
                CpStatisticVo vo = new CpStatisticVo();
                // 组装回采
                if(i < backMiningData.size() - 1){
                    vo.setBackMiningInfo(backMiningData.get(i));
                }else{
                    vo.setBackMiningInfo(null);
                }
                // 组装掘进
                if(i < drivingData.size() - 1){
                    vo.setDrivingInfo(drivingData.get(i));
                }else{
                    vo.setDrivingInfo(null);
                }
                // 组装回采
                if(i < drillData.size() - 1){
                    vo.setDrillInfo(drillData.get(i));
                }else{
                    vo.setDrillInfo(null);
                }
                statisticData.add(vo);
            }
            // 数据部分的最后一条统计数据：合计，合计二字可以理解为工作名称
            CpStatisticVo last = new CpStatisticVo();
            last.setBackMiningInfo(backMiningData.get(backMiningData.size() - 1));
            last.setDrivingInfo(drivingData.get(drivingData.size() - 1));
            last.setDrillInfo(drillData.get(drillData.size() - 1));
            // 表明该条数据为合计数据
            last.setAmount(true);
            statisticData.add(last);
            // 填充数据
            res.setStatisticData(statisticData);

            // == 填充备注信息部分，在数据库中查询
            ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findAllByBelongCompanyAndDailyTime(company, time);
            if(produceCmDailyEntity != null){
                // 说明有备注信息，设置备注信息
                res.setRemarks(produceCmDailyEntity.getRemarks());
            }else{
                res.setRemarks(null);
            }
            // 加入到最后的返回结果之中，继续下一个平台的数据统计
            result.add(res);
        }
        // 最后计算所有煤矿的合计信息，也许前端不喜欢这样的结构体，不过暂时先这样返回。
        CpStatisticRes amount = new CpStatisticRes();
        amount.setCmPlatformName(ResultDataConstant.AMOUNT_OF_ALL_CM_PLATFORM);
        CpStatisticVo vo = new CpStatisticVo();
        result.forEach(s -> s.getStatisticData().forEach(d -> {
            // 若是统计数据，收走。
            if(d.isAmount()){
                /*回采工作面：早中班日月产煤量*/
                vo.setBackMiningMorningOutput(vo.getBackMiningMorningOutput().add(d.getBackMiningMorningOutput()));
                vo.setBackMiningMorningOutput(vo.getBackMiningNoonOutput().add(d.getBackMiningNoonOutput()));
                vo.setBackMiningEveningOutput(vo.getBackMiningEveningOutput().add(d.getBackMiningEveningOutput()));
                vo.setBackMiningDayOutput(vo.getBackMiningDayOutput().add(d.getBackMiningDayOutput()));
                vo.setBackMiningMonthOutput(vo.getBackMiningMonthOutput().add(d.getBackMiningMonthOutput()));

                /* 掘进工作面：早中班日月进尺 */
                vo.setDrivingMorningLength(vo.getDrivingMorningLength().add(d.getDrivingMorningLength()));
                vo.setDrivingNoonLength(vo.getDrivingNoonLength().add(d.getDrivingNoonLength()));
                vo.setDrivingEveningLength(vo.getDrivingEveningLength().add(d.getDrivingEveningLength()));
                vo.setDrivingDayLength(vo.getDrivingDayLength().add(d.getDrivingDayLength()));
                vo.setDrivingMonthLength(vo.getDrivingMonthLength().add(d.getDrivingMonthLength()));

                /* 钻孔工作：日月进尺 */
                vo.setDrillDayLength(vo.getDrillDayLength().add(d.getDrillDayLength()));
                vo.setDrillMonthLength(vo.getDrillMonthLength().add(d.getDrillMonthLength()));
            }
        }));
        amount.setStatisticData(Arrays.asList(vo));
        amount.setRemarks(null);
        result.add(amount);
        return JoyResult.buildSuccessResultWithData(result);
    }


    /**
     * 获取回采统计
     */
    public List<BackMiningStatisticVo> getBackMiningData(DepartmentEntity company, Date time) {
        List<BackMiningStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        BackMiningStatisticVo amount = new BackMiningStatisticVo();
        amount.setName(ResultDataConstant.AMOUNT);
        // 只统计拥有日报的工作面信息
        List<BackMiningFaceEntity> faces = backMiningFaceRepository.findAllByDailyTimeAndBelongCompany(time, company);
        for (BackMiningFaceEntity face : faces) {
            BackMiningStatisticVo vo = new BackMiningStatisticVo();
            // 获取该工作面对应的本日的日报信息
            BackMiningDailyEntity daily = backMiningDailyRepository.findAllByBackMiningFaceAndDailyTime(face, time);
            // 若日报有填写, 获取日报详情信息
            if(daily != null){
                List<BackMiningDailyDetailEntity> details = backMiningDailyDetailRepository.findAllByBackMiningDaily(daily);
                // 同一个班次会出现很多次(因为队伍不同)，和平台不同的是，这里统计的是煤产量，而不是进尺。
                for (BackMiningDailyDetailEntity detail : details) {
                    switch (detail.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningOutput(vo.getMorningOutput().add(detail.getOutput()));
                            break;
                        case NOON:
                            vo.setNoonOutput(vo.getNoonOutput().add(detail.getOutput()));
                            break;
                        case EVENING:
                            vo.setEveningOutput(vo.getEveningOutput().add(detail.getOutput()));
                            break;
                        default:break;
                    }
                    // 以日统计
                    vo.setDayOutput(vo.getDayOutput().add(detail.getOutput()));
                }
            }
            // 统计月信息
            CmStatisticVo temp = backMiningDailyRepository.statisticOutPut(face, DateUtil.getMonthFirstDay(time), DateUtil.getMonthLastDay(time));
             // 月累计产煤
            vo.setMonthOutput(temp.getMonthOutput() == null? BigDecimalValueConstant.ZERO : temp.getMonthOutput());
            // 工作面名称
            vo.setName(face.getBackMiningFaceName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningOutput(amount.getMorningOutput().add(vo.getMorningOutput()));
            amount.setNoonOutput(amount.getNoonOutput().add(vo.getNoonOutput()));
            amount.setEveningOutput(amount.getEveningOutput().add(vo.getEveningOutput()));
            amount.setDayOutput(amount.getDayOutput().add(vo.getDayOutput()));
            amount.setMonthOutput(amount.getMonthOutput().add(vo.getMonthOutput()));
        }
        // 最后一条添加为合计
        result.add(amount);
        return result;
    }



    /**
     * 获取掘进面统计数据
     */
    public List<DrivingStatisticVo> getDrivingData(DepartmentEntity company, Date time) {
        List<DrivingStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        DrivingStatisticVo amount = new DrivingStatisticVo();
        amount.setName(ResultDataConstant.AMOUNT);
        // 只统计拥有日报的工作面信息
        List<DrivingFaceEntity> faces = drivingFaceRepository.findAllByDailyTimeAndBelongCompany(time, company);
        for (DrivingFaceEntity face : faces) {
            DrivingStatisticVo vo = new DrivingStatisticVo();
            // 获取该工作面对应的本日的日报信息
            DrivingDailyEntity daily = drivingDailyRepository.findAllByDrivingFaceAndDailyTime(face, time);
            // 若日报有填写, 获取日报详情信息
            if(daily != null){
                List<DrivingDailyDetailEntity> details = drivingDailyDetailRepository.findAllByDrivingDaily(daily);
                // 不同队伍的统计
                for (DrivingDailyDetailEntity detail : details) {
                    switch (detail.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningLength(vo.getMorningLength().add(detail.getDoneLength()));
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(detail.getDoneLength()));
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(detail.getDoneLength()));
                            break;
                        default:break;
                    }
                    // 日统计
                    vo.setDayLength(vo.getDayLength().add(detail.getDoneLength()));
                }
            }
            // 统计月信息
            DrivingStatisticVo monthData = drivingDailyRepository.statisticDoneLength(face, DateUtil.getMonthFirstDay(time), DateUtil.getMonthLastDay(time));
            vo.setMonthLength(monthData.getMonthLength() == null? BigDecimalValueConstant.ZERO :monthData.getMonthLength());
            // 工作面名称
            vo.setName(face.getDrivingFaceName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningLength(amount.getMorningLength().add(vo.getMorningLength()));
            amount.setNoonLength(amount.getNoonLength().add(vo.getNoonLength()));
            amount.setEveningLength(amount.getEveningLength().add(vo.getEveningLength()));
            amount.setDayLength(amount.getDayLength().add(vo.getDayLength()));
            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
        }
        // 合计项
        result.add(amount);
        return result;
    }



    /**
     * 获取打钻统计，打钻统计不同于上面二者，班次是放在总日报信息里面的，而且已经进行了统计，因此，无需查询详细的打孔信息即可
     * 完成此处的需求。钻孔也只有打钻进尺的数据
     * 集团只需进行日统计、月统计。
     */
    public List<DrillStatisticVo> getDrillData(DepartmentEntity company, Date time) {
        List<DrillStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        DrillStatisticVo amount = new DrillStatisticVo();
        amount.setName(ResultDataConstant.AMOUNT);
        // 只获取有日报的钻孔工作列表
        List<DrillWorkEntity> works = drillWorkRepository.findAllByDistinctBelongCompanyAndDailyTime(company,time);
        for (DrillWorkEntity work : works) {
            DrillStatisticVo vo = new DrillStatisticVo();
            // 获取该工作面对应的本日的日报信息
            List<DrillDailyEntity> dailies = drillDailyRepository.findAllByDrillWorkAndDailyTime(work, time);
            for (DrillDailyEntity daily : dailies) {
                vo.setDayLength(vo.getDayLength().add(daily.getTotalDoneLength()));
            }
            // == 统计月信息
            DrillStatisticVo temp = drillDailyRepository.statisticDoneLength(work, DateUtil.getMonthFirstDay(time), DateUtil.getMonthLastDay(time));
            // 月累计进尺
            vo.setMonthLength(temp.getMonthLength() == null? BigDecimalValueConstant.ZERO :temp.getMonthLength());
            // 工作名称
            vo.setName(work.getDrillWorkName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setDayLength(amount.getDayLength().add(vo.getDayLength()));
            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
        }
        result.add(amount);
        return result;
    }
}
