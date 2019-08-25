package com.joy.xxfy.informationaldxn.module.safe.web.req;

import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
public class ChangeRectificationStatusReq {
    /**
     * 整改记录的ID列表
     */
    @NotEmpty(message = "整改记录信息不能为空")
    private List<Long> ids;

    /**
     * 整改状态
     */
    @NotNull(message = "整改状态不能为空")
    private RectificationStatusEnum rectificationStatus;

    /**
     * 整改人
     */
    @NotBlank(message = "整改人不能为空")
    private String rectificationPeople;

    /**
     * 备注信息
     */
    private String remarks;
}
