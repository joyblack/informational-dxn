package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillDailyDetailAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillDailyDetailUpdateReq;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.LongValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.*;

@Transactional
@Service
public class DrillDailyDetailService {

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    @Autowired
    private DrillHoleRepository drillHoleRepository;

    @Autowired
    private DrillDailyDetailRepository drillDailyDetailRepository;

    /**
     * 添加打钻详情
     */
    public JoyResult add(DrillDailyDetailAddReq req) {
        // 验证钻孔是否存在
        DrillHoleEntity drillHole = drillHoleRepository.findAllById(req.getDrillHoleId());
        if(drillHole == null){
            return JoyResult.buildFailedResult(Notice.DRILL_HOLE_NOT_EXIST);
        }
        // 验证所属日报信息是否存在
        DrillDailyEntity drillDaily = drillDailyRepository.findAllById(req.getDrillDailyId());
        if(drillDaily == null){
            return JoyResult.buildFailedResult(Notice.DAILY_NOT_EXIST);
        }
        // 验证是否是重复的记录：（一个日报仅允许添加一条钻孔信息，这样的信息只允许存在一条）
        // 日报固定的情况下：班次、日期、队伍都固定了。
        DrillDailyDetailEntity exist = drillDailyDetailRepository.findAllByDrillDailyAndDrillHole(drillDaily, drillHole);
        if(exist != null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_ALREADY_EXIST);
        }
        // 验证打孔长度是否超标，获取钻孔的剩余长度
        // 若提交的长度需要小0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 若当前提交的长度大于钻孔的剩余长度，不合法
        if(more(req.getDoneLength(),drillHole.getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + drillHole.getLeftLength());
        }
        // == 修改日报信息
        // 当日打钻总量
        drillDaily.setTotalDoneLength(drillDaily.getTotalDoneLength().add(req.getDoneLength()));

        // == 修改钻孔信息
        // 已打长度
        drillHole.setDoneLength(drillHole.getDoneLength().add(req.getDoneLength()));
        // 剩余长度
        drillHole.setLeftLength(drillHole.getTotalLength().subtract(drillHole.getDoneLength()));
        // 若钻孔长度已完成，设置成孔日期参数为日报时间
        if(equal(drillHole.getDoneLength(), drillHole.getTotalLength())){
            drillHole.setCompleteTime(drillDaily.getDailyTime());
        }

