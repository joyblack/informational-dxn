package com.joy.xxfy.informationaldxn.module.produce.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
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
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrillStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.web.req.SetRemarkReq;
import com.joy.xxfy.informationaldxn.module.produce.web.res.CmStatisticRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 煤矿生产日报服务
 */
@Transactional
@Service
public class CmStatisticService {

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


    /**
     * 获取煤矿生产日报服务的所有统计数据
     */
    public JoyResult getData(DepartmentEntity company, Date time) {
        LogUtil.info("Start statistic time: {}", time);
        LogUtil.info("Start statistic company: {}", company.getDepartmentName());
        // 返回结果
        CmStatisticRes res = new CmStatisticRes();
        // copy数据
        ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findAllByBelongCompanyAndDailyTime(company, time);
        if(produceCmDailyEntity != null){
            JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(produceCmDailyEntity,res);
        }
        // 三维统计信息：后期这些信息可能需要存储到数据库
        res.setDrivingStatistic(getDrivingData(company,time));
        res.setBackMiningStatistic(getBackMiningData(company,time));
        res.setDrillStatistic(getDrillData(company,time));
        return JoyResult.buildSuccessResultWithData(res);
    }

    /**
     * 获取掘进面统计数据
     */
    public List<CmStatisticVo> getDrivingData(DepartmentEntity company, Date time) {
        List<CmStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        CmStatisticVo amount = new CmStatisticVo();
        amount.setWorkName(ResultDataConstant.AMOUNT);

        // 只统计拥有日报的工作面信息
        List<DrivingFaceEntity> faces = drivingFaceRepository.findAllByDailyTimeAndBelongCompany(time, company);

        System.out.println(faces.size());
        System.out.println(faces);

        for (DrivingFaceEntity face : faces) {
            CmStatisticVo vo = new CmStatisticVo();
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
                            // 人数
                            vo.setMorningPeople(vo.getMorningPeople() + detail.getPeopleNumber());
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(detail.getDoneLength()));
                            vo.setNoonPeople(vo.getNoonPeople() + detail.getPeopleNumber());
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(detail.getDoneLength()));
                            vo.setEveningPeople(vo.getEveningPeople() + detail.getPeopleNumber());
                            break;
                        default:break;
                    }
                    // 产煤: 以日统计
                    vo.setDayOutput(vo.getDayOutput().add(detail.getOutput()));
                }
                // 圆班
                vo.setShiftTotalLength(vo.getMorningLength().add(vo.getNoonLength()).add(vo.getEveningLength()));
                vo.setShiftTotalPeople(vo.getMorningPeople() + vo.getNoonPeople() + vo.getEveningPeople());
            }
            // 统计月信息
            CmStatisticVo cmStatisticVo = drivingDailyRepository.statisticDoneLengthAndOutPut(face, DateUtil.getMonthFirstDay(time), DateUtil.getMonthLastDay(time));
            // 月累计进尺
            vo.setMonthLength(cmStatisticVo.getMonthLength() == null? BigDecimalValueConstant.ZERO :cmStatisticVo.getMonthLength());
            // 月累计产煤
            vo.setMonthOutput(cmStatisticVo.getMonthOutput() == null? BigDecimalValueConstant.ZERO : cmStatisticVo.getMonthOutput());
            // 工作面名称
            vo.setWorkName(face.getDrivingFaceName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningLength(amount.getMorningLength().add(vo.getMorningLength()));
            amount.setMorningPeople(amount.getMorningPeople() + vo.getMorningPeople());

            amount.setNoonLength(amount.getNoonLength().add(vo.getNoonLength()));
            amount.setNoonPeople(amount.getNoonPeople() + vo.getNoonPeople());

            amount.setEveningLength(amount.getEveningLength().add(vo.getEveningLength()));
            amount.setEveningPeople(amount.getEveningPeople() + vo.getEveningPeople());

            amount.setShiftTotalLength(amount.getMorningLength().add(amount.getNoonLength()).add(amount.getEveningLength()));
            amount.setShiftTotalPeople(amount.getMorningPeople() + amount.getNoonPeople() + amount.getEveningPeople());

            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
            amount.setMonthOutput(amount.getMonthOutput().add(vo.getMonthOutput()));
        }
        // 合计项
        result.add(amount);
        return result;
    }

    /**
     * 获取回采统计
     */
    public List<CmStatisticVo> getBackMiningData(DepartmentEntity company, Date time) {
        List<CmStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        CmStatisticVo amount = new CmStatisticVo();
        amount.setWorkName(ResultDataConstant.AMOUNT);
        // 只统计拥有日报的工作面信息
        List<BackMiningFaceEntity> faces = backMiningFaceRepository.findAllByDailyTimeAndBelongCompany(time, company);

        for (BackMiningFaceEntity face : faces) {
            CmStatisticVo vo = new CmStatisticVo();
            // 获取该工作面对应的本日的日报信息
            BackMiningDailyEntity daily = backMiningDailyRepository.findAllByBackMiningFaceAndDailyTime(face, time);
            // 若日报有填写, 获取日报详情信息
            if(daily != null){
                List<BackMiningDailyDetailEntity> details = backMiningDailyDetailRepository.findAllByBackMiningDaily(daily);
                // 同一个班次会出现很多次(因为队伍不同)
                for (BackMiningDailyDetailEntity detail : details) {
                    switch (detail.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningLength(vo.getMorningLength().add(detail.getDoneLength()));
                            // 人数
                            vo.setMorningPeople(vo.getMorningPeople() + detail.getPeopleNumber());
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(detail.getDoneLength()));
                            vo.setNoonPeople(vo.getNoonPeople() + detail.getPeopleNumber());
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(detail.getDoneLength()));
                            vo.setEveningPeople(vo.getEveningPeople() + detail.getPeopleNumber());
                            break;
                        default:break;
                    }
                    // 产煤: 以日统计
                    vo.setDayOutput(vo.getDayOutput().add(detail.getOutput()));
                }
                // 圆班
                vo.setShiftTotalLength(vo.getMorningLength().add(vo.getNoonLength()).add(vo.getEveningLength()));
                vo.setShiftTotalPeople(vo.getMorningPeople() + vo.getNoonPeople() + vo.getEveningPeople());
            }
            // 统计月信息
            CmStatisticVo cmStatisticVo = backMiningDailyRepository.statisticDoneLengthAndOutPut(face,  DateUtil.getMonthFirstDay(time), DateUtil.getMonthLastDay(time));
            // 月累计进尺
            vo.setMonthLength(cmStatisticVo.getMonthLength() == null? BigDecimalValueConstant.ZERO :cmStatisticVo.getMonthLength());
            // 月累计产煤
            vo.setMonthOutput(cmStatisticVo.getMonthOutput() == null? BigDecimalValueConstant.ZERO : cmStatisticVo.getMonthOutput());
            // 工作面名称
            vo.setWorkName(face.getBackMiningFaceName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningLength(amount.getMorningLength().add(vo.getMorningLength()));
            amount.setMorningPeople(amount.getMorningPeople() + vo.getMorningPeople());

            amount.setNoonLength(amount.getNoonLength().add(vo.getNoonLength()));
            amount.setNoonPeople(amount.getNoonPeople() + vo.getNoonPeople());

            amount.setEveningLength(amount.getEveningLength().add(vo.getEveningLength()));
            amount.setEveningPeople(amount.getEveningPeople() + vo.getEveningPeople());

            amount.setShiftTotalLength(amount.getMorningLength().add(amount.getNoonLength()).add(amount.getEveningLength()));
            amount.setShiftTotalPeople(amount.getMorningPeople() + amount.getNoonPeople() + amount.getEveningPeople());

            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
            amount.setMonthOutput(amount.getMonthOutput().add(vo.getMonthOutput()));
        }
        result.add(amount);
        return result;
    }


    /**
     * 获取打钻统计，打钻统计不同于上面二者，班次是放在总日报信息里面的，而且已经进行了统计，因此，无需查询详细的打孔信息即可
     * 完成此处的需求。
     * 钻孔也只有打钻进尺的数据
     */
    public List<CmStatisticVo> getDrillData(DepartmentEntity company, Date time) {
        List<CmStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        CmStatisticVo amount = new CmStatisticVo();
        amount.setWorkName(ResultDataConstant.AMOUNT);
        // 只获取有日报的钻孔工作列表
        List<DrillWorkEntity> works = drillWorkRepository.findAllByDistinctBelongCompanyAndDailyTime(company,time);
        for (DrillWorkEntity work : works) {
            CmStatisticVo vo = new CmStatisticVo();
            // 获取该工作面对应的本日的日报信息
            List<DrillDailyEntity> dailies = drillDailyRepository.findAllByDrillWorkAndDailyTime(work, time);
            for (DrillDailyEntity daily : dailies) {
                switch (daily.getShifts()) {
                    case MORNING:
                        // 进尺
                        vo.setMorningLength(vo.getMorningLength().add(daily.getTotalDoneLength()));
                        break;
                    case NOON:
                        vo.setNoonLength(vo.getNoonLength().add(daily.getTotalDoneLength()));
                        break;
                    case EVENING:
                        vo.setEveningLength(vo.getEveningLength().add(daily.getTotalDoneLength()));
                        break;
                    default:break;
                }
            }
            // == 统计月信息
            DrillStatisticVo cmStatisticVo = drillDailyRepository.statisticDoneLength(work,  DateUtil.getMonthFirstDay(time), DateUtil.getMonthLastDay(time));
            // 月累计进尺
            vo.setMonthLength(cmStatisticVo.getMonthLength() == null? BigDecimalValueConstant.ZERO :cmStatisticVo.getMonthLength());
            // 工作名称
            vo.setWorkName(work.getDrillWorkName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningLength(amount.getMorningLength().add(vo.getMorningLength()));

            amount.setNoonLength(amount.getNoonLength().add(vo.getNoonLength()));

            amount.setEveningLength(amount.getEveningLength().add(vo.getEveningLength()));

            amount.setShiftTotalLength(amount.getMorningLength().add(amount.getNoonLength()).add(amount.getEveningLength()));

            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
        }
        result.add(amount);
        return result;
    }


    public JoyResult setRemarks(SetRemarkReq req, UserEntity loginUser) {
        // 查询是否具有这条数据
        ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findAllByBelongCompanyAndDailyTime(loginUser.getCompany(), req.getTime());
        if(produceCmDailyEntity == null){
            produceCmDailyEntity = new ProduceCmDailyEntity();
        }
        produceCmDailyEntity.setBelongCompany(loginUser.getCompany());
        produceCmDailyEntity.setDailyTime(req.getTime());
        produceCmDailyEntity.setRemarks(req.getRemarks());
        return JoyResult.buildSuccessResultWithData(produceCmDailyRepository.save(produceCmDailyEntity));
    }
}
