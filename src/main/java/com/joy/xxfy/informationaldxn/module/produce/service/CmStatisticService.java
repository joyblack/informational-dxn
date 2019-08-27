package com.joy.xxfy.informationaldxn.module.produce.service;

import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
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
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private DrivingDailyDetailRepository drivingDailyDetailRepository;

    /**
     * 获取煤矿生产日报服务的所有统计数据
     */
    public JoyResult getData(DepartmentEntity company, Date time) {
        LogUtil.info("Start statistic time: {}", time);
        LogUtil.info("Start statistic company: {}", company.getDepartmentName());
        // 掘进面信息统计
        // 直接取出当日的掘进工作面的所有信息即可
        List<CmStatisticVo> result = new ArrayList<>();
        // 获取属于该平台的工作面列表
        List<DrivingFaceEntity> faces = drivingFaceRepository.findAllByBelongCompany(company);
        for (DrivingFaceEntity face : faces) {
            CmStatisticVo vo = new CmStatisticVo();
            // 获取该工作面对应的本日的日报信息
            DrivingDailyEntity daily = drivingDailyRepository.findAllByDrivingFaceAndDailyTime(face, time);
            // 若日报有填写, 获取日报详情信息
            if(daily != null){
                List<DrivingDailyDetailEntity> details = drivingDailyDetailRepository.findAllByDrivingDaily(daily);
                // 同一个班次会出现很多次(因为队伍不同)
                for (DrivingDailyDetailEntity detail : details) {
                    switch (detail.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningLength(vo.getMorningLength().add(detail.getDoneLength()));
                            // 人数
                            vo.setNoonPeople(vo.getNoonPeople() + detail.getPeopleNumber());
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(detail.getDoneLength()));
                            vo.setMorningPeople(vo.getMorningPeople() + detail.getPeopleNumber());
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(detail.getDoneLength()));
                            vo.setEveningPeople(vo.getEveningPeople() + detail.getPeopleNumber());
                            break;
                        default:break;
                    }
                    // 产煤: 以日统计
                    vo.setTodayOutput(vo.getTodayOutput().add(detail.getOutput()));
                }
                // 圆班
                vo.setShiftTotalLength(vo.getMorningLength().add(vo.getNoonLength()).add(vo.getEveningLength()));
                vo.setShiftTotalPeople(vo.getMorningPeople() + vo.getNoonPeople() + vo.getEveningPeople());
                // 月累计进尺
               // vo.setMonthLength(drivingDailyRepository);
                // 月累计产煤
                //vo.setMonthOutput();
            }

            // 添加到返回结果数组
        }
        /**
         * 获取工作面
         */
        return JoyResult.buildSuccessResultWithData(result);
    }







}
