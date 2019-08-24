package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.module.department.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyDetailEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyDetailRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillDailyDetailAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillDailyDetailUpdateReq;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.less;
import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.more;

@Transactional
@Service
public class DrillDailyDetailService {

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillHoleRepository drillHoleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
            return JoyResult.buildFailedResult(Notice.DRILL_DAILY_NOT_EXIST);
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

        // == 修改日报信息（当日打钻总量）
        drillDaily.setTotalDoneLength(drillDaily.getTotalDoneLength().add(req.getDoneLength()));

        // == 修改钻孔信息
        // 已打长度
        drillHole.setDoneLength(drillHole.getDoneLength().add(req.getDoneLength()));
        // 剩余长度
        drillHole.setLeftLength(drillHole.getTotalLength().subtract(drillHole.getDoneLength()));

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
            return JoyResult.buildFailedResult(Notice.DRILL_DAILY_DETAIL_NOT_EXIST);
        }
        // 未验证钻孔信息是否合法。

        // 若提交的长度需要大于0
        if(less(req.getDoneLength(), BigDecimal.ZERO)){
            return JoyResult.buildFailedResult(Notice.LENGTH_SHOULD_MORE_ZERO);
        }
        // 修改长度：与旧长度的差值相当于新增加的长度（可能为负值）
        BigDecimal offset = req.getDoneLength().subtract(detail.getDoneLength());

        // 若差值大于钻孔的剩余长度，不合法
        if(more(offset,detail.getDrillHole().getLeftLength())){
            return JoyResult.buildFailedResult(Notice.SET_LENGTH_MORE_LEFT_LENGTH,
                    ResultDataConstant.MESSAGE_LEFT_LENGTH + detail.getDrillHole().getLeftLength());
        }
        // == 修改日报信息：old + offset
        detail.getDrillDaily().setTotalDoneLength(detail.getDrillDaily().getTotalDoneLength().add(offset));

        // == 修改钻孔信息：old + offset
        detail.getDrillHole().setDoneLength(detail.getDrillHole().getDoneLength().add(offset));
        // 剩余长度
        detail.getDrillHole().setLeftLength(detail.getDrillHole().getTotalLength().subtract(detail.getDrillHole().getDoneLength()));

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
            return JoyResult.buildFailedResult(Notice.DRILL_DAILY_DETAIL_NOT_EXIST);
        }
        // == 修改钻孔信息,将钻孔长度进行回溯(还原删除数据处理掉的长度)
        // 已打长度：钻孔已打长度 - 已打长度
        detail.getDrillHole().setDoneLength(ComputeUtils.sub(detail.getDrillHole().getDoneLength(), detail.getDoneLength()));
        // 剩余长度: 重新计算
        detail.getDrillHole().setLeftLength(ComputeUtils.sub(detail.getDrillHole().getTotalLength(), detail.getDrillHole().getDoneLength()));

        // == 修改日报打钻总量：旧值 - 已打长度
        detail.getDrillDaily().setTotalDoneLength(ComputeUtils.sub(detail.getDrillDaily().getTotalDoneLength(), detail.getDoneLength()));
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
