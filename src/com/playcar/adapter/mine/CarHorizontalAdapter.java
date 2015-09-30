package com.playcar.adapter.mine;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.playcar.R;

public class CarHorizontalAdapter<T> extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private List<T> mList;

	public CarHorizontalAdapter(Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		mList = new ArrayList<T>();
	}

	public void setList(List<T> list) {
		if (list == null) {
			return;
		}
		mList = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.car_listitem_car_img, null);
		}
		return convertView;
	}

}
