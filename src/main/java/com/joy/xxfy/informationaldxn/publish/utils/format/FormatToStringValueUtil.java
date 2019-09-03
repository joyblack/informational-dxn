package com.joy.xxfy.informationaldxn.publish.utils.format;

import java.text.NumberFormat;

/**
 * 返回String类型的操作
 */
public class FormatToStringValueUtil {
    /**
     * 数值类型的格式化
     * @return
     */
    public static<T> String numberFormat(T number){
        if(number == null){
            return "";
        }else{
            return number.toString();
        }
    }

    /**
     * 给数字补前置0，如月份
     */
    public static String addLeftZero(int num){
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(2);
        formatter.setGroupingUsed(false);
        return formatter.format(num);
    }

    public static void main(String[] args) {
        System.out.println(addLeftZero(1));
    }


}
