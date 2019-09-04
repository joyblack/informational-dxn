package com.joy.xxfy.informationaldxn.module.drill.service;

import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillHoleEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillHoleRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillHoleAddReq;
import com.joy.xxfy.informationaldxn.module.drill.web.req.DrillHoleUpdateReq;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.LongValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.result.Notice;
import com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.RateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.project.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joy.xxfy.informationaldxn.publish.utils.ComputeUtils.equal;

@Transactional
@Service
public class DrillHoleService {

    @Autowired
    private DrillWorkRepository drillWorkRepository;

    @Autowired
    private DrillHoleRepository drillHoleRepository;

    @Autowired
    private DrillDailyRepository drillDailyRepository;

    /**
     * 添加
     */
    public JoyResult add(DrillHoleAddReq req) {
        // 获取工作信息
        DrillWorkEntity drillWork = drillWorkRepository.findAllById(req.getDrillWorkId());
        if(drillWork == null){
            return JoyResult.buildFailedResult(Notice.DRILL_WORK_NOT_EXIST);
        }
        // 添加信息
        DrillHoleEntity drillHoleEntity = new DrillHoleEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, drillHoleEntity);
        // 已打长度初始化为0
        drillHoleEntity.setDoneLength(BigDecimal.ZERO);
        // 剩余长度
        drillHoleEntity.setLeftLength(ComputeUtils.sub(drillHoleEntity.getTotalLength(), drillHoleEntity.getDoneLength()));
        // 进度
        drillHoleEntity.setProgress(RateUtil.compute(drillHoleEntity.getDoneLength(), drillHoleEntity.getTotalLength(),false));
        // == 设置工作信息
        // 初始化统计参数
        // 钻孔总数: +1
        drillWork.setTotalDrillHoleNumber(drillWork.getTotalDrillHoleNumber() + LongValueConstant.ONE);
        // 钻孔总量：+length
        drillWork.setTotalLength(drillWork.getTotalLength().add(req.getTotalLength()));
        // 已施工钻孔总数: 不变.
        // 未施工钻孔总数: 钻孔总数 - 已施工钻孔总数 = 当前未施工总数 + 1
        drillWork.setNotCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());
        // 已打总量: 不变
        // 未打总量：钻孔总量 - 已打总量 = 当前未打总量 + req.totalLength
        drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));
        // 进度
        drillWork.setProgress(RateUtil.compute(drillWork.getTotalDoneLength(), drillWork.getTotalLength(),false));
        // 修改时间
        drillWork.setUpdateTime(new Date());
        // 设置关联
        drillHoleEntity.setDrillWork(drillWork);
        // save
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.save(drillHoleEntity));
    }

    /**
     * 改
     */
    public JoyResult update(DrillHoleUpdateReq req) {
        DrillHoleEntity info = drillHoleRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_HOLE_NOT_EXIST);
        }
        // 判断总长是否有进行修改，从而修改关联钻孔工作的统计信息
        if(!equal(info.getTotalLength(), req.getTotalLength())){
            // 先判断是否允许修改:若已有日报，则不允许进行修改
            List<DrillDailyEntity> dailies = drillDailyRepository.findAllByDrillWork(info.getDrillWork());
            if(dailies.size() > 0){
                return JoyResult.buildFailedResult(Notice.DAILY_EXIST_CANT_EDIT_LENGTH);
            }else{
                DrillWorkEntity drillWork = info.getDrillWork();
                // == 设置工作信息
                // 初始化统计参数
                // 钻孔总数: 不变
                // 已施工钻孔总数: 不变
                // 未施工钻孔总数: 不变
                // 已打总量: 不变
                // 钻孔总量：+ offset
                drillWork.setTotalLength(drillWork.getTotalLength().add(info.getTotalLength().subtract(req.getTotalLength())));
                // 未打总量：钻孔总量 - 已打总量
                drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));
                // 进度
                drillWork.setProgress(RateUtil.compute(drillWork.getTotalDoneLength(), drillWork.getTotalLength(),false));
            }
        }

        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        // 钻孔进度
        info.setProgress(RateUtil.compute(info.getDoneLength(), info.getTotalLength(),false));
        // 若钻孔工作还未做完，不允许修改实际见煤、实际止煤等参数
        if(!equal(info.getDoneLength(), info.getTotalLength())){
            info.setRealAppearCoal(null);
            info.setRealDisappearCoal(null);
            info.setRealCoalThickness(null);
        }
        // save.
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        DrillHoleEntity info = drillHoleRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DRILL_HOLE_NOT_EXIST);
        }
        // 若已有日报，则不允许删除
        List<DrillDailyEntity> dailies = drillDailyRepository.findAllByDrillWork(info.getDrillWork());
        if(dailies.size() > 0) {
            return JoyResult.buildFailedResult(Notice.DAILY_EXIST_CANT_EDIT_LENGTH);
        }else{
            // 更新关联的钻孔工作信息
            DrillWorkEntity drillWork = info.getDrillWork();
            // 钻孔总数: - 1
            drillWork.setTotalDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - LongValueConstant.ONE);
            // 钻孔总量：- totalLength
            drillWork.setTotalLength(drillWork.getTotalLength().subtract(info.getTotalLength()));
            // 已施工钻孔总数: 不变.
            // 未施工钻孔总数: 钻孔总数 - 已施工钻孔总数 = 当前未施工总数 - 1
            drillWork.setNotCompletedDrillHoleNumber(drillWork.getTotalDrillHoleNumber() - drillWork.getCompletedDrillHoleNumber());
            // 已打总量: 不变
            // 未打总量
            drillWork.setTotalLeftLength(drillWork.getTotalLength().subtract(drillWork.getTotalDoneLength()));
            // 进度
            drillWork.setProgress(RateUtil.compute(drillWork.getTotalDoneLength(), drillWork.getTotalLength(),false));
        }
        info.setIsDelete(true);
        drillHoleRepository.save(info);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAllById(id));
    }

    /**
     * 获取数据(通过钻孔工作ID)
     */
    public JoyResult getAllByDrillWorkId(Long id) {

        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAllByDrillWorkId(id));
    }




    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(BasePageReq req) {
        return JoyResult.buildSuccessResultWithData(drillHoleRepository.findAll(getPredicates(req)));
    }

    /**
     * 暂时无条件获取
     */
    private Specification<DrillHoleEntity> getPredicates(BasePageReq req){
        return (Specification<DrillHoleEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
