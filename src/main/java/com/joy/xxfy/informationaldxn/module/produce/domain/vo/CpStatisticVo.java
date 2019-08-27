package com.joy.xxfy.informationaldxn.module.produce.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 集团统计实体
 */
@Data
@ToString
public class CpStatisticVo {
    /*回采工作面：早中班日月产煤量*/
    private String backMiningFaceName = "";
    private BigDecimal backMiningMorningOutput = BigDecimal.ZERO;
    private BigDecimal backMiningNoonOutput = BigDecimal.ZERO;
    private BigDecimal backMiningEveningOutput = BigDecimal.ZERO;
    private BigDecimal backMiningDayOutput = BigDecimal.ZERO;
    private BigDecimal backMiningMonthOutput = BigDecimal.ZERO;

    /* 掘进工作面：早中班日月进尺 */
    private String drivingFaceName = "";
    private BigDecimal drivingMorningLength = BigDecimal.ZERO;
    private BigDecimal drivingNoonLength = BigDecimal.ZERO;
    private BigDecimal drivingEveningLength = BigDecimal.ZERO;
    private BigDecimal drivingDayLength = BigDecimal.ZERO;
    private BigDecimal drivingMonthLength = BigDecimal.ZERO;

    /* 钻孔工作：日月进尺 */
    private String drillWorkName = "";
    private BigDecimal drillDayLength = BigDecimal.ZERO;
    private BigDecimal drillMonthLength = BigDecimal.ZERO;

    public void setBackMiningInfo(BackMiningStatisticVo vo){
        if(vo == null){
            backMiningFaceName = null;
            backMiningMorningOutput = null;
            backMiningNoonOutput = null;
            backMiningEveningOutput = null;
            backMiningDayOutput = null;
            backMiningMonthOutput = null;
        }else{
            backMiningFaceName = vo.getName();
            backMiningMorningOutput = vo.getMorningOutput();
            backMiningNoonOutput = vo.getNoonOutput();
            backMiningEveningOutput = vo.getEveningOutput();
            backMiningDayOutput = vo.getDayOutput();
            backMiningMonthOutput = vo.getMonthOutput();
        }
    }

    public void setDrivingInfo(DrivingStatisticVo vo){
        if(vo == null){
            drivingFaceName = null;
            drivingMorningLength = null;
            drivingNoonLength = null;
            drivingEveningLength = null;
            drivingDayLength = null;
            drivingMonthLength = null;
        }else{
            drivingFaceName = vo.getName();
            drivingMorningLength = vo.getMorningLength();
            drivingNoonLength = vo.getNoonLength();
            drivingEveningLength = vo.getEveningLength();
            drivingDayLength = vo.getDayLength();
            drivingMonthLength = vo.getMonthLength();
        }

    }

    public void setDrillInfo(DrillStatisticVo vo){
        if(vo == null){
            drillWorkName = null;
            drillDayLength = null;
            drillMonthLength = null;
        }else{
            drillWorkName = vo.getName();
            drillDayLength = vo.getDayLength();
            drillMonthLength = vo.getMonthLength();
        }

    }

}
