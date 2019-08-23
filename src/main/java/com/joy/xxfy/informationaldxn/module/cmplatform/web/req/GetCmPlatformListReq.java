package com.joy.xxfy.informationaldxn.module.cmplatform.web.req;

import com.joy.xxfy.informationaldxn.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GetCmPlatformListReq extends BasePageReq {
    /**
     * 登陆名称模糊查询
     */
    private String loginName;

    /**
     * 煤矿名称模糊查询
     */
    private String cmName;
}
