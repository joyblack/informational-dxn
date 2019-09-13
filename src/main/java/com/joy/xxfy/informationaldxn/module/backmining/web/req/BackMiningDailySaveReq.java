package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
public class BackMiningDailySaveReq extends BaseAddReq {
    /**
     * 关联的工作面信息
     */
    @NotNull(message = "工作面信息不能为空")
    private Long backMiningFaceId;

    /**
     * 日期
     */
    @NotNull(message = "日期不能为空")
    private Date dailyTime;


    @Valid
    @NotNull
    private List<BackMiningDailySaveItem> items;

}
