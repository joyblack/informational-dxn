package com.joy.xxfy.informationaldxn.module.produce.service;

import com.joy.xxfy.informationaldxn.module.department.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 获取煤矿生产日报服务的所有统计数据
     */
    public JoyResult getData(DepartmentEntity company, Date time) {
        /**
         * 获取工作面
         */
        return null;
    }

    /**
     * 很笨的获取方式
     */
    public JoyResult getDataFool(DepartmentEntity company, Date time) {
        LogUtil.info("Start statistic company: {}", company.getDepartmentName());
        // 掘进面信息统计
        // 直接取出当日的掘进工作面的所有信息即可
        List<DrivingDailyEntity> drivingDailyEntities = drivingDailyRepository.findAllByBeLongCompanyAndDailyTime(company, new Date());
        // 统



        /**
         * 获取工作面
         */
        return null;
    }





}
