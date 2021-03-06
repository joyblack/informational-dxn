package com.joy.xxfy.informationaldxn.module.drill.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.validate.annotates.Angle;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
public class DrillHoleAddReq extends BaseAddReq {
    /**
     * 关联的钻孔工作
     */
    @NotNull(message = "钻孔工作ID不能为空")
    private Long drillWorkId;

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
    @Angle(message = "倾角不合法，请设置为-360~360之间的值.")
    private BigDecimal dipAngle;

    /**
     * 夹角
     */
    @Angle(message = "夹角不合法，请设置为-360~360之间的值.")
    private BigDecimal intersectionAngle;

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
