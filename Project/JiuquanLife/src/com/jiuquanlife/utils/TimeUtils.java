package com.jiuquanlife.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.Time;

public class TimeUtils {

	public static String getFormatedCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		String dateTime = ""
				+ calendar.get(Calendar.YEAR)
				+ "-"
				+ ((calendar.get(Calendar.MONTH) + 1) < 10 ? "0"
						+ (calendar.get(Calendar.MONTH) + 1) : (calendar
						.get(Calendar.MONTH) + 1))
				+ "-"
				+ (calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0"
						+ calendar.get(Calendar.DAY_OF_MONTH) : calendar
						.get(Calendar.DAY_OF_MONTH));
		return dateTime;
	}

	public static String convertToFormatedDate(int year, int monthOfYear,
			int dayOfMonth) {

		String dateTime = year
				+ "-"
				+ ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1)
						: (monthOfYear + 1)) + "-"
				+ (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
		return dateTime;
	}

	public static String convertToFormatedTime(int hour, int minute) {

		return (hour < 10 ? "0" + hour : hour) + ":"
				+ (minute < 10 ? "0" + minute : minute);
	}

	public static String getCurrentTime() {

		Time time = new Time();
		time.setToNow();
		return time.format("%H:%M");
	}
	
	public static String getFormatDate(Calendar c) {
		
		Time time = new Time();
		time.set(c.getTimeInMillis());
		return time.format("%Y-%m-%d"); 
	}
	
	public static String getFormatedDateTime(Calendar c) {
		
		Time time = new Time();
		time.set(c.getTimeInMillis());
		return time.format("%Y-%m-%d %H:%M:%S"); 
	}
	
	public static String getCurrentFormatedDateTime() {

		Time time = new Time();
		time.setToNow();
		return time.format("%Y-%m-%d %H:%M:%S");
	}

	public static String getCurrentFormatedDate() {

		Time time = new Time();
		time.setToNow();
		return time.format("%Y-%m-%d");
	}

	public static boolean isToday(String dateStr) {

		if (!StringUtils.isNullOrEmpty(dateStr)) {
			Date date = getDateFromString(dateStr);
			if (date != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				Calendar now = Calendar.getInstance();
				if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
						&& c.get(Calendar.DAY_OF_YEAR) == now
								.get(Calendar.DAY_OF_YEAR)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Date转String
	 * 
	 * @param count
	 * @return
	 */
	public static Date getDateFromString(String date) {

		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = formatDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	public static Date getDateFromDateString(String date) {

		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date time = null;
		try {
			time = formatDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String getTimeOfDateString(String date) {
		return date.substring(10, date.length() - 3);
	}

	/**
	 * 
	 * @param one
	 * @param another
	 * @return ture if one and another in the same day
	 */
	public static boolean isInSameDay(long one, long another) {

		Calendar oneC = Calendar.getInstance();
		oneC.setTimeInMillis(one);
		Calendar anotherC = Calendar.getInstance();
		anotherC.setTimeInMillis(another);
		if (oneC.get(Calendar.YEAR) == anotherC.get(Calendar.YEAR)
				&& oneC.get(Calendar.DAY_OF_YEAR) == anotherC
						.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if today is work day
	 */
	public static boolean isWorkDay() {

		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
			return false;
		}
		return true;
	}

	public static String getCurrentMonthDate() {

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		return year + "-" + month;
	}

	/**
	 * 
	 * @param workDay
	 *            For example "1,2,3,4,5" means Monday to Friday
	 * @return
	 */
	public static boolean isWorkDay(String workDay) {

		if (!StringUtils.isNullOrEmpty(workDay)) {
			String[] days = workDay.split(",");
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			if (days != null) {
				for (String temp : days) {
					if (!StringUtils.isNullOrEmpty(temp)) {
						int day = Integer.parseInt(temp) + 1;
						if (day == dayOfWeek) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isEarlierThanToday(String dateStr){
		
		Date date = getDateFromDateString(dateStr);
		Date today = getDateFromDateString(getCurrentFormatedDate());
		return date.before(today);
	}
	
}
