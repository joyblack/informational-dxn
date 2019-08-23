package com.joy.xxfy.informationaldxn.publish.utils;

import java.math.BigDecimal;

public class ComputeUtils {
    // 加法
    public static BigDecimal add(BigDecimal a, BigDecimal b){
        return a.add(b);
    }

    // 减法
    public static BigDecimal sub(BigDecimal a, BigDecimal b){
        return a.subtract(b);
    }

    // a是否等于
    public static boolean equal(BigDecimal a, BigDecimal b){
        return a.compareTo(b) == 0;
    }

    // a是否小于
    public static boolean less(BigDecimal a, BigDecimal b){
        return a.compareTo(b) == -1;
    }

    // a是否小于等于
    public static boolean lessThan(BigDecimal a, BigDecimal b){
        return a.compareTo(b) != 1;
    }

    // 大于
    public static boolean more(BigDecimal a, BigDecimal b){
        return a.compareTo(b) == 1;
    }

    // 大于等于
    public static boolean moreThan(BigDecimal a, BigDecimal b){
        return a.compareTo(b) == -1;
    }

    // 比较大小：
    // 1 >
    // 0 =
    // -1 <
    public static int compare(BigDecimal a, BigDecimal b){
        return a.compareTo(b);
    }



    public static void main(String[] args) {
        System.out.println(add(BigDecimal.valueOf(1),BigDecimal.valueOf(-2)));
    }
}
