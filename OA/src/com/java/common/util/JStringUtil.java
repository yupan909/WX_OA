package com.java.common.util;

import java.util.Calendar;
import java.util.UUID;

/**
 * 字符串工具类
 * @author Administrator
 *
 */
public class JStringUtil{
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str==null || "".equals(str) || "undefined".equals(str);
	}
	
	/**
	 * 随机生成32位uuid
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 获取当前日期 格式为（yyyy-MM-dd hh:mm:ss）
	 * @return
	 */
	public static String getNowDate(){
		 Calendar calendar = Calendar.getInstance();
	     String NowYear = Integer.toString(calendar.get(Calendar.YEAR));
	     String NowMonth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
	     String NowDay = Integer.toString(calendar.get(Calendar.DATE));
	     String NowHour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
	     String NowMinute = Integer.toString(calendar.get(Calendar.MINUTE));
	     String NowSecond = Integer.toString(calendar.get(Calendar.SECOND));
	     String NowDate = NowYear + "-" + ((NowMonth.length() == 1) ? "0" + NowMonth : NowMonth) + "-" + ((NowDay.length() == 1) ? "0" + NowDay : NowDay) + " " + ((NowHour.length() == 1) ? "0" + NowHour : NowHour) + ":" + ((NowMinute.length() == 1) ? "0" + NowMinute : NowMinute) + ":" + ((NowSecond.length() == 1) ? "0" + NowSecond : NowSecond);
	     return NowDate;
	}
	
	/**
	 * 获取当前日期 格式为（yyyy-MM-dd hh:mm:ss）
	 * @return
	 */
	public static String getNowDate2(){
		Calendar calendar = Calendar.getInstance();
		String NowYear = Integer.toString(calendar.get(Calendar.YEAR));
		String NowMonth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String NowDay = Integer.toString(calendar.get(Calendar.DATE));
		String week = "";
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		if(weekDay==Calendar.MONDAY){
			week = "星期一";
		}else if(weekDay==Calendar.TUESDAY){
			week = "星期二";
		}else if(weekDay==Calendar.WEDNESDAY){
			week = "星期三";
		}else if(weekDay==Calendar.THURSDAY){
			week = "星期四";
		}else if(weekDay==Calendar.FRIDAY){
			week = "星期五";
		}else if(weekDay==Calendar.SATURDAY){
			week = "星期六";
		}else if(weekDay==Calendar.SUNDAY){
			week = "星期日";
		}
		String NowDate = NowYear + "年" + NowMonth + "月" + NowDay + "日" + " " + week;
		return NowDate;
	}
	
}
