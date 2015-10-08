package com.playcar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;

public class Tools {
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		Activity activity = (Activity) context;
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;//宽度height = dm.heightPixels ;//高度
	}

	/**
	 * 检测是否 存在SD卡
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测网络是否存在
	 * 
	 * @return
	 */
	public static boolean checkNetwork(final Activity mActivity) {
		ConnectivityManager conn = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conn.getActiveNetworkInfo();
		if (net != null && net.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 将String类型的日期转换成calendar
	 * 
	 * @param str
	 * @return
	 */
	public static Calendar stringToCalendar(String str) {
		Calendar calendar = new GregorianCalendar(Locale.CHINA);
		try {
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}

	/**
	 * 写文件到 SD卡
	 */
	public static void writeFileSdcard(String fileName, String message) {
		if (message.equals("[]")) {
			message = "";
		}
		try {
			// FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
			FileOutputStream fout = new FileOutputStream(fileName);
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从SD卡读取信息
	 */
	public static String readFileSdcard(String fileName) {
		String res = "";
		File file = new File(fileName);
		if (file.exists()) {
			try {
				FileInputStream fin = new FileInputStream(fileName);
				int length = fin.available();
				byte[] buffer = new byte[length];
				fin.read(buffer);
				res = EncodingUtils.getString(buffer, "UTF-8");
				fin.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.equals("")) {
				res = " ";
			}
		} else {
			res = " ";
		}
		return res;
	}

	/**
	 * 判断String 是否有内容
	 * 
	 * @param str
	 * @return
	 */
	public static boolean StringHasContent(String str) {
		if (null == str || "".equals(str) || "null".equals(str) || str.length() == 0) {
			return false;
		}
		return true;
	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		if (context == null) {
			return (int) dpValue;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		if (context == null) {
			return (int) pxValue;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		if (context == null) {
			return (int) pxValue;
		}
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		if (context == null) {
			return (int) spValue;
		}
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	public static String getCurrentDate(String pattern){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

}
