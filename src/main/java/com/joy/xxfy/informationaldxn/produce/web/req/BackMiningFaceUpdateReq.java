package com.joy.xxfy.informationaldxn.produce.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationaldxn.produce.domain.enums.BackMiningModeEnum;
import com.joy.xxfy.informationaldxn.produce.domain.enums.VentilationModeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 回采工作面
 */
@Data
@ToString(callSuper = true)
public class BackMiningFaceUpdateReq extends BaseUpdateReq {
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String backMiningFaceName;

    /**
     * 采面斜长
     */
    @NotNull(message = "采面斜长不能为空")
    private BigDecimal slopeLength;

    /**
     * 回风顺槽长度
     */
    @NotNull(message = "回风顺槽不能为空")
    private BigDecimal returnAirChute;

    /**
     * 运输顺槽
     */
    @NotNull(message = "运输顺槽不能为空")
    private BigDecimal transportChute;


    /**
     * 已采长度
     */
    @NotNull(message = "已采长度不能为空")
    private BigDecimal doneLength;

    /**
     * 采面走向长度
     */
    private BigDecimal trendLength;

    /**
     * 开采日期
     */
    private Date startTime;

    /**
     * 煤层厚度
     */
    private BigDecimal coalSeamThickness;

    /**
     * 煤层倾角
     */
    private BigDecimal coalSeamDipAngle;

    /**
     * 采高
     */
    private BigDecimal miningHigh;


    /**
     * 通风方式
     */
    private VentilationModeEnum ventilationMode;

    /***
     * 回采方式
     */
    private BackMiningModeEnum backMiningMode;

    /**
     * 可采储量(t)
     */
    private BigDecimal recoverReserves;
}
