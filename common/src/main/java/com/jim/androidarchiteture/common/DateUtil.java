package com.jim.androidarchiteture.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static long ONE_DAY = 86400000;////24*60*60*1000=86400000 

	public static String toHttpDateString(Date date){
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
		return format.format(date);
	}

	public static String toLongDateString(Date date){
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATETIME, Locale.US);
		return format.format(date);
	}
	
	public static String toShortDateString(Date date){
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE, Locale.US);
		return format.format(date);
	}	
	
	public static String format(Date date,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(date);
	}

	public static Date parseLongDate(String date){
		return parse(date, FORMAT_DATETIME);
	}
	
	public static Date parse(String date,String format){
		if (null == date) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			if (!FORMAT_DATE.equals(format))
				return parse(date,FORMAT_DATE);
			else
				return null;
		}
	}
	
	public static int dayBetween(Date olddate,Date newdate){
		return (int) ((newdate.getTime() - olddate.getTime())/(24*3600000));
	}
	
	public static Date removeTime(Date date,int dateAdd){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,dateAdd);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
}
