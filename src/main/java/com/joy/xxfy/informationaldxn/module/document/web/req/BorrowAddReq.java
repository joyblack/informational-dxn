package com.joy.xxfy.informationaldxn.module.document.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class BorrowAddReq extends BaseAddReq {

    /**
     * 资料名称
     */
    @NotBlank(message = "资料名称不能为空")
    private String documentName;

    /**
     * 借阅人
     */
    @NotBlank(message = "借阅人不能为空")
    private String borrowPeople;

    /**
     * 归还期限
     */
    @NotNull(message = "归还期限不能为空")
    private Date deadTime;

}
