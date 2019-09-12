package com.joy.xxfy.informationaldxn.module.common.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DateVo {
    private Date date;

    public DateVo(Date date) {
        this.date = date;
    }
}
