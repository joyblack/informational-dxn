package com.joy.xxfy.informationaldxn.module.produce.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningDailyEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.entity.BackMiningFaceEntity;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningDailyRepository;
import com.joy.xxfy.informationaldxn.module.backmining.domain.repository.BackMiningFaceRepository;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.entity.CmPlatformEntity;
import com.joy.xxfy.informationaldxn.module.cmplatform.domain.repository.CmPlatformRepository;
import com.joy.xxfy.informationaldxn.module.common.service.BaseService;
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
import com.joy.xxfy.informationaldxn.module.produce.domain.vo.*;
import com.joy.xxfy.informationaldxn.module.produce.web.res.CpStatisticRes;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationaldxn.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationaldxn.publish.constant.BigDecimalValueConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ExportConstant;
import com.joy.xxfy.informationaldxn.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationaldxn.publish.result.JoyResult;
import com.joy.xxfy.informationaldxn.publish.utils.CompareUtil;
import com.joy.xxfy.informationaldxn.publish.utils.DateUtil;
import com.joy.xxfy.informationaldxn.publish.utils.excel.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.joy.xxfy.informationaldxn.publish.utils.format.FormatToStringValueUtil.numberFormat;

/**
 * 集团调度日报服务
 */
@Transactional
@Service
public class CpStatisticService extends BaseService {

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

    @Autowired
    private CmPlatformRepository cmPlatformRepository;

    /**
     * 获取煤矿生产日报服务的所有统计数据
     */
    public JoyResult getData(UserEntity loginUser, Date time) {
        // 返回结果
        List<CpStatisticRes> result = new ArrayList<>();
        // 依次处理每一个煤矿的数据
        List<CmPlatformEntity> cms = cmPlatformRepository.findAll();
        for (CmPlatformEntity cm : cms) {
            System.out.println("煤矿平台信息：" + cm);
            // 返回结果的子元素
            CpStatisticRes res = new CpStatisticRes();
            // == 填充平台名字
            res.setCmPlatformName(cm.getCmName());
            // == 填充统计数据
            // 子元素的子元素：统计数据部分
            List<CpStatisticVo> statisticData = new ArrayList<>();
            // 获取关联的公司信息
            DepartmentEntity company = cm.getUser().getDepartment();
            // 获取单个平台的回采统计信息
            List<BackMiningStatisticVo> backMiningData = getBackMiningData(company, time);
            // 掘进统计信息
            List<DrivingStatisticVo> drivingData = getDrivingData(company, time);
            // 钻孔统计信息
            List<DrillStatisticVo> drillData = getDrillData(company, time);
            int max = CompareUtil.getMaxNumber(backMiningData.size(), drivingData.size(), drillData.size());
            // 填充统计数据, 注意获取到的数据的最后一条是统计
            for (int i = 0; i < max - 1; i++) {
                CpStatisticVo vo = new CpStatisticVo();
                // 组装回采
                if(i < backMiningData.size() - 1){
                    vo.setBackMiningInfo(backMiningData.get(i));
                }else{
                    vo.setBackMiningInfo(null);
                }
                // 组装掘进
                if(i < drivingData.size() - 1){
                    vo.setDrivingInfo(drivingData.get(i));
                }else{
                    vo.setDrivingInfo(null);
                }
                // 组装回采
                if(i < drillData.size() - 1){
                    vo.setDrillInfo(drillData.get(i));
                }else{
                    vo.setDrillInfo(null);
                }
                statisticData.add(vo);
            }
            // 数据部分的最后一条统计数据：合计，合计二字可以理解为工作名称
            CpStatisticVo last = new CpStatisticVo();
            last.setBackMiningInfo(backMiningData.get(backMiningData.size() - 1));
            last.setDrivingInfo(drivingData.get(drivingData.size() - 1));
            last.setDrillInfo(drillData.get(drillData.size() - 1));
            // 表明该条数据为合计数据
            last.setAmount(true);
            statisticData.add(last);
            // 填充数据
            res.setStatisticData(statisticData);

            // == 填充备注信息部分，在数据库中查询
            ProduceCmDailyEntity produceCmDailyEntity = produceCmDailyRepository.findFirstByBelongCompanyAndDailyTime(company, time);
            if(produceCmDailyEntity != null){
                // 说明有备注信息，设置备注信息
                res.setRemarks(produceCmDailyEntity.getRemarks());
            }else{
                res.setRemarks(null);
            }
            // 加入到最后的返回结果之中，继续下一个平台的数据统计
            result.add(res);
        }
        // 最后计算所有煤矿的合计信息，也许前端不喜欢这样的结构体，不过暂时先这样返回。
        CpStatisticRes amount = new CpStatisticRes();
        amount.setCmPlatformName(ResultDataConstant.AMOUNT_OF_ALL_CM_PLATFORM);
        CpStatisticVo vo = new CpStatisticVo();
        result.forEach(s -> s.getStatisticData().forEach(d -> {
            // 若是统计数据，收走。
            if(d.isAmount()){
                /*回采工作面：早中班日月产煤量*/
                vo.setBackMiningMorningOutput(vo.getBackMiningMorningOutput().add(d.getBackMiningMorningOutput()));
                vo.setBackMiningMorningOutput(vo.getBackMiningNoonOutput().add(d.getBackMiningNoonOutput()));
                vo.setBackMiningEveningOutput(vo.getBackMiningEveningOutput().add(d.getBackMiningEveningOutput()));
                vo.setBackMiningDayOutput(vo.getBackMiningDayOutput().add(d.getBackMiningDayOutput()));
                vo.setBackMiningMonthOutput(vo.getBackMiningMonthOutput().add(d.getBackMiningMonthOutput()));

                /* 掘进工作面：早中班日月进尺 */
                vo.setDrivingMorningLength(vo.getDrivingMorningLength().add(d.getDrivingMorningLength()));
                vo.setDrivingNoonLength(vo.getDrivingNoonLength().add(d.getDrivingNoonLength()));
                vo.setDrivingEveningLength(vo.getDrivingEveningLength().add(d.getDrivingEveningLength()));
                vo.setDrivingDayLength(vo.getDrivingDayLength().add(d.getDrivingDayLength()));
                vo.setDrivingMonthLength(vo.getDrivingMonthLength().add(d.getDrivingMonthLength()));

                /* 钻孔工作：日月进尺 */
                vo.setDrillDayLength(vo.getDrillDayLength().add(d.getDrillDayLength()));
                vo.setDrillMonthLength(vo.getDrillMonthLength().add(d.getDrillMonthLength()));
            }
        }));
        amount.setStatisticData(Arrays.asList(vo));
        amount.setRemarks(null);
        result.add(amount);
        return JoyResult.buildSuccessResultWithData(result);
    }


