package com.playcar.activity.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.playcar.R;
import com.playcar.activity.mine.CarMineBaseActivity;
import com.playcar.adapter.news.NewsAdapter;
import com.playcar.adapter.news.NewsAdapter.IOnItemRightClickListener;
import com.playcar.util.Tools;
import com.wk.libs.listview.SwipeListView;

public class CarPushMessageActivity extends CarMineBaseActivity{
	@ViewInject(R.id.swipeListView)
	private SwipeListView swipeListView;
	private NewsAdapter newsAdapter;
	private List<String> list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_car_push_message_activity);
		initializeHead("", "消息", "忽略未读");
		ViewUtils.inject(this);
		View footView = LayoutInflater.from(this).inflate(R.layout.car_news_service_view, null);
		swipeListView.addFooterView(footView);
		setAdapter();
	}
	public void setAdapter(){
		if(newsAdapter == null){
			list.add("");
			list.add("");
			int width = Tools.dip2px(this, 50);
			swipeListView.setRightViewWidth(width);
			newsAdapter = new NewsAdapter(list, this, width, new IOnItemRightClickListener() {
				
				@Override
				public void onRightClick(View v, int position) {
					swipeListView.resetItems();
				}
			});
			swipeListView.setAdapter(newsAdapter);
		}
		newsAdapter.notifyDataSetChanged();
	}
	@OnItemClick(R.id.swipeListView)
	private void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
		Intent intent = new Intent(this,ChatingActivity.class);
		openActivityAfterLogin(intent);
	}
	
}
