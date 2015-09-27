package com.wk.libs.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.playcar.WKApplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WKUtils {

	/**
	 * 判断是否有汉字编码
	 */
	static String regEx = "[\u4e00-\u9fa5]";
	static Pattern pat = Pattern.compile(regEx);

	public static boolean isContainsChinese(String str) {
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find()) {
			flg = true;
		}
		return flg;
	}
	
	/***
	 * 判断当前网络是否连接
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return false;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return false;
		}
		if (netinfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * wifi是否OK
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}

		return false;
	}

	/**
	 * 跳转到拨打电话界面
	 * 
	 * @param ctx
	 * @param phoneNo
	 */
	public static void call(Context ctx, String phoneNo) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ phoneNo));
		ctx.startActivity(intent);
	}

	/**
	 * 直接拨打
	 * 
	 * @param ctx
	 * @param phoneNo
	 */
	public static void doCall(Context ctx, String phoneNo) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ phoneNo));
		ctx.startActivity(intent);
	}

	/**
	 * 是否为电话号
	 */
	public static boolean isTel(String tel) {
		Pattern pattern = Pattern.compile("^1\\d{10}$");
		Matcher matcher = pattern.matcher(tel);
		return matcher.matches();
	}

	/**
	 * 验证输入密码条件(字符与数据同时出现)
	 */
	public static boolean isPassword(String str) {
		String regex = "^[a-zA-Z0-9]{6,16}$";
		return match(regex, str);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isEmulator(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (imei != null && imei.equals("000000000000000")) {
				return true;
			}
			return (Build.MODEL.equals("sdk"))
					|| (Build.MODEL.equals("google_sdk"));
		} catch (Exception ioe) {
		}
		return false;
	}

	/**
	 * 获取当前应用程序的版本
	 */
	public static String getVersionName(Context ctx) {
		PackageManager pm = ctx.getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(ctx.getPackageName(), 0);
			return packinfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取当前应用程序的版本号
	 */
	public static int getVersionCode(Context ctx) {
		PackageManager pm = ctx.getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(ctx.getPackageName(), 0);
			return packinfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 验证邮政编码
	public static boolean checkPost(String post) {
		if (post.matches("^[1-9]\\d{5}(?!\\d)$")) {
			return true;
		} else {
			return false;
		}
	}

	// 加载HTML格式的字符串
	public static void initWebView(Context ctx, WebView webView, String htmlStr) {
		webView.setHorizontalScrollBarEnabled(false);//水平不显示
		webView.setVerticalScrollBarEnabled(false); //垂直不显示
		WebSettings setting = webView.getSettings();
		// 自适应屏幕
		setting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		setting.setLoadWithOverviewMode(true);

		// 设置 缓存模式
		setting.setCacheMode(WebSettings.LOAD_DEFAULT);
		// 开启 DOM storage API 功能
		setting.setDomStorageEnabled(true);
		// 开启 database storage API 功能
		setting.setDatabaseEnabled(true);
		String APP_CACAHE_DIRNAME = "/webcache";
		String cacheDirPath = ctx.getFilesDir().getAbsolutePath()
				+ APP_CACAHE_DIRNAME;
		Log.i("TAG", "cacheDirPath=" + cacheDirPath);
		// 设置数据库缓存路径
		setting.setDatabasePath(cacheDirPath);
		// 设置 Application Caches 缓存目录
		setting.setAppCachePath(cacheDirPath);
		// 开启 Application Caches 功能
		setting.setAppCacheEnabled(true);
		
		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed();// 设置证书
			}
		});
		webView.loadDataWithBaseURL(null, htmlStr, "text/html", "utf-8", null);
	}

	/**
	 * 播放视频
	 */
	public static void playVideoCheck(final Activity ctx,
			final String videoPath, final String noWifiTip) {
		if (isNetworkAvailable(ctx) == false) {
			WKApplication.showToast("请检查下网络");
			return;
		}
		if (isWifiConnected(ctx) == false) {// nowifi
			WKApplication.showMsg(noWifiTip, "确定", new OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					playVideo(ctx, videoPath);
				}
			}, "返回", new OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {

				}
			});
		} else {
			playVideo(ctx, videoPath);
		}
	}

	public static final int WKPLayVedioBack = 10086;

	/**
	 * 播放视频
	 */
	public static void playVideo(Activity ctx, String videoPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String strend = "";
		if (videoPath.toLowerCase().endsWith(".mp4")) {
			strend = "mp4";
		} else if (videoPath.toLowerCase().endsWith(".3gp")) {
			strend = "3gp";
		} else if (videoPath.toLowerCase().endsWith(".mov")) {
			strend = "mov";
		} else if (videoPath.toLowerCase().endsWith(".wmv")) {
			strend = "wmv";
		}

		intent.setDataAndType(Uri.parse(videoPath), "video/" + strend);
		ctx.startActivity(intent);
		ctx.startActivityForResult(intent, WKPLayVedioBack);
	}

}
