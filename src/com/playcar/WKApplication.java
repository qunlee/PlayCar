package com.playcar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.itotem.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.itotem.imageloader.core.DisplayImageOptions;
import com.itotem.imageloader.core.ImageLoader;
import com.itotem.imageloader.core.ImageLoaderConfiguration;
import com.itotem.imageloader.core.assist.ImageScaleType;
import com.playcar.R;
import com.wk.libs.WKCrashHandler;
import com.wk.libs.beans.LogInfoGsonBean;
import com.wk.libs.beans.UserInfoGsonBean;
import com.wk.libs.utils.T;
import com.wk.libs.utils.WKHImageLoader;
import com.wk.libs.utils.WKSharedPreferencesUtil;
import com.wk.libs.utils.WKTakePhotoUtils;

/**
 * WKApplication，集成基本的功能
 */
public class WKApplication extends Application {

	/*****************************************************************
	 * 需要配置
	 ****************************************************************/

	public static String BASE_URL = "";
	public static boolean isSetCrashHandler = false;// 是否设置异常捕获，不设置为了调试方便
	public static boolean isHttps = false;// 是否使用https链接
	public static boolean isSaveHttpData = true;// 是否保存http数据
	public static boolean isOpenLocalCache = true;// 是否打开本地缓存
	public static boolean showGuidePageAllTime = false;// 是否一直显示引导页
	public static boolean isTestServer = false;// 是否连接测试服务器
	public static final boolean SHOW_NET_LOG = true;// 是否显示网络相关调试
	public static boolean isAutoLogin = false;// 是否自动填上账户
	public static boolean isOpenPush = false;// 是否打开推送
	public static boolean isOpenCookie4Http = false;// 是否打开cookie
	public static int listUtilsCurIndex = 0;
	public static boolean isShowCookie = false;// 是否打印cookie
	public static boolean isOnlySavePostCookie = false;// 是否只保存post的cookie

	/*****************************************************************
	 * 以上需要配置
	 ****************************************************************/

	public static String NO_NET_TIP = "服务器开小差儿了～";

