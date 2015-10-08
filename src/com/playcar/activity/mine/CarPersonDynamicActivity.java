package com.playcar.activity.mine;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.playcar.R;
import com.playcar.adapter.mine.PersionDynamicAdapter;
import com.wk.libs.listview.PullToRefreshBase;
import com.wk.libs.listview.PullToRefreshBase.OnRefreshListener2;
import com.wk.libs.listview.PullToRefreshListView;

public class CarPersonDynamicActivity extends CarMineBaseActivity implements OnRefreshListener2<ListView>{
	@ViewInject(R.id.listView)
	private PullToRefreshListView listView;
	private PersionDynamicAdapter adapter;
	private List<String> list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_person_dynamic_activity);
		initializeHead("返回", "个人动态", "");
		ViewUtils.inject(this);
		setPullToRefreshAttribute(listView);
		listView.setOnRefreshListener(this);
		setAdapter();
	}
	public void setAdapter(){
		if(adapter == null){
			list.add("");
			list.add("");
			list.add("");
			adapter = new PersionDynamicAdapter(list, this);
			listView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		
	}
}
