package com.playcar.activity.trands;

import java.util.ArrayList;

import com.facebook.drawee.view.SimpleDraweeView;
import com.playcar.R;
import com.playcar.bean.ReviewsBean;
import com.playcar.bean.ReviewsBean.ReviewBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterReview extends BaseAdapter {

	public Context context;
	public ReviewsBean reviewsBean;

	public AdapterReview(Context context, ReviewsBean reviewsBean) {
		this.context = context;
		this.reviewsBean = reviewsBean;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return reviewsBean.reviews.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return reviewsBean.reviews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.car_near_trand_reviews_item, null);
			holder = new ViewHolder();
			
			holder.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.review = (TextView) convertView.findViewById(R.id.review);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
		}
//		ReviewBean reviewBean = reviewsBean.reviews.get(position);
//		holder.name.setText("");
		
		return convertView;
	}

	/** 存放控件 */
	public final class ViewHolder {
		public SimpleDraweeView img;
		public TextView name;
		public TextView time;
		public TextView review;
	}

}