	// 缓存key
	public String cacheKey = "";

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String s) {
		this.cacheKey = s;
	}

	/**
	 * 用户信息
	 */
	public UserInfoGsonBean user = null;
	public LogInfoGsonBean loginfo = null;

	/**
	 * 拍照工具类
	 */
	public WKTakePhotoUtils takePhoto = null;

	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;
	public static Activity curAct = null;
	public WKSharedPreferencesUtil sp = null;
	public List<Activity> mList = new LinkedList<Activity>();
	public WKHImageLoader imageLoader = null;
	public DisplayImageOptions options;
	public DisplayImageOptions options2; // 不带缓存的配置

	private DisplayMetrics dm = null;
	private InputMethodManager imm = null;
	private boolean isInitOK = false;

	public static String SD_PATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	public static final String DCIM = SD_PATH + "/DCIM/Camera/";
	public static final String APP_NAME = "jifentong";
	public static String IMAGE_SD_CACHE_PATH = SD_PATH + "/" + APP_NAME
			+ "/image_cache/";

	/**
	 * 城市相关，name从百度地图获取，id从服务器城市列表里获取
	 */
	public static int city_id = 0;
	public static String city_name = "";

	@Override
	public void onCreate() {
		super.onCreate();

		if (isTestServer) {
			BASE_URL = "https://www.jft365.cn:9876/AppService/";
		} else {
			BASE_URL = "https://www.jft365.cn/AppService/";
		}

		WKSharedPreferencesUtil.init(getApplicationContext());
		sp = WKSharedPreferencesUtil.getInstance();
		takePhoto = new WKTakePhotoUtils();
		takePhoto.initApp(this);
		if (isSetCrashHandler) {
			WKCrashHandler crashHandler = WKCrashHandler.getInstance();
			crashHandler.init(this);
		}

		Fresco.initialize(this);

	}


	/**
	 * Activity基类方法，都过来签到！
	 */
	public void initApp(Activity act) {
		curAct = act;
		mList.add(act);
		if (isInitOK == false) {
			imm = ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE));
			dm = new DisplayMetrics();
			curAct.getWindowManager().getDefaultDisplay().getMetrics(dm);
			DisplayMetrics outMetrics = new DisplayMetrics();
			curAct.getWindowManager().getDefaultDisplay()
					.getMetrics(outMetrics);
			WKApplication.SCREEN_WIDTH = outMetrics.widthPixels;
			WKApplication.SCREEN_HEIGHT = outMetrics.heightPixels;
			initImageLoader();

			isInitOK = true;
		}
	}
	/**
	 * Activity基类方法，都过来签出！
	 */
	public void deleteApp(Activity act) {
			mList.remove(act);
			act = null;
//			curAct = null;
	}

	private static Toast mToast = null;

	/**
	 * 显示信息
	 */
	public static void showToast(final String msg) {
		curAct.runOnUiThread(new Runnable() {
			public void run() {
				try {
					if (mToast != null)
						mToast.cancel();
					mToast = T.showShort(curAct, msg);
					mToast.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 显示信息
	 */
	public static Toast showMsg(String msg) {
		new AlertDialog.Builder(curAct).setMessage(msg)
				.setPositiveButton("确定", null)
				.setNeutralButton("", new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
		return null;
	}

	/**
	 * 显示信息，点击确定后退出
	 */
	public static Toast showMsgFinish(String msg) {
		new AlertDialog.Builder(curAct).setMessage(msg)
				.setNeutralButton("确定", new OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						if (curAct != null) {
							curAct.finish();
						}
					}
				}).show();
		return null;
	}

	/**
	 * 显示信息,自定义两个按钮
	 * 
	 * @param msg
	 *            提示信息
	 * @param clickStr1
	 *            按钮文本1
	 * @param click1
	 *            按钮点击1
	 * @param clickStr2
	 *            按钮文本2
	 * @param click2
	 *            按钮点击2
	 * @return
	 */
	public static Toast showMsg(String msg, String clickStr1,
			OnClickListener click1, String clickStr2, OnClickListener click2) {
		new AlertDialog.Builder(curAct).setMessage(msg)
				.setPositiveButton(clickStr1, click1)
				.setNeutralButton(clickStr2, click2).show();
		return null;
	}

	/**
	 * 显示对话框
	 */
	public void showMsg(String msg, DialogInterface.OnClickListener click) {
		new AlertDialog.Builder(curAct).setMessage(msg)
				.setPositiveButton("确定", click).show();
	}

	/**
	 * 杀死所有Activity
	 */
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null) {
					activity.finish();
					activity = null;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * 重启APP
	 */
	public void restart() {

		for (Activity activity : mList) {
			if (activity != null)
				activity.finish();
		}

		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		am.killBackgroundProcesses(getPackageName());
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
								getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}, 200);

		// exit();
	}

	/**
	 * 释放ImageView的bitmap
	 */
	public void recycleImageView(ImageView view) {
		try {
			if (view == null)
				return;
			view.setDrawingCacheEnabled(true);
			Bitmap bitmap = view.getDrawingCache();
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			view = null;

			recycleBitmap(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放bitmap
	 */
	public void recycleBitmap(Bitmap bitmap) {
		try {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是第一次启动
	 */
	public boolean isFirstOpen() {
		boolean isFirst = sp.getBoolean("FirstOpenFlag", true);
		sp.setBoolean("FirstOpenFlag", false);
		return isFirst;
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideInputKeyboard() {
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(curAct.getWindow().getDecorView()
					.getWindowToken(), 0);
		}
	}

	/**
	 * 显示软键盘
	 */
	public void showInputKeyboard() {
		imm.showSoftInput(curAct.getCurrentFocus(),
				InputMethodManager.RESULT_SHOWN);
	}

	/**
	 * 初始化 ImageLoader
	 */
	private void initImageLoader() {

		// imageLoader = new HBHImageLoader();
		// ImageLoaderConfiguration config = new
		// ImageLoaderConfiguration.Builder(
		// getApplicationContext()).threadPoolSize(3)
		// .threadPriority(Thread.NORM_PRIORITY - 2)
		// .memoryCacheSize(2 * 1024 * 1024)
		// .discCacheSize(300 * 1024 * 1024)
		// .denyCacheImageMultipleSizesInMemory()
		// .discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
		imageLoader = new WKHImageLoader();
		imageLoader.app = this;
		imageLoader.loader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(300 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).build();
		imageLoader.loader.init(config);
		imageLoader = new WKHImageLoader();
		imageLoader.app = this;
		imageLoader.loader = ImageLoader.getInstance();
		// options = new DisplayImageOptions.Builder().cacheOnDisc()
		// .cacheInMemory().imageScaleType(ImageScaleType.EXACT).build();
		options = new DisplayImageOptions.Builder().cacheOnDisc()
				.cacheInMemory().imageScaleType(ImageScaleType.EXACT).build();
		options2 = new DisplayImageOptions.Builder().imageScaleType(
				ImageScaleType.EXACT).showStubImage(R.color.gary_ava).showImageForEmptyUri(
						R.color.gary_ava).build();
	}

	/**
	 * 获取IMEI号
	 * */
	public String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * 是否是登录状态
	 */
	public boolean ifLogin() {
		if (user == null)
			return false;
		return true;
	}
	
	/**
     * 清除缓存
     */
    public void cleanCache() {
        WKApplication.showMsg("是否清除缓存?", "是", new OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                new Handler().post(new Runnable() {
                    public void run() {
                        imageLoader.clearMemoryCache();
                        sp.clear();
                        showToast("缓存已清除");
                    }
                });
            }
        }, "否", null);
    }

}
