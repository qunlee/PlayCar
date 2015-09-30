package com.playcar.activity.trands;

import com.facebook.drawee.view.SimpleDraweeView;
import com.playcar.R;
import com.playcar.activity.trands.AdapterReview.ViewHolder;
import com.playcar.bean.LocalsBean;
import com.playcar.bean.LocalsBean.LocalBean;
import com.playcar.bean.ReviewsBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterLocal extends BaseAdapter {
	public Context context;
	public LocalsBean localsBean;
	public AdapterLocal(Context context, LocalsBean localsBean) {
		this.context = context;
		this.localsBean = localsBean;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return localsBean.locals.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return localsBean.locals.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.car_locals_list_item, null);
			holder = new ViewHolder();

			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.detail = (TextView) convertView.findViewById(R.id.detail);
			holder.selected = (ImageView) convertView.findViewById(R.id.selected);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
//		ReviewBean reviewBean = reviewsBean.reviews.get(position);
//		holder.name.setText("");
		LocalBean localBean = localsBean.locals.get(position);
		holder.selected.setVisibility(localBean.selected ? View.VISIBLE : View.INVISIBLE);
		
		return convertView;
	}
	
	/** 存放控件 */
	public final class ViewHolder {
		TextView address;
		TextView detail;
		ImageView selected;
	}

}
