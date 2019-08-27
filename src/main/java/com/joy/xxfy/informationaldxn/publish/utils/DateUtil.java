package com.joy.xxfy.informationaldxn.publish.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 获取指定时间所在月份的第一天
     */
    public static Date getMonthFirstDay(Date time){
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.setTime(time);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }
}
