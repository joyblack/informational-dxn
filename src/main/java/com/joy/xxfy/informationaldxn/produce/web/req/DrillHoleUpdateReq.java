package com.joy.xxfy.informationaldxn.produce.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class DrillHoleUpdateReq extends BaseUpdateReq {

    /**
     * 钻孔序号
     */
    @NotNull(message = "钻孔序号不能为空")
    private Long orderNumber;

    /**
     * 编号
     */
    @NotBlank(message = "钻孔编号不能为空")
    private String code;

    /**
     * 钻孔设计长度
     */
    @NotNull(message = "钻孔设计长度不能为空")
    private BigDecimal totalLength;


    /**
     * 倾角
     */
    private BigDecimal dipAngle;

    /**
     * 预计见煤
     */
    private BigDecimal predicateAppearCoal;

    /**
     * 预计止煤
     */
    private BigDecimal predicateDisappearCoal;

    /**
     * 预计煤厚
     */
    private BigDecimal predicateCoalThickness;




}
