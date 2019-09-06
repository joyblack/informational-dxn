package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.backmining.domain.enums.BackMiningModeEnum;
import com.joy.xxfy.informationaldxn.module.backmining.domain.enums.VentilationModeEnum;
import com.joy.xxfy.informationaldxn.validate.annotates.Angle;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 回采工作面
 */
@Data
@ToString(callSuper = true)
public class BackMiningFaceAddReq extends BaseAddReq {
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String backMiningFaceName;

    /**
     * 采面斜长
     */
    @NotNull(message = "采面斜长不能为空")
    @Min(value = 0,message = "采面斜长不能小于0")
    private BigDecimal slopeLength;

    /**
     * 回风顺槽长度
     */
    @NotNull(message = "回风顺槽不能为空")
    @Min(value = 0,message = "回风顺槽不能小于0")
    private BigDecimal returnAirChute;

    /**
     * 运输顺槽
     */
    @NotNull(message = "运输顺槽不能为空")
    @Min(value = 0,message = "运输顺槽不能小于0")
    private BigDecimal transportChute;


    /**
     * 采面走向长度
     */
    @Min(value = 0,message = "采面走向长度不能小于0")
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
    @Angle(message = "煤层倾角不合法，请设置为-360~360之间的值.")
    private BigDecimal coalSeamDipAngle;

    /**
     * 采高
     */
    @Min(value = 0,message = "采高不能小于0")
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
    @Min(value = 0,message = "可采储量不能小于0")
    private BigDecimal recoverReserves;
}
