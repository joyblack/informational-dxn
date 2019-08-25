package com.joy.xxfy.informationaldxn.module.task.timing;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import com.joy.xxfy.informationaldxn.module.safe.domain.repository.SafeInspectionRepository;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 定时任务：处理安全巡检相关定时任务
 */
@Component
@Transactional
public class SafeInspectionTask {

    @Autowired
    private SafeInspectionRepository safeInspectionRepository;

    // 每一分钟说一下话
    @Scheduled(cron = "* 1 * * * *")
    public void test(){
        System.out.println("这是我每1分钟跳动一次的心脏，听，心跳声......");
    }

    // 每天凌晨零点：更新所有超时了的未整改的记录的超时状态为超时

//    @Scheduled(cron = "0/10 * * * * *")
    @Scheduled(cron = "0 0 0 * * *")
    public void updateIsOvertTime(){
        LogUtil.info("[Timing task]：update all not rectification record, if overtime, set it overtime is Yes...");
        safeInspectionRepository.updateIsOvertTimeByNowAndRectificationStatus(
                CommonYesEnum.YES,
                new Date(),
                RectificationStatusEnum.NO
        );

    }

}
