package com.wk.libs.net;

import android.content.Context;

/**
 * 带缓存的异步任务。构造此类时，会传入参数来判断从服务器或者本地来获取数据。
 */
public abstract class WKAsyncTaskPro {

	public boolean showLoading = true;// 是否显示加载进度圈

	private String localResult = null;// 本地数据，跟服务器对比，来判断是否需要更新

	private boolean isLoadCacheOK = false;// 用来标记是否已经读取完本地数据，每次都读取本地数据

	private boolean isBusy = false;// 是否正在请求

	/**
	 * 后台执行，升级版
	 */
	protected abstract String doInBackground(boolean... params);

	/**
	 * 结果返回，升级版
	 * 
	 * isLocal：是否从本地获取
	 */
	protected abstract void doOnPostExecute(String result, boolean isLocal);

	/**
	 * 构造，默认不读取本地数据，只从服务器拿
	 */
	public WKAsyncTaskPro(Context context) {
		isLoadCacheOK = true;
		new MyTask(context).execute();
	}

	/**
	 * 构造,是否先读取缓存
	 * 
	 * @param context
	 * @param localFirst
	 *            是否从本地读取
	 */
	public WKAsyncTaskPro(Context context, boolean localFirst) {
		isLoadCacheOK = false;
		new MyTask(context).execute();
	}

	/**
	 * 构造,带提示信息
	 * 
	 * @param context
	 * @param info
	 *            提示信息
	 * @param localFirst
	 *            是否先显示本地信息
	 */
	public WKAsyncTaskPro(Context context, String info, boolean localFirst) {
		isLoadCacheOK = false;
		new MyTask(context, info).execute();
	}

	/**
	 * 构造,是否显示加载信息
	 * 
	 * @param context
	 * @param localFirst
	 *            是否读取本地
	 * @param showLoading
	 *            是否显示加载进度圈
	 */
	public WKAsyncTaskPro(Context context, boolean localFirst,
			boolean showLoading) {
		isLoadCacheOK = !localFirst;// 是否从本地读取
		new MyTask(context, showLoading).execute();
	}

	/**
	 * 真正的异步任务
	 */
	class MyTask extends WKAsyncTask<String, String, String> {

		public MyTask(Context context, String info) {
			super(context, info);
		}

		public MyTask(Context context) {
			super(context);
		}

		public MyTask(Context context, boolean showLoading) {
			super(context, showLoading);
		}

		@Override
		protected String doInBackground(String... params) {
			if (isBusy) {
				return "";
			}
			isBusy = true;
			return WKAsyncTaskPro.this.doInBackground(!isLoadCacheOK);
		}

		protected void doOnPostExecute(String result) {
			isBusy = false;
			if (isLoadCacheOK == false) {// 本地
				isLoadCacheOK = true;
				localResult = result;
				if (result != null && !result.equals("")) {
					WKAsyncTaskPro.this.doOnPostExecute(result, true);
				}
				// 本地用完了再从服务器拿
				new MyTask(taskContext.get()).execute();
			} else {// 不是本地
				// 本地为空，本地和服务器数据不相同，
				if (localResult == null || !localResult.equals(result)) {
					WKAsyncTaskPro.this.doOnPostExecute(result, false);
					// System.out.println("lazy list need update");
				} else {
					// System.out.println("lazy list no update");
				}
			}
		}
	}

}
