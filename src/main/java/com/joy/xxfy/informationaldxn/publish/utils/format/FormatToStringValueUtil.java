package com.joy.xxfy.informationaldxn.publish.utils.format;

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
}
