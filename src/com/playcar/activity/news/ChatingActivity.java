package com.playcar.activity.news;

import java.util.ArrayList;
import java.util.List;

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
	private List<String> list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_chating_activity);
		initializeHead("返回", "萍儿", "资料");
		ViewUtils.inject(this);
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		adapter = new ChattingAdapter(list, this);
		listView.setAdapter(adapter);
		listView.setSelection(list.size() -1);
	}
}
