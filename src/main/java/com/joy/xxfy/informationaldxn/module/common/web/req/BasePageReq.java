package com.joy.xxfy.informationaldxn.module.common.web.req;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.Sort.Direction;

/**
 * 分页列表基础参数
 */
@Data
@ToString
public class BasePageReq {
    private int page = 1;

    private int size = 10;
    /**
     * 模糊搜索字段
     */
    private String search;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方向
     */
    private Direction order = Direction.ASC;



}
