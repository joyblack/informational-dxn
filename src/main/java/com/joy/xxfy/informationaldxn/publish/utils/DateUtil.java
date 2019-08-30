package com.joy.xxfy.informationaldxn.publish.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 获取指定时间月初
     */
    public static Date getMonthFirstDay(Date time){
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.setTime(time);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }

    /**
     * 获取指定时间月末
     */
    public static Date getMonthLastDay(Date time)
    {
        Calendar cale = Calendar.getInstance();
        cale.setTime(time);
        //获取某月最大天数
        //设置日历中月份的最大天数
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cale.getTime();
    }

    /**
     * 指定日期加上天数后的日期
     */
    public static Date addDay(Date currentTime, int days){
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentTime);
        ca.add(Calendar.DATE, days);
        return ca.getTime();
    }

    /**
     * 比较日期大小，只考虑年月日
     */
    public static int compare(Date date1, Date date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateFirst = dateFormat.format(date1);
        String dateLast = dateFormat.format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal > dateLastIntVal) {
            return 1;
        } else if (dateFirstIntVal < dateLastIntVal) {
            return -1;
        }
        return 0;
    }

    /**
     * 获得指定日期的零点时间
     */
    public static Date getDateJustYMD(Date currentTime){
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentTime);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return ca.getTime();
    }
}
