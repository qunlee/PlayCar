package com.wk.libs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;

public class WKTimeUtils {

	static final String TAG = "TimeTools";

	/**
	 * 获取当前时间,刷新用
	 */
	@SuppressLint("SimpleDateFormat")
	public static String timeForListviewUpdate() {
		Date date = new Date();
		// SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String values = sDate.format(date);
		return values;
	}

	/*
	 * Long转换为时间格式
	 */
	public static CharSequence getTimeFromLong(Long time) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				"yyyy-MM-dd hh:MM:ss");
		String str = dateformat1.format(time);
		return str;
	}

	/*
	 * Long转换为时间格式
	 */
	public static CharSequence getTimeFromLong1(Long time) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat(
				" yyyy-MM-dd \n hh:MM:ss ");
		String str = dateformat1.format(time);
		return str;
	}

	/*
	 * Long转换为时间格式
	 */
	public static CharSequence getTimeFromLongEndWithMM(Long time) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd hh:MM");
		String str = dateformat1.format(time);
		return str;
	}

	/*
	 * Long转换为时间格式_MM-dd
	 */
	public static CharSequence getMonthFromLong(Long time) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("MM-dd");
		String str = dateformat1.format(time);
		return str;
	}

	/*
	 * Long转换为时间格式_MM-dd hh:MM
	 */
	public static CharSequence getMMddhhMMFromLong(Long time) {
		SimpleDateFormat dateformat1 = new SimpleDateFormat("MM-dd hh:MM");
		String str = dateformat1.format(time);
		return str;
	}

	/**
	 * 格式化时间
	 */
	public static CharSequence formatTime(String update_time) {
		try {
			long cur = System.currentTimeMillis();
			Long time = Long.valueOf(update_time);

			if (time * 10 < cur) {
				time *= 1000;
			}

			long d = (cur - time);
			if (d < 1000 * 10) {
				return "刚刚";
			} else if (d < 1000 * 60) {
				return (int) (d / (1000)) + "秒前";
			} else if (d < 1000 * 60 * 60) {// 小于一小时
				return (int) (d / (1000 * 60)) + "分钟前";
			} else if (d < 1000 * 60 * 60 * 24) { // 小于一天
				return (int) (d / (1000 * 60 * 60)) + "小时前";
			} else if (d < 1000 * 60 * 60 * 24 * 3) {
				return (int) (d / (1000 * 60 * 60 * 24)) + "天前";
			} else {
				SimpleDateFormat dateformat1 = new SimpleDateFormat(
						"yyyy-MM-dd");
				String str = dateformat1.format(time);
				return str;
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 
	 * @param context
	 * @param time
	 *            时间戳，以秒计
	 * @return
	 */
	private static String getTimeString(Context context, String time) {
		Date afterDate = new Date();
		// Log.i(TAG, "After time:" + afterDate.toGMTString());
		long beforeTime = Long.valueOf(time) * 1000L;
		Date beforeDate = new Date(beforeTime);
		// Log.i(TAG, "Before time:" + beforeDate.toGMTString());
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
		String values = sDate.format(beforeDate);
		if (afterDate.getYear() == beforeDate.getYear()) {
			int month = afterDate.getMonth() - beforeDate.getMonth();
			if (month > 3 || month < 0) {
				return values;
			} else {
				int day = afterDate.getDate() - beforeDate.getDate();
				if (month == 3) {
					if (day >= 0) {
						return "三个月前";
					} else {
						return "二个月前";
					}
				} else if (month == 2) {
					if (day >= 0) {
						return "二个月前";
					} else {
						return "一个月前";
					}
				} else if (month == 1) {
					if (day >= 0) {
						return "一个月前";
					} else {
						return getDateString(context, afterDate.getTime()
								- beforeTime);
					}
				} else if (month == 0) {
					return getDateString(context, afterDate.getTime()
							- beforeTime);
				}
			}
		}
		return values;
	}

	public static String getDateString(Context context, long time) {
		long days = time / (1000 * 60 * 60 * 24);
		if (days >= 7) {
			return days / 7 + "周前";
		} else {
			if (days > 0) {
				return days + "天前";
			} else {
				long hours = time / (1000 * 60 * 60);
				if (hours > 0) {
					return hours + "小时前";
				} else {
					long minutes = time / (1000 * 60);
					if (minutes > 0) {
						return minutes + "分钟前";
					} else {
						long seconds = time / 1000 - minutes * 60;
						if (seconds <= 0) {
							seconds = 10;
						}
						return seconds + "秒钟前";
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param context
	 * @param time
	 *            传入秒值
	 * @return
	 */
	public static String getTimeStr(Context context, String time) {
		Date afterDate = new Date();
		long beforeTime = Long.valueOf(time) * 1000L;
		Date beforeDate = new Date(beforeTime);
		if (beforeDate.getYear() == afterDate.getYear()) {
			if (beforeDate.getDate() == afterDate.getDate()
					&& beforeDate.getMonth() == afterDate.getMonth()) {
				if ((afterDate.getTime() - beforeTime) < 60 * 60 * 1000) {
					if ((afterDate.getTime() - beforeTime) < 60 * 1000) {
						int seconds = (int) ((afterDate.getTime() - beforeTime) / 1000);
						if (seconds <= 0) {
							seconds = 10;
						}
						return seconds + "秒钟前";
					} else {
						return (afterDate.getTime() - beforeTime) / (60 * 1000)
								+ "分钟前";
					}
				} else {
					SimpleDateFormat sDate = new SimpleDateFormat("HH:mm");
					String values = sDate.format(beforeDate);
					return "今天  " + values;
				}
			} else {
				SimpleDateFormat sDate = new SimpleDateFormat("MM-dd HH:mm");
				String values = sDate.format(beforeDate);
				return values;
			}
		} else {
			SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
			String values = sDate.format(beforeDate);
			return values;
		}
	}

	public static String getDate(String time) {
		long da = Long.valueOf(time) * 1000L;
		Date date = new Date(da);
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
		String values = sDate.format(date);
		return values;
	}

	public static String getDateAndTime(String time) throws Exception {
		long da = Long.valueOf(time) * 1000L;
		Date date = new Date(da);
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy年MM月dd日   hh:mm");
		String values = sDate.format(date);
		return values;
	}

	public static String getDateHanzi(String time) throws Exception {
		long da = Long.valueOf(time) * 1000L;
		Date date = new Date(da);
		SimpleDateFormat sDate = new SimpleDateFormat("MM月dd日");
		String values = sDate.format(date);
		return values;
	}

	public static String getDateDay(String time) throws Exception {
		long da = Long.valueOf(time) * 1000L;
		Date date = new Date(da);
		SimpleDateFormat sDate = new SimpleDateFormat("dd");
		String values = sDate.format(date);
		return values;
	}

	public static String getDateMonth(String time) throws Exception {
		long da = Long.valueOf(time) * 1000L;
		Date date = new Date(da);
		SimpleDateFormat sDate = new SimpleDateFormat("MM月");
		String values = sDate.format(date);
		return values;
	}

	/**
	 * 传入2个时间戳 计算相差天数 精确到秒
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static String getDateDays(String time1, String time2) {

		try {
			long day = 0;
			long da1 = Long.valueOf(time1);
			long da2 = Long.valueOf(time2);
			Date date1 = new Date(da1);
			Date date2 = new Date(da2);
			day = getGapCount(date1, date2);
			return day + "";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取两个日期之间的间隔天数
	 * 
	 * @return
	 */
	public static int getGapCount(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);
		return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            设置的需要判断的时间 //格式如2012-09-08
	 * 
	 * 
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */

	// String pTime = "2012-03-12";
	public static String getWeek(String pTime) {

		String Week = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd");
		Calendar c = Calendar.getInstance();
		try {

			c.setTime(format.parse(pTime));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "天";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}

		return Week;
	}

	/**
	 * 字符串转换成日期 yyyyMMdd
	 * 
	 * @param str
	 * @return date
	 */
	public static Date strToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

}
