package com.joy.xxfy.informationaldxn.module.task.timing.document;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.ReturnStatusEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.repository.BorrowRepository;
import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 借阅的资料
 */
@Component
@Transactional
public class BorrowTask {
    @Autowired
    private BorrowRepository borrowRepository;

    /**
     * 每天凌晨00:30，将未归还并且超时的借阅信息设置为超时状态。
     */
    @Scheduled(cron = "0 30 0 * * *")
    public void updateIsOvertTime(){
        LogUtil.info("[Timing task]：update all document borrow record, if overtime, set it overtime is Yes...");
        borrowRepository.updateIsOvertTimeByNowAndReturnStatus(
                CommonYesEnum.YES, // 设置为超时
                new Date(),// 超过当前时间
                ReturnStatusEnum.RETURN_STATUS_NO// 只处理未归还的记录
        );
    }
}
