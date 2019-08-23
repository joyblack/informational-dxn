package com.joy.xxfy.informationaldxn.module.backmining.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import com.joy.xxfy.informationaldxn.module.backmining.domain.enums.BackMiningModeEnum;
import com.joy.xxfy.informationaldxn.module.backmining.domain.enums.VentilationModeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(callSuper = true)
public class BackMiningFaceGetListReq extends BasePageReq {

    /**
     * 名称
     */
    private String backMiningFaceName;

    /**
     * 开采日期起止
     */
    private Date startTimeStart;
    private Date startTimeEnd;

    /**
     * 通风方式
     */
    private VentilationModeEnum ventilationMode;

    /**
     * 回采方式
     */
    private BackMiningModeEnum backMiningMode;

}
