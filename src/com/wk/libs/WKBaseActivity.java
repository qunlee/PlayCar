package com.wk.libs;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.playcar.R;

import net.tsz.afinal.FinalActivity;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 基类
 */
public abstract class WKBaseActivity extends FinalActivity {

	// 是否显示显示标题栏
	private boolean noTitle = true;
	// 是否显示状态栏
	private boolean noStateBar = false;
	// 上下文
	protected Context ctx;
	protected Activity activity;
	// 宽度、高度
	public int width;
	public int height;

	/**
	 * 获取全局APP
	 */
	public WKApplication app() {
		return (WKApplication) super.getApplication();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.ctx = this;
		this.activity = this;
		initScreen();
		getDisplay();
		loadXml();
		getIntentDataTryCatch(savedInstanceState);
		initTryCatch();
		setDataTryCatch();
	}

	/**
	 * 初始化屏幕
	 */
	public void initScreen() {
		// 无标题
		if (noTitle) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		// 无状态栏
		if (noStateBar) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		// 竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * 得到父view
	 */
	public View getRootView() {
		return ((ViewGroup) this.findViewById(android.R.id.content))
				.getChildAt(0);
	}

	/**
	 * 获取屏幕尺寸
	 */
	private void getDisplay() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		width = mDisplayMetrics.widthPixels;
		height = mDisplayMetrics.heightPixels;
	}

	public void getIntentDataTryCatch(Bundle savedInstanceState) {
		try {
			getIntentData(savedInstanceState);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initTryCatch() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDataTryCatch() {
		try {
			setData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置xml文件
	 */
	public abstract void loadXml();

	/**
	 * 获取intent数据，因为跟saveIntentData对应使用，频率不高，隐藏此方法。
	 */
	public void getIntentData(Bundle savedInstanceState) {
	};

	/**
	 * view 初始化
	 */
	public abstract void init();

	/**
	 * 数据设置
	 */
	public abstract void setData();

	protected void onResume() {
		super.onResume();
		app().initApp(activity);
		// System.out.println("onResume()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		// System.out.println("onStart()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// System.out.println("onPause()");
	}

	@Override
	protected void onDestroy() {
		this.ctx = null;
		this.activity = null;
		// System.out.println("onDestroy()");
		recycleView((ViewGroup) getRootView());
		
		app().deleteApp(this);
		System.gc();
		super.onDestroy();
	}

	// private void clear() {
	//
	// try {
	// setContentView(R.layout.wk_null_layout);
	// for (SoftReference<ImageView> iv : OOMImageViewList.values()) {
	// if (iv.get() != null) {
	// app().recycleImageView(iv.get());
	// }
	// }
	// OOMImageViewList.clear();
	// OOMImageViewList = null;
	//
	// for (SoftReference<Object> object : OOMObjectList.values()) {
	// if (object != null)
	// object = null;
	// }
	//
	// OOMObjectList.clear();
	// OOMObjectList = null;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	protected void onStop() {
		super.onStop();
	}

	// /**
	// * 将对象添加到待销毁列表
	// */
	// public void addOOMList(View o) {
	// if (o == null)
	// return;
	// if (o instanceof ImageView) {
	// OOMImageViewList.put(o.getId(), new SoftReference<ImageView>(
	// (ImageView) o));
	// } else {
	// OOMObjectList.put(o.getId(), new SoftReference<Object>(o));
	// }
	// }

	// /**
	// * ImageView列表
	// */
	// public Map<Integer, SoftReference<ImageView>> OOMImageViewList = new
	// HashMap<Integer, SoftReference<ImageView>>();
	// /**
	// * 对象列表
	// */
	// public Map<Integer, SoftReference<Object>> OOMObjectList = new
	// HashMap<Integer, SoftReference<Object>>();

	/**
	 * 释放所有的ImageView资源
	 */
	private void recycleView(ViewGroup v) {
		if (v instanceof ViewGroup) {
			for (int i = 0; i < v.getChildCount(); i++) {
				View vv = v.getChildAt(i);
				if (vv instanceof ViewGroup) {
					recycleView((ViewGroup) vv);
				} else if (vv instanceof ImageView) {
					app().recycleImageView((ImageView) vv);
				} else if (vv instanceof View) {
					if (vv != null)
						vv = null;
				}
			}
		}
		v = null;
	}
}
