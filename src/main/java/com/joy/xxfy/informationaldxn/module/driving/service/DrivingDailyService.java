package com.joy.xxfy.informationaldxn.module.driving.service;

import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.vo.SumDrivingDailyDetailVo;
import com.joy.xxfy.informationaldxn.module.driving.web.req.DrivingDailyAddReq;
import com.joy.xxfy.informationaldxn.module.driving.web.res.DrivingDailyRes;
import com.joy.xxfy.informationaldxn.module.user.domain.entity.UserEntity;
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
public class DrivingDailyService {

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private DrivingDailyDetailRepository drivingDailyDetailRepository;

    /**
     * 添加
     */
    public JoyResult add(DrivingDailyAddReq req, UserEntity loginUser) {
        // 验证工作面信息是否存在
        DrivingFaceEntity drivingFace = drivingFaceRepository.findAllById(req.getDrivingFaceId());
        if(drivingFace == null){
            return JoyResult.buildFailedResult(Notice.DRIVING_FACE_NOT_EXIST);
        }
        // 验证该日期的掘进日报是否已经填写(不会有多个煤矿平台操作的，因此这个变量无需考虑)
        DrivingDailyEntity drivingDaily = drivingDailyRepository.findAllByDrivingFaceAndDailyTime(drivingFace, req.getDailyTime());
        if(drivingDaily != null){
            return JoyResult.buildFailedResult(Notice.DAILY_ALREADY_EXIST, ResultDataConstant.MESSAGE_DAILY_REPEAT);
        }
        // 装配实体：日报信息
        DrivingDailyEntity dailyEntity = new DrivingDailyEntity();
        // 日期
        dailyEntity.setDailyTime(req.getDailyTime());
        // 工作面信息
        dailyEntity.setDrivingFace(drivingFace);
        dailyEntity.setTotalDoneLength(BigDecimalValueConstant.ZERO);
        dailyEntity.setTotalOutput(BigDecimalValueConstant.ZERO);
        dailyEntity.setTotalPeopleNumber(LongValueConstant.ZERO);
        // 添加信息
        return JoyResult.buildSuccessResultWithData(drivingDailyRepository.save(dailyEntity));
    }


    /**
     * 删除
     */
    public JoyResult delete(Long id, UserEntity loginUser) {
        // 获取日报信息
        DrivingDailyEntity info = drivingDailyRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 计算日报详细信息得到的总长度
        SumDrivingDailyDetailVo vo = drivingDailyDetailRepository.aggDailyDetail(info);
        // 刷新工作面已掘长度信息
        DrivingFaceEntity drivingFace = info.getDrivingFace();
        if(vo.getTotalDoneLengthSum() != null){
            drivingFace.setDoneLength(drivingFace.getDoneLength().subtract(vo.getTotalDoneLengthSum()));
        }
        drivingFace.setLeftLength(drivingFace.getTotalLength().subtract(drivingFace.getDoneLength()));
        // 删除该日报的详情信息
        drivingDailyDetailRepository.updateIsDeleteByDrivingDaily(info, true);
        // 删除日报信息
        info.setIsDelete(true);
        drivingDailyRepository.save(info);

        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id, UserEntity loginUser) {
        DrivingDailyRes res = new DrivingDailyRes();
        DrivingDailyEntity info = drivingDailyRepository.findAllById(id);
        if(info != null){
            JoyBeanUtil.copyProperties(info, res);
            // 设置详情信息
            res.setDrivingDailyDetail(drivingDailyDetailRepository.findAllByDrivingDaily(info));
        }else{
            res = null;
        }
        return JoyResult.buildSuccessResultWithData(res);
    }


}
