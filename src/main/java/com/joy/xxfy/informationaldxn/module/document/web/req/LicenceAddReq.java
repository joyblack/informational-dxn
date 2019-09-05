package com.joy.xxfy.informationaldxn.module.document.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.LicenceTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class LicenceAddReq extends BaseAddReq {
    /**
     * 所属煤矿平台
     */
    @NotNull(message = "所属煤矿平台不能为空")
    private Long belongCompanyId;

    /**
     * 证件类型
     */
    @NotNull(message = "证件类型不能为空")
    private LicenceTypeEnum licenceType;

    /**
     * 证号
     */
    private String licenceNumber;

    /**
     * 有效期限
     */
    private Date expiryTime;


    /**
     * 颁证日期
     */
    private Date issueTime;


    /**
     * 有效期超时前提示天数，默认30天
     */
    private Long tipDays;

    /* 如下是采矿许可证多出来的属性 */
    /**
     * 矿区面积（km2）
     */
    private BigDecimal mineArea;

    /**
     * 矿井规模（万吨/年）
     */
    private BigDecimal mineScale;

    /**
     * 准采标高
     */
    private String mineElevation;

}
