package com.wk.libs.net;

import java.lang.ref.WeakReference;

import com.playcar.WKApplication;
import com.wk.libs.utils.WKProgressDlg;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author lazybone
 * 
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
public abstract class WKAsyncTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result> {

	// 是否显示加载进度圈
	public boolean showLoading = true;

	// 是否读取本地缓存
	public int GET_DATA_TYPE = GET_DATA_TYPE_SERVER;
	// 只能服务器读取
	public static final int GET_DATA_TYPE_SERVER = 1;
	// 只能本地读取
	public static final int GET_DATA_TYPE_LOCAL = 2;
	// 先从服务器读取，在从本地读取
	public static final int GET_DATA_TYPE_LOCAL_THEN_SERVER = 2;

	protected WeakReference<Context> taskContext;
	private String info = "";

	/**
	 * 构造
	 */
	public WKAsyncTask(Context context) {
		super();
		taskContext = new WeakReference<Context>(context);
	}

	/**
	 * 构造,带提示信息
	 */
	public WKAsyncTask(Context context, String info) {
		super();
		taskContext = new WeakReference<Context>(context);
		this.info = info;
	}

	/**
	 * 构造,是否显示加载信息
	 */
	public WKAsyncTask(Context context, boolean showLoading) {
		super();
		taskContext = new WeakReference<Context>(context);
		this.showLoading = showLoading;
	}

	/**
	 * 设置请求类型
	 * 
	 * @param type
	 *            1:服务器 2:本地 3:先服务器再本地
	 */
	public void setGetDataType(int type) {
		GET_DATA_TYPE = type;
	}

	private long startTime = 0;
	private long endTime = 0;

	@Override
	protected abstract Result doInBackground(Params... params);

	@Override
	protected void onPostExecute(Result result) {
		try {
			super.onPostExecute(result);
			WKProgressDlg.stopDialog();
			endTime = System.currentTimeMillis();
			if (WKApplication.SHOW_NET_LOG) {
				Log.i("WK", "net: cost " + (endTime - startTime));
				Log.i("WK", "net: " + result.toString());
			}
			doOnPostExecute(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理数据类，代替之前的onPostExecute,实现时无需手动 try catch
	 */
	protected abstract void doOnPostExecute(Result result);

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		startTime = System.currentTimeMillis();
		if (showLoading) {
			if (this.taskContext.get() != null) {
				WKProgressDlg.showDialog(this.taskContext.get(), info);
			}
		}
	}

}
