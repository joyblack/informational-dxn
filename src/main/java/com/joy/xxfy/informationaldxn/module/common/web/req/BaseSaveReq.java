package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;

/**
 * 添加、修改、删除批量一体Base
 */
@Data
@ToString
public class BaseSaveReq {
    /**
     * id
     */
    private Long id;
    /**
     * 备注信息
     */
    private String remarks;
}
