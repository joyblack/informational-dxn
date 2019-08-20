package com.joy.xxfy.informationaldxn.publish.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
/**
 * 日期操作工具类
 * @author cj(copy jobs)
 *
 */
public class DateOperationUtil {
	/**
	 * 追加指定的天数。
	 */
	public static Date addDay(final Date old,final int day)
	{
		//得到日历类的实例
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(old);
		//在当前日期上追加指定天数
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
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
	 * 追加指定的年数
	 */
	public static Date addYear(final Date old,final int year)
	{
		//得到日历类的实例
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(old);
		//在当前日期上追加指定天数
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}


	
	/**
	 * 功能介绍: 将日期格式化成指定的格式
	 */
	public static Date toDate(final String dt, String formatType)
	{
		//获得日期格式化实例
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
		try{
			//假如日期合法
			if(!StringUtil.isEmpty(dt))
			{
				return simpleDateFormat.parse(dt);
			}
		}
		catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	/**
	 * 将日期格式化为指定格式的形式
	 */
	public static String toDateString(final Date date,String formatType)
	{
		if(StringUtil.isEmpty(date.toString()))
		{
			return "";
		}
		//获得格式化格式
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println("当前时间: " + date.toString());
		System.out.println("添加5天后的时间：" + addDay(date, 5));
		System.out.println("添加6个月后的时间：" +  addMonth(date, 6));
		System.out.println("添加2年之后的时间" + addYear(date, 2));
	}
	
}