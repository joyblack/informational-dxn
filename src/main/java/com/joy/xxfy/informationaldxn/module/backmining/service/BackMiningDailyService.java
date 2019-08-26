package com.joy.xxfy.informationaldxn.module.backmining.service;

import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.vo.SumBackMiningDailyDetailVo;
import com.joy.xxfy.informationaldxn.module.backmining.web.req.BackMiningDailyAddReq;
import com.joy.xxfy.informationaldxn.module.backmining.web.res.BackMiningDailyRes;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.LongValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BackMiningDailyService {

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

    @Autowired
    private BackMiningDailyDetailRepository backMiningDailyDetailRepository;

    /**
     * 添加
     */
    public JoyResult add(BackMiningDailyAddReq req) {
        // 验证工作面信息是否存在
        BackMiningFaceEntity backMiningFace = backMiningFaceRepository.findAllById(req.getBackMiningFaceId());
        if(backMiningFace == null){
            return JoyResult.buildFailedResult(Notice.BACK_MINING_NOT_EXIST);
        }
        // 验证该日期的日报是否已经填写(不会有多个煤矿平台操作的，因此这个变量无需考虑)
        BackMiningDailyEntity backMiningDaily = backMiningDailyRepository.findAllByBackMiningFaceAndDailyTime(backMiningFace, req.getDailyTime());
        if(backMiningDaily != null){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_REPEAT);
        }
        // 装配实体：日报信息
        BackMiningDailyEntity dailyEntity = new BackMiningDailyEntity();
        // 日期
        dailyEntity.setDailyTime(req.getDailyTime());
        // 工作面信息
        dailyEntity.setBackMiningFace(backMiningFace);
        dailyEntity.setTotalDoneLength(BigDecimalValueConstant.ZERO);
        dailyEntity.setTotalOutput(BigDecimalValueConstant.ZERO);
        dailyEntity.setTotalPeopleNumber(LongValueConstant.ZERO);
        // 添加信息
        return JoyResult.buildSuccessResultWithData(backMiningDailyRepository.save(dailyEntity));
    }


    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        // 获取日报信息
        BackMiningDailyEntity info = backMiningDailyRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 计算日报详细信息得到的总长度，虽然也可以用日报直接获得，但是在计算一次又何妨呢？
        SumBackMiningDailyDetailVo vo = backMiningDailyDetailRepository.aggDailyDetail(info);
        // 刷新工作面回采进尺信息
        BackMiningFaceEntity backMiningFace = info.getBackMiningFace();
        if(vo.getTotalDoneLengthSum() != null){
            backMiningFace.setDoneLength(backMiningFace.getDoneLength().subtract(vo.getTotalDoneLengthSum()));
        }
        // 删除该日报的详情信息
        backMiningDailyDetailRepository.updateIsDeleteByBackMiningDaily(info, true);
        // 删除日报信息
        info.setIsDelete(true);
        backMiningDailyRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        BackMiningDailyRes res = new BackMiningDailyRes();
        BackMiningDailyEntity info = backMiningDailyRepository.findAllById(id);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 设置详情信息
            res.setBackMiningDailyDetail(backMiningDailyDetailRepository.findAllByBackMiningDaily(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }


}
