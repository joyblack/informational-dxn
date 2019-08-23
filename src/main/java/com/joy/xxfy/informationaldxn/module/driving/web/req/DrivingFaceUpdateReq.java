package com.joy.xxfy.informationaldxn.module.driving.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.CrossSectionTypeEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.DrivingTechnologyTypeEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.RockCharacterEnum;
import com.joy.xxfy.informationaldxn.module.driving.domain.enums.SupportMethodEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class DrivingFaceUpdateReq extends BaseUpdateReq {
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    private String drivingFaceName;


    /**
     * 设计长度(总长度)
     */
    @NotNull(message = "设计长度不能为空")
    private BigDecimal totalLength;


    /**
     * 已掘长度
     */
    @NotNull(message = "已掘长度不能为空")
    private BigDecimal doneLength;

    /**
     * 剩余长度(自动计算)
     */
    private BigDecimal leftLength;

    /**
     * 开掘日期
     */
    private Date startTime;


    /**
     * 掘进高度
     */
    private BigDecimal drivingHigh;

    /**
     * 掘进坡度
     */
    private BigDecimal drivingSlope;


    /**
     * 断面(m2)
     */
    private BigDecimal crossSection;

    /**
     * 断面形式
     */
    private CrossSectionTypeEnum crossSectionType;


    /**
     * 煤层厚度
     */
    private BigDecimal coalSeamThickness;


    /**
     * 掘进工艺
     */
    private DrivingTechnologyTypeEnum drivingTechnologyType;

    /**
     * 支护方式
     */
    private SupportMethodEnum supportMethod;

    /**
     * 岩性
     */
    private RockCharacterEnum rockCharacter;

}
