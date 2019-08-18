package com.joy.xxfy.informationaldxn.common.web.req;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Sort.Direction;

/**
 * 分页列表基础参数
 */
@Data
@ToString
public class BaseAddReq {
    /**
     * 备注信息
     */
    private String remarks;
}
