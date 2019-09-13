package com.joy.xxfy.informationaldxn.publish.utils;

import java.math.BigDecimal;

public class TransferValueUtil {
    /**
     * 若为空，返回默认值，否则返回源数据
     */
    public static Long get(Long value, Long defaultValue){
        if(value == null){
            return defaultValue;
        }else{
            return value;
        }
    }

    /**
     * 若为空，返回0，否则返回源数据
     */
    public static Long get(Long value){
        if(value == null){
            return 0L;
        }else{
            return value;
        }
    }

    /**
     * 若为空，返回默认值，否则返回源数据
     */
    public static BigDecimal get(BigDecimal value, BigDecimal defaultValue){
        if(value == null){
            return defaultValue;
        }else{
            return value;
        }
    }

    /**
     * 若为空，返回0，否则返回原数据
     */
    public static BigDecimal get(BigDecimal value){
        if(value == null){
            return BigDecimal.ZERO;
        }else{
            return value;
        }
    }
}
