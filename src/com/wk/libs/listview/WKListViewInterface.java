package com.wk.libs.listview;

import android.view.View;

/**
 * WKListView需要继承的几个方法
 */
public interface WKListViewInterface {

	/**
	 * 后台请求数据的方法
	 */
	public abstract String getData(boolean... isLocal);

	/**
	 * 请求数据返回的方法
	 * 
	 * @param result
	 *            返回的json数据
	 */
	public abstract void getDataBack(String result, boolean isLocal);

	/**
	 * 初始化列表item的viewHolder
	 */
	public abstract WKViewHolder createViewHolder(View arg1);

	/**
	 * 设置 item 的xml
	 */
	public abstract int getItemViewId();
}
