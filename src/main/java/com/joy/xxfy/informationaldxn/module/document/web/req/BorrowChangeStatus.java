package com.joy.xxfy.informationaldxn.module.document.web.req;

import com.joy.xxfy.informationaldxn.module.common.enums.CommonStatusEnum;
import com.joy.xxfy.informationaldxn.module.common.enums.CommonYesEnum;
import com.joy.xxfy.informationaldxn.module.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationaldxn.module.device.domain.enums.DeviceStatusEnum;
import com.joy.xxfy.informationaldxn.module.document.domain.enums.ReturnStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
public class BorrowChangeStatus {
    /**
     * ID列表
     */
    @NotEmpty(message = "借阅记录不能为空")
    private List<Long> ids;

    /**
     * 归还状态
     */
    @NotNull(message = "归还状态不能为空")
    private ReturnStatusEnum returnStatus;
}
