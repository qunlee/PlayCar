package com.wk.libs.listview;

/**
 * 
 * ViewHolder 接口，必须实现设置数据方法
 * 
 * @param <T>
 *            这个T是指 getView中填充的bean
 */
public abstract class WKViewHolder<T> {

	public abstract void setData(T t);
}