    /**
     * 获取回采统计
     */
    public List<BackMiningStatisticVo> getBackMiningData(DepartmentEntity company, Date time) {
        List<BackMiningStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        BackMiningStatisticVo amount = new BackMiningStatisticVo();
        amount.setName(ResultDataConstant.AMOUNT);
        // 只统计拥有日报的工作面信息
        List<BackMiningFaceEntity> faces = backMiningFaceRepository.findAllByDailyTimeAndBelongCompany(time, company);
        for (BackMiningFaceEntity face : faces) {
            BackMiningStatisticVo vo = new BackMiningStatisticVo();
            // 获取该工作面对应的本日的日报信息
            List<BackMiningDailyEntity> dailies = backMiningDailyRepository.findAllByBackMiningFaceAndDailyTime(face, time);
            // 若日报有填写, 获取日报详情信息
            if(dailies.size() > 0){

                // 同一个班次会出现很多次(因为队伍不同)，和平台不同的是，这里统计的是煤产量，而不是进尺。
                for (BackMiningDailyEntity daily : dailies) {
                    switch (daily.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningOutput(vo.getMorningOutput().add(daily.getOutput()));
                            break;
                        case NOON:
                            vo.setNoonOutput(vo.getNoonOutput().add(daily.getOutput()));
                            break;
                        case EVENING:
                            vo.setEveningOutput(vo.getEveningOutput().add(daily.getOutput()));
                            break;
                        default:break;
                    }
                    // 以日统计
                    vo.setDayOutput(vo.getDayOutput().add(daily.getOutput()));
                }
            }
            // 统计月信息
            String ym = DateUtil.getYMString(new Date(),false);
            CmStatisticVo temp = backMiningDailyRepository.statisticOutput(face,ym);
             // 月累计产煤
            vo.setMonthOutput(temp.getMonthOutput() == null? BigDecimalValueConstant.ZERO : temp.getMonthOutput());
            // 工作面名称
            vo.setName(face.getBackMiningFaceName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningOutput(amount.getMorningOutput().add(vo.getMorningOutput()));
            amount.setNoonOutput(amount.getNoonOutput().add(vo.getNoonOutput()));
            amount.setEveningOutput(amount.getEveningOutput().add(vo.getEveningOutput()));
            amount.setDayOutput(amount.getDayOutput().add(vo.getDayOutput()));
            amount.setMonthOutput(amount.getMonthOutput().add(vo.getMonthOutput()));
        }
        // 最后一条添加为合计
        result.add(amount);
        return result;
    }



    /**
     * 获取掘进面统计数据
     */
    public List<DrivingStatisticVo> getDrivingData(DepartmentEntity company, Date time) {
        List<DrivingStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        DrivingStatisticVo amount = new DrivingStatisticVo();
        amount.setName(ResultDataConstant.AMOUNT);
        // 只统计拥有日报的工作面信息
        List<DrivingFaceEntity> faces = drivingFaceRepository.findAllByDailyTimeAndBelongCompany(time, company);
        for (DrivingFaceEntity face : faces) {
            DrivingStatisticVo vo = new DrivingStatisticVo();
            // 获取该工作面对应的本日的日报信息
            List<DrivingDailyEntity> dailies = drivingDailyRepository.findAllByDrivingFaceAndDailyTime(face, time);
            // 若日报有填写, 获取日报详情信息
            if(dailies.size() > 0){
                // 不同队伍的统计
                for (DrivingDailyEntity daily : dailies) {
                    switch (daily.getShifts()) {
                        case MORNING:
                            // 进尺
                            vo.setMorningLength(vo.getMorningLength().add(daily.getDoneLength()));
                            break;
                        case NOON:
                            vo.setNoonLength(vo.getNoonLength().add(daily.getDoneLength()));
                            break;
                        case EVENING:
                            vo.setEveningLength(vo.getEveningLength().add(daily.getDoneLength()));
                            break;
                        default:break;
                    }
                    // 日统计
                    vo.setDayLength(vo.getDayLength().add(daily.getDoneLength()));
                }
            }
            // 统计月信息
            String ym = DateUtil.getYMString(new Date(),false);
            DrivingStatisticVo monthData = drivingDailyRepository.statisticDoneLength(face, ym);
            vo.setMonthLength(monthData.getMonthLength() == null? BigDecimalValueConstant.ZERO :monthData.getMonthLength());
            // 工作面名称
            vo.setName(face.getDrivingFaceName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setMorningLength(amount.getMorningLength().add(vo.getMorningLength()));
            amount.setNoonLength(amount.getNoonLength().add(vo.getNoonLength()));
            amount.setEveningLength(amount.getEveningLength().add(vo.getEveningLength()));
            amount.setDayLength(amount.getDayLength().add(vo.getDayLength()));
            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
        }
        // 合计项
        result.add(amount);
        return result;
    }



    /**
     * 获取打钻统计，打钻统计不同于上面二者，班次是放在总日报信息里面的，而且已经进行了统计，因此，无需查询详细的打孔信息即可
     * 完成此处的需求。钻孔也只有打钻进尺的数据
     * 集团只需进行日统计、月统计。
     */
    public List<DrillStatisticVo> getDrillData(DepartmentEntity company, Date time) {
        List<DrillStatisticVo> result = new ArrayList<>();
        // 合计元素(也伪装成一条记录的形式返回给前端)
        DrillStatisticVo amount = new DrillStatisticVo();
        amount.setName(ResultDataConstant.AMOUNT);
        // 只获取有日报的钻孔工作列表
        List<DrillWorkEntity> works = drillWorkRepository.findAllByDistinctBelongCompanyAndDailyTime(company,time);
        for (DrillWorkEntity work : works) {
            DrillStatisticVo vo = new DrillStatisticVo();
            // 获取该工作面对应的本日的日报信息
            List<DrillDailyEntity> dailies = drillDailyRepository.findAllByDrillWorkAndDailyTime(work, time);
            for (DrillDailyEntity daily : dailies) {
                vo.setDayLength(vo.getDayLength().add(daily.getTotalDoneLength()));
            }
            // == 统计月信息
            String ym = DateUtil.getYMString(new Date(),false);
            DrillStatisticVo temp = drillDailyRepository.statisticDoneLength(work, ym);
            // 月累计进尺
            vo.setMonthLength(temp.getMonthLength() == null? BigDecimalValueConstant.ZERO :temp.getMonthLength());
            // 工作名称
            vo.setName(work.getDrillWorkName());
            // 添加到结果数组
            result.add(vo);
            // 计算合计
            amount.setDayLength(amount.getDayLength().add(vo.getDayLength()));
            amount.setMonthLength(amount.getMonthLength().add(vo.getMonthLength()));
        }
        result.add(amount);
        return result;
    }


    public void exportData(Date time, UserEntity loginUser, HttpServletRequest request, HttpServletResponse response) {
        // 日期导出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(ExportConstant.DATE_FORMAT);
        // 三段数据
        ExcelWriter writer = ExcelUtil.getWriter();
        // 文件名，也是表格的头信息
        String fileName = loginUser.getCompany().getDepartmentName() + "日报表-" + dateFormat.format(time.getTime());
        // 头合并两行
        writer.merge(0,1,0,16, fileName,true);
        writer.setCurrentRow(writer.getCurrentRow() + 2);
        int currentRow = writer.getCurrentRow();
        writer.merge(currentRow,currentRow + 1,0,0,"煤矿名称",false);
        writer.merge(currentRow,currentRow + 1,1,1,"回采面名称",false);
        writer.merge(currentRow,currentRow,2,4,"班产量（吨）",false);
        writer.merge(currentRow,currentRow + 1,5,5,"日产量（吨）",false);
        writer.merge(currentRow,currentRow + 1,6,6,"月累计产量（吨）",false);
        writer.merge(currentRow,currentRow + 1,7,7,"掘进面名称",false);
        writer.merge(currentRow,currentRow,8,10,"班掘进进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,11,11,"日掘进进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,12,12,"月累计掘进进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,13,13,"钻孔工作名称",false);
        writer.merge(currentRow,currentRow + 1,14,14,"日打钻进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,15,15,"月累计打钻进尺（米）",false);
        writer.merge(currentRow,currentRow + 1,16,16,"备注",false);
        // 前进
        writer.setCurrentRow(writer.getCurrentRow() + 1);
        // 添加标题头
        writer.writeRow(CollUtil.newArrayList(ExportConstant.FIELD_CP_PRODUCE));

        // 填充数据
        List<CpStatisticRes> allData = (List<CpStatisticRes>)(getData(loginUser, time).getData());
        for(CpStatisticRes data : allData){
            int startRow = writer.getCurrentRow();
            List<List<String>> rows = new ArrayList<>();
            // 若不是最后的统计数据（全平台）且只有一条数据,填充一条空行记录
            if(data.getStatisticData().size() == 1 && !data.getCmPlatformName().equals(ResultDataConstant.AMOUNT_OF_ALL_CM_PLATFORM)){
//                    List<String> row = CollUtil.newArrayList("");
//                    rows.add(row);
                writer.passCurrentRow();
            }
            for (CpStatisticVo st : data.getStatisticData()) {
                    List<String> row = CollUtil.newArrayList(
                            // == 煤矿名称：待合并
                            "",
                            // == 回采工作面
                            // * 名称
                            numberFormat(st.getBackMiningFaceName()),
                            // * 班产量
                            numberFormat(st.getBackMiningMorningOutput()),
                            numberFormat(st.getBackMiningNoonOutput()),
                            numberFormat(st.getBackMiningEveningOutput()),
                            // * 日、月产量
                            numberFormat(st.getBackMiningDayOutput()),
                            numberFormat(st.getBackMiningMonthOutput()),

                            // == 掘进工作面
                            // * 名称
                            numberFormat(st.getDrivingFaceName()),
                            // * 班掘进进尺
                            numberFormat(st.getDrivingMorningLength()),
                            numberFormat(st.getDrivingNoonLength()),
                            numberFormat(st.getDrivingEveningLength()),
                            // 日、月
                            numberFormat(st.getDrivingDayLength()),
                            numberFormat(st.getDrivingMonthLength()),
                            // == 钻孔工作
                            // * 名称
                            numberFormat(st.getDrillWorkName()),
                            // * 日打钻进尺
                            numberFormat(st.getDrillDayLength()),
                            // * 月累计打钻进尺
                            numberFormat(st.getDrillMonthLength()),

                            // 备注信息
                            numberFormat(data.getRemarks())

                    );
                    rows.add(row);
                }
                // 写入
                writer.write(rows,true);
                // 写入煤矿名称
                if(data.getCmPlatformName().equals(ResultDataConstant.AMOUNT_OF_ALL_CM_PLATFORM)){
                    // 综合只需一行，并且吃掉前两列
                    writer.merge(startRow, startRow,0,1,data.getCmPlatformName(),false);

                }else{
                    writer.merge(startRow, writer.getCurrentRow() - 1,16,16,data.getRemarks(),false);
                    writer.merge(startRow, writer.getCurrentRow() - 1,0,0,data.getCmPlatformName(),false);
                }
//                // 写入备注信息
//
            }

        // 设置宽度
        writer.setColumnWidth(0,22);
        writer.setColumnWidth(1,20);
        writer.setColumnWidth(5,16);
        writer.setColumnWidth(6,16);
        writer.setColumnWidth(7,16);

        writer.setColumnWidth(11,16);
        writer.setColumnWidth(12,16);
        writer.setColumnWidth(13,16);
        writer.setColumnWidth(14,16);
        writer.setColumnWidth(15,16);
        writer.setColumnWidth(16,16);


        ExportUtil.exportData(request, response, fileName, writer);
    }
}
