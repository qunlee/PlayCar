package com.playcar.activity.trands;

import com.playcar.R;
import com.playcar.bean.ReviewsBean;
import com.playcar.bean.ReviewsBean.ReviewBean;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityTrandInfo extends Activity implements OnClickListener {

	private ListView review_list;
	private AdapterReview adapter;
	private ReviewsBean reviewsBean = new ReviewsBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_near_trand_info);

		review_list = (ListView) findViewById(R.id.review_list);

		initData();
		adapter = new AdapterReview(this, reviewsBean);
		review_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void initData() {
		ReviewBean reviewBean;
		for (int i = 0; i < 4; i++) {
			reviewBean = reviewsBean.new ReviewBean();

			reviewsBean.reviews.add(reviewBean);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back_btn:
			// 返回
			finish();
			break;
		case R.id.attention_btn:
			// 关注
			Toast.makeText(this, "关注", Toast.LENGTH_SHORT).show();
			break;
		case R.id.ask:
			// 进入评论
			Toast.makeText(this, "进入评论", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
