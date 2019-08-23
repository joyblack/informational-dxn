package com.joy.xxfy.informationaldxn.module.staff.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class StaffBlacklistUpdateReq extends BaseUpdateReq {
    /**
     * 工伤原因
     */
    @Lob
    @NotBlank(message = "原因不能为空")
    private String blacklistReasons;

    /**
     * 自动解禁日期
     */
    private Date overTime;

}
