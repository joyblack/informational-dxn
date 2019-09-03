package com.joy.xxfy.informationaldxn.module.document.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.PermissionTypeEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.ReturnStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class BorrowGetListReq extends BasePageReq {
    /**
     * 资料名称
     */
    private String documentName;

    /**
     * 借阅人
     */
    private String borrowPeople;

    /**
     * 归还期限区间
     */
    private Date deadTimeStart;
    private Date deadTimeEnd;

    /**
     * 归还状态
     */
    private ReturnStatusEnum returnStatus;


    /**
     * 超时归还
     */
    private CommonYesEnum isOverTime;

}
