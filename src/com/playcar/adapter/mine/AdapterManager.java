package com.playcar.adapter.mine;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lidroid.xutils.BitmapUtils;
import com.playcar.R;

/**
 * 适配器管理类
 */
public abstract class AdapterManager<T> extends BaseAdapter {
	protected List<T> list = null;
	protected Context context;
	protected LayoutInflater inflater;
	protected BitmapUtils bu;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public AdapterManager(List<T> list, Context context) {
		this.list = list;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		bu = new BitmapUtils(context);
		bu.configDefaultLoadFailedImage(R.drawable.car);
		bu.configDefaultLoadingImage(R.drawable.car);
	}

	public int getCount() {
		return (list != null && list.size() > 0) ? list.size() : 0;
	}

	public T getItem(int arg0) {
		return list.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public void refresh() {
		this.notifyDataSetChanged();
	}

	public void deleteFromIndex(int index) {
		this.list.remove(index);
		this.notifyDataSetChanged();
	}

	public void deleteFromData(T t) {
		this.list.remove(t);
		this.notifyDataSetChanged();
	}

	public void addData(List<T> listAll) {
		if (null != listAll && listAll.size() > 0) {
			this.list.addAll(listAll);
			this.notifyDataSetChanged();
		}
	}

	public void addData(T t, int index) {
		if (t != null) {
			this.list.add(index, t);
			this.notifyDataSetChanged();
		}
	}

	public void setData(int position, T t) {
		if (null != list && list.size() > 0) {
			this.list.set(position, t);
			this.notifyDataSetChanged();
		}
	}

	public void deleteData() {
		if (null != list && list.size() > 0) {
			this.list.clear();
			this.notifyDataSetChanged();
		}
	}

	public void clean() {
		if (null != list && list.size() > 0) {
			this.list.clear();
			this.notifyDataSetChanged();
		}
	}

	public abstract View getView(int arg0, View arg1, ViewGroup arg2);

}
