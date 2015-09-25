package com.wk.libs.listview;

import com.wk.libs.WKApplication;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class WKBaseAdapter extends BaseAdapter {

	public Context ctx;

	@SuppressWarnings("rawtypes")
	public WKViewHolder holder;

	public WKBaseAdapter(Context ctx) {
		this.ctx = ctx;
	}

	public WKApplication app() {
		return (WKApplication) ctx.getApplicationContext();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

}
