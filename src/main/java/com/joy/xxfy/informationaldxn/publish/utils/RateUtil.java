package com.joy.xxfy.informationaldxn.publish.utils;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 计算比率工具
 */
public class RateUtil {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    // 百分
    private static final NumberFormat nf;

    static {
        nf = NumberFormat.getPercentInstance();
        //nf.setMaximumFractionDigits(2);
    }

    public static String compute(Long var1, Long val2, boolean withPercent){
        double result = var1.doubleValue() / val2.doubleValue();
        if(withPercent){
            return nf.format(result);
        }else{
            return df.format(result);
        }
    }

    public static String compute(BigDecimal var1, BigDecimal val2, boolean withPercent){
        BigDecimal result = var1.divide(val2, RoundingMode.HALF_UP);
        System.out.println(result);
        if(withPercent){
            return nf.format(result);
        }else{
            return df.format(result);
        }
    }

    public static void main(String[] args) {
        System.out.println(compute(3L,10L, true));
    }
}
