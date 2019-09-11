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
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        ca.set(Calendar.DAY_OF_MONTH,1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }

    /**
     * 获取指定时间月末
     */
    public static Date getMonthLastDay(Date time)
    {
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        //获取某月最大天数: 设置日历中月份的最大天数
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
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
     * 追加指定的月数
     */
    public static Date addMonth(final Date old,final int month)
    {
        //得到日历类的实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(old);
        //在当前日期上追加指定天数
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
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
        // 一定要将微妙置为0
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }

    /**
     * 获得当前日期的零点时间
     */
    public static Date getDateJustYMD(){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        // 一定要将微妙置为0
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }

    /**
     * 获得当前日期的零点时间
     */
    public static Date now(){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        // 一定要将微妙置为0
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }

    /**
     * 获得指定日期年初时间
     */
    public static Date getDateYearStart(Date currentTime){
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentTime);
        ca.set(Calendar.MONTH, 0);
        ca.set(Calendar.DAY_OF_MONTH,1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }

    /**
     * 获得指定日期的年尾时间
     */
    public static Date getDateYearEnd(Date currentTime){
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentTime);
        ca.set(Calendar.MONTH, 11);
        ca.set(Calendar.DAY_OF_MONTH, 31);
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }

    /**
     * 获得指定日期的年月:yyyyMM
     * fitZero：月份是否自动补0
     */
    public static String getYMString(Date currentTime,boolean fitZero){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        if(!fitZero){
            dateFormat = new SimpleDateFormat("yyyyM");
        }
        return dateFormat.format(currentTime);
    }

    /**
     * 获取指定日期的月日
     * fitZero：月份、日期是否自动补0
     */
    public static String getMDString(Date currentTime,boolean fitZero){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        if(!fitZero){
            dateFormat = new SimpleDateFormat("M-d");
        }
        return dateFormat.format(currentTime);
    }

    /**
     * 获得指定日期的年份
     */
    public static String getYearString(Date time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.format(time);
    }



    /**
     * 指定年月日获取时间
     */
    public static Date getDate(int year, int month, int day){
        Calendar ca = Calendar.getInstance();
        ca.set(year,month -1, day,0,0,0);
        // 不要微秒时间
        ca.set(Calendar.MILLISECOND,0);
        return ca.getTime();
    }


    public static void main(String[] args) {
        System.out.println(getDateYearStart(new Date()));
    }

}