        // == 修改钻孔工作统计参数
        DrillWorkEntity drillWork = drillHole.getDrillWork();
        // 钻孔总数: 不变
        // 钻孔总量：不变
        // 已施工钻孔总数：若钻孔长度已完成，已施工数 + 1
        if(equal(drillHole.getDoneLength(), drillHole.getTotalLength())){
            drillWork.setCompletedDrillHoleNumber(drillWork.getCompletedDrillHoleNumber() + LongValueConstant.ONE);
        }
        // 已打总量: 叠加
        drillWork.setTotalDoneLength(drillWork.getTotalDoneLength().add(req.getDoneLength()));
        // 未施工钻孔总数: 钻孔总数 - 已施工钻孔总数
        drillWork.setNotCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());
        // 未打总量：钻孔总量 - 已打总量
        drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));

        // == 添加钻孔日报详情
        DrillDailyDetailEntity detail = new DrillDailyDetailEntity();
        // 工作长度
        detail.setDoneLength(req.getDoneLength());
        // 关联的日报信息
        detail.setDrillDaily(drillDaily);
        // 关联的钻孔
        detail.setDrillHole(drillHole);
        // 序号
        detail.setOrderNumber(req.getOrderNumber());
        // 备注信息
        detail.setRemarks(req.getRemarks());

        // 更新：日报打钻总量（当前日报的所有钻孔已打综合）
        // 更新：钻孔信息（钻孔已打长度，剩余长度）
        // 添加：打孔信息
        return JoyResult.buildSuccessResultWithData(drillDailyDetailRepository.save(detail));
    }

    /**
     * 改
     */
    public JoyResult update(DrillDailyDetailUpdateReq req) {
        // 获取打钻详情信息
        DrillDailyDetailEntity detail = drillDailyDetailRepository.findAllById(req.getId());
        if(detail == null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_NOT_EXIST);
        }
        // 若提交的长度需要大于0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 修改长度：与旧长度的差值相当于新增加的长度（可能为负值）
        BigDecimal offset = req.getDoneLength().subtract(detail.getDoneLength());
        DrillHoleEntity drillHole = detail.getDrillHole();
        // 若差值大于钻孔的剩余长度，不合法
        if(more(offset, drillHole.getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + drillHole.getLeftLength());
        }
        // == 修改日报信息：old + offset
        DrillDailyEntity drillDaily = detail.getDrillDaily();
        drillDaily.setTotalDoneLength(drillDaily.getTotalDoneLength().add(offset));

        // == 修改钻孔信息：old + offset
        drillHole.setDoneLength(drillHole.getDoneLength().add(offset));
        // 剩余长度
        drillHole.setLeftLength(drillHole.getTotalLength().subtract(drillHole.getDoneLength()));
        // 若工作已完成，则设置成孔日期参数为日报时间
        if(equal(drillHole.getDoneLength(), drillHole.getTotalLength())){
            drillHole.setCompleteTime(drillDaily.getDailyTime());
        }else {// 否则可能由于修改导致未完成
            drillHole.setCompleteTime(null);
        }
        // == 修改钻孔工作统计参数
        DrillWorkEntity drillWork = drillHole.getDrillWork();
        // 钻孔总数: 不变
        // 钻孔总量：不变
        // 已施工钻孔总数：若钻孔长度已完成，已施工数 + 1
        if(equal(drillHole.getDoneLength(), drillHole.getTotalLength())){
            drillWork.setCompletedDrillHoleNumber(drillWork.getCompletedDrillHoleNumber() + LongValueConstant.ONE);
        }
        // 已打总量: + offset
        drillWork.setTotalDoneLength(drillWork.getTotalDoneLength().add(offset));
        // 未施工钻孔总数: 钻孔总数 - 已施工钻孔总数
        drillWork.setNotCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());
        // 未打总量：钻孔总量 - 已打总量
        drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));

        // == 修改打钻详情信息
        // 工作长度
        detail.setDoneLength(req.getDoneLength());
        // 序号
        detail.setOrderNumber(req.getOrderNumber());
        // 备注信息
        detail.setRemarks(req.getRemarks());
        // save.
        return JoyResult.buildSuccessResultWithData(drillDailyDetailRepository.save(detail));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {

        // 获取日报详情信息
        DrillDailyDetailEntity detail = drillDailyDetailRepository.findAllById(id);
        if(detail == null){
            return JoyResult.buildFailedResult(Notice.DAILY_DETAIL_NOT_EXIST);
        }
        DrillHoleEntity drillHole = detail.getDrillHole();
        DrillWorkEntity drillWork = drillHole.getDrillWork();
        DrillDailyEntity drillDaily = detail.getDrillDaily();

        // == 修改钻孔信息,将钻孔长度进行回溯(还原删除数据处理掉的长度)
        // 已打长度：钻孔已打长度 - 已打长度
        drillHole.setDoneLength(ComputeUtils.sub(drillHole.getDoneLength(), detail.getDoneLength()));
        // 剩余长度: 重新计算
        drillHole.setLeftLength(ComputeUtils.sub(drillHole.getTotalLength(), drillHole.getDoneLength()));
        // == 如果是钻孔工作已经是完成状态（成孔日期不为空）：
        // 1.修改成孔日期为空
        // 2.钻孔工作的已完成数 - 1，剩余数量 + 1（总-已）
        if(drillHole.getCompleteTime() != null){
            // 设置为空，现在肯定是未完成状态
            drillHole.setCompleteTime(null);
            // 关联的工作的已完成数 - 1
            drillWork.setCompletedDrillHoleNumber(drillWork.getCompletedDrillHoleNumber() - LongValueConstant.ONE);
            // 重新计算剩余数量
            drillWork.setCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());
        }

        // == 打钻日报
        // 打钻总量旧值 - 删掉的日报详情的长度
        drillDaily.setTotalDoneLength(drillDaily.getTotalDoneLength().subtract(detail.getDoneLength()));

        // == 打钻工作统计信息
        // 钻孔总数: 不变
        // 钻孔总量：不变
        // 已打总量： -
        drillWork.setTotalDoneLength(drillWork.getTotalDoneLength().subtract(detail.getDoneLength()));
        // 未打总量：钻孔总量 - 已打总量
        drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));

        // 软删除
        detail.setIsDelete(true);
        drillDailyDetailRepository.save(detail);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(drillDailyDetailRepository.findAllById(id));
    }
}
