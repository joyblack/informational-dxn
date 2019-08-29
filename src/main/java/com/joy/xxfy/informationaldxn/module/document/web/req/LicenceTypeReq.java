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
public class LicenceTypeReq extends BaseAddReq {

    /**
     * 证件类型
     */
    @NotNull(message = "证件类型不能为空")
    private LicenceTypeEnum licenceType;

}
