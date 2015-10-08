package com.playcar.activity.news;

import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.playcar.R;
import com.playcar.activity.mine.CarMineBaseActivity;
import com.playcar.adapter.news.ChattingAdapter;

public class ChatingActivity extends CarMineBaseActivity{
	@ViewInject(R.id.listView)
	private ListView listView;
	private ChattingAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_chating_activity);
		initializeHead("返回", "萍儿", "资料");
		ViewUtils.inject(this);
		
	}
}
