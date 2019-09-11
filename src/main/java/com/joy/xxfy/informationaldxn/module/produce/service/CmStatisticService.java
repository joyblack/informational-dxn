package com.joy.xxfy.informationaldxn.module.produce.service;

import com.joy.xxfy.informationaldxn.module.produce.domain.vo.CmStatisticVo;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
import com.joy.xxfy.informationaldxn.module.common.web.req.TimeReq;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillDailyEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.entity.DrillWorkEntity;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillDailyRepository;
import com.joy.xxfy.informationaldxn.module.drill.domain.repository.DrillWorkRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingDailyEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.entity.DrivingFaceEntity;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingDailyRepository;
import com.joy.xxfy.informationaldxn.module.driving.domain.repository.DrivingFaceRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.entity.ProduceCmDailyEntity;
import com.joy.xxfy.informationaldxn.module.produce.domain.repository.ProduceCmDailyRepository;
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.DrillStatisticVo;
import com.joy.xxfy.informationaldxn.module.produce.web.req.SetRemarkReq;
import com.joy.xxfy.informationaldxn.module.produce.web.res.CmStatisticRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ExportConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationaldxn.publish.utils.LogUtil;
import com.joy.xxfy.informationaldxn.publish.utils.excel.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joy.xxfy.informationaldxn.publish.utils.format.FormatToStringValueUtil.numberFormat;

/**
 * 煤矿生产日报服务
 */
@Transactional
@Service
public class CmStatisticService extends BaseService {

    @Autowired
    private ProduceCmDailyRepository produceCmDailyRepository;

    @Autowired
    private DrivingDailyRepository drivingDailyRepository;

    @Autowired
    private DrivingFaceRepository drivingFaceRepository;

    @Autowired
    private BackMiningFaceRepository backMiningFaceRepository;

