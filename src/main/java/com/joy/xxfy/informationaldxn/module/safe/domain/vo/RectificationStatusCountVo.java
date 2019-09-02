package com.joy.xxfy.informationaldxn.module.safe.domain.vo;

import com.joy.xxfy.informationaldxn.module.safe.domain.enums.RectificationStatusEnum;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RectificationStatusCountVo {
    public RectificationStatusEnum rectificationStatusEnum;
    public Long ct;

    public RectificationStatusCountVo(RectificationStatusEnum rectificationStatusEnum, Long ct) {
        this.rectificationStatusEnum = rectificationStatusEnum;
        this.ct = ct;
    }


}
