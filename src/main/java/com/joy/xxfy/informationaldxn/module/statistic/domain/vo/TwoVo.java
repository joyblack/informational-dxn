package com.joy.xxfy.informationaldxn.module.statistic.domain.vo;

import lombok.Data;

@Data
public class TwoVo <T,V>{
    private T va1;
    private T va2;

    public TwoVo(T va1, T va2) {
        this.va1 = va1;
        this.va2 = va2;
    }

    public TwoVo() {
    }
}