    @Autowired
    private BackMiningDailyRepository backMiningDailyRepository;

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
        ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findFirstByBelongCompanyAndDailyTime(company, time);
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
        for (DrivingFaceEntity face : faces) {
            CmStatisticVo vo = new CmStatisticVo();
            // 获取该工作面对应的本日的日报信息
            List<DrivingDailyEntity> dailies = drivingDailyRepository.findAllByDrivingFaceAndDailyTime(face, time);
            // 若日报有填写, 开始统计
            if(dailies.size() > 0){
                for (DrivingDailyEntity daily : dailies) {
                    switch (daily.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningLength(vo.getMorningLength().add(daily.getDoneLength()));
                            // 人数
                            vo.setMorningPeople(vo.getMorningPeople() + daily.getPeopleNumber());
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(daily.getDoneLength()));
                            vo.setNoonPeople(vo.getNoonPeople() + daily.getPeopleNumber());
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(daily.getDoneLength()));
                            vo.setEveningPeople(vo.getEveningPeople() + daily.getPeopleNumber());
                            break;
                        default:break;
                    }
                    // 产煤: 以日统计
                    vo.setDayOutput(vo.getDayOutput().add(daily.getOutput()));
                }
                // 圆班
                vo.setShiftTotalLength(vo.getMorningLength().add(vo.getNoonLength()).add(vo.getEveningLength()));
                vo.setShiftTotalPeople(vo.getMorningPeople() + vo.getNoonPeople() + vo.getEveningPeople());
            }
            // 统计月信息
            String ym = DateUtil.getYMString(new Date(),false);
            CmStatisticVo cmStatisticVo = drivingDailyRepository.statisticDoneLengthAndOutPut(face,ym);
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

            amount.setDayOutput(amount.getDayOutput().add(vo.getDayOutput()));

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
            List<BackMiningDailyEntity> dailies = backMiningDailyRepository.findAllByBackMiningFaceAndDailyTime(face, time);
            // 若日报有填写
            if(dailies.size() > 0){
                // 同一个班次会出现很多次(因为队伍不同)
                for (BackMiningDailyEntity daily : dailies) {
                    switch (daily.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningLength(vo.getMorningLength().add(daily.getDoneLength()));
                            // 人数
                            vo.setMorningPeople(vo.getMorningPeople() + daily.getPeopleNumber());
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(daily.getDoneLength()));
                            vo.setNoonPeople(vo.getNoonPeople() + daily.getPeopleNumber());
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(daily.getDoneLength()));
                            vo.setEveningPeople(vo.getEveningPeople() + daily.getPeopleNumber());
                            break;
                        default:break;
                    }
                    // 产煤: 以日统计
                    vo.setDayOutput(vo.getDayOutput().add(daily.getOutput()));
                }
                // 圆班
                vo.setShiftTotalLength(vo.getMorningLength().add(vo.getNoonLength()).add(vo.getEveningLength()));
                vo.setShiftTotalPeople(vo.getMorningPeople() + vo.getNoonPeople() + vo.getEveningPeople());
            }
            // 统计月信息
            String ym = DateUtil.getYMString(new Date(),false);
            CmStatisticVo cmStatisticVo = backMiningDailyRepository.statisticDoneLengthAndOutput(face, ym);
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

            amount.setDayOutput(amount.getDayOutput().add(vo.getDayOutput()));
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
            String ym = DateUtil.getYMString(new Date(),false);
            DrillStatisticVo cmStatisticVo = drillDailyRepository.statisticDoneLength(work, ym);
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
        ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findFirstByBelongCompanyAndDailyTime(loginUser.getCompany(), req.getTime());
        if(produceCmDailyEntity == null){
            produceCmDailyEntity = new ProduceCmDailyEntity();
        }
        produceCmDailyEntity.setBelongCompany(loginUser.getCompany());
        produceCmDailyEntity.setDailyTime(req.getTime());
        produceCmDailyEntity.setRemarks(req.getRemarks());
        return JoyResult.buildSuccessResultWithData(produceCmDailyRepository.save(produceCmDailyEntity));
    }


    public void exportData(Date time, UserEntity loginUser, HttpServletRequest request, HttpServletResponse response) {
        // 日期导出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(ExportConstant.DATE_FORMAT);
        List<List<String>> rows;

        // 三段数据
        ExcelWriter writer = ExcelUtil.getWriter();
        // 文件名，也是表格的头信息
        String fileName = loginUser.getCompany().getDepartmentName() + "日报表-" + dateFormat.format(time);
        // 头合并两行
        writer.merge(0,1,0,11, fileName,true);
        writer.setCurrentRow(writer.getCurrentRow() + 2);
        // == 1. 掘进面处理
        rows = new ArrayList<>();
        int currentRow = writer.getCurrentRow();
        writer.merge(currentRow,currentRow + 1,0,0,"掘进面名称",false);
        writer.merge(currentRow,currentRow,1,4,"掘进进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,5,5,"月累计进尺（米）",false);
        writer.merge(currentRow,currentRow,6,9,"入井人数",false);
        writer.merge(currentRow,currentRow + 1,10,10,"今日产煤（吨）",false);
        writer.merge(currentRow,currentRow + 1,11,11,"月累计产煤（吨）",false);
        // 前进
        writer.setCurrentRow(writer.getCurrentRow() + 1);
        // 添加标题头
        writer.writeRow(CollUtil.newArrayList(ExportConstant.FIELD_CM_PRODUCE));
        // 装配数据部分
        List<CmStatisticVo> drivingData = getDrivingData(loginUser.getCompany(), time);
        for (CmStatisticVo d : drivingData) {
            List<String> row = CollUtil.newArrayList(
                    // == 掘进面名称
                    numberFormat(d.getWorkName()),
                    // == 掘进进尺（早、中、晚、圆、月累计）
                    numberFormat(d.getMorningLength()),
                    numberFormat(d.getNoonLength()),
                    numberFormat(d.getEveningLength()),
                    numberFormat(d.getShiftTotalLength()),
                    numberFormat(d.getMonthLength()),
                     // == 入井人数：早、中、晚、圆）
                    numberFormat(d.getMorningPeople()),
                    numberFormat(d.getNoonPeople()),
                    numberFormat(d.getEveningPeople()),
                    numberFormat(d.getShiftTotalPeople()),
                    // == 今日产煤
                    numberFormat(d.getDayOutput()),
                    // == 月累计产煤
                    numberFormat(d.getMonthOutput())
            );
            rows.add(row);
        }
        writer.write(rows, true);

        // == 2.回采
        rows = new ArrayList<>();
        currentRow = writer.getCurrentRow();
        writer.merge(currentRow,currentRow + 1,0,0,"回采面名称",false);
        writer.merge(currentRow,currentRow,1,4,"采煤进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,5,5,"月累计进尺（米）",false);
        writer.merge(currentRow,currentRow,6,9,"入井人数",false);
        writer.merge(currentRow,currentRow + 1,10,10,"今日产煤（吨）",false);
        writer.merge(currentRow,currentRow + 1,11,11,"月累计产煤（吨）",false);
        // 前进
        writer.setCurrentRow(writer.getCurrentRow() + 1);
        // 添加标题头
        writer.writeRow(CollUtil.newArrayList(ExportConstant.FIELD_CM_PRODUCE));
        // 装配数据部分
        List<CmStatisticVo> backMiningData = getBackMiningData(loginUser.getCompany(), time);
        for (CmStatisticVo d : backMiningData) {
            List<String> row = CollUtil.newArrayList(
                    // == 掘进面名称
                    numberFormat(d.getWorkName()),
                    // == 进尺（早、中、晚、圆、月累计）
                    numberFormat(d.getMorningLength()),
                    numberFormat(d.getNoonLength()),
                    numberFormat(d.getEveningLength()),
                    numberFormat(d.getShiftTotalLength()),
                    numberFormat(d.getMonthLength()),
                    // == 入井人数：早、中、晚、圆）
                    numberFormat(d.getMorningPeople()),
                    numberFormat(d.getNoonPeople()),
                    numberFormat(d.getEveningPeople()),
                    numberFormat(d.getShiftTotalPeople()),
                    // == 今日产煤
                    numberFormat(d.getDayOutput()),
                    // == 月累计产煤
                    numberFormat(d.getMonthOutput())
            );
            rows.add(row);
        }
        writer.write(rows, true);


        // == 3.打钻
        rows = new ArrayList<>();
        int startRow = writer.getCurrentRow();
        writer.merge(startRow,startRow + 1,0,0,"钻孔工作",false);
        writer.merge(startRow,startRow,1,4,"打钻进尺（米）",false);
        writer.merge(startRow,startRow + 1,5,5,"月累计进尺（米）",false);

        // 前进
        writer.setCurrentRow(writer.getCurrentRow() + 1);
        // 添加标题头
        writer.writeRow(CollUtil.newArrayList(ExportConstant.FIELD_CM_PRODUCE_SHORT));
        // 装配数据部分
        List<CmStatisticVo> drillData = getDrillData(loginUser.getCompany(), time);
        for (CmStatisticVo d : drillData) {
            List<String> row = CollUtil.newArrayList(
                    // == 面名称
                    numberFormat(d.getWorkName()),
                    // == 进尺（早、中、晚、圆、月累计）
                    numberFormat(d.getMorningLength()),
                    numberFormat(d.getNoonLength()),
                    numberFormat(d.getEveningLength()),
                    numberFormat(d.getShiftTotalLength()),
                    numberFormat(d.getMonthLength())
            );
            rows.add(row);
        }
        writer.write(rows, true);
        // 备注部分
        writer.merge(startRow,writer.getCurrentRow() - 1,6,6,"备注",false);
        CmStatisticRes res = new CmStatisticRes();
        ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findFirstByBelongCompanyAndDailyTime(loginUser.getCompany(), time);
        writer.merge(startRow,writer.getCurrentRow() - 1,7,11, produceCmDailyEntity == null? null: produceCmDailyEntity.getRemarks(),false);

        // 设置宽度
        writer.setColumnWidth(0,28);
        writer.setColumnWidth(5,20);
        writer.setColumnWidth(10,20);
        writer.setColumnWidth(11,20);

        ExportUtil.exportData(request, response, fileName, writer);
    }


}
