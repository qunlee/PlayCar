package com.playcar.activity.mine;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.playcar.R;
import com.playcar.adapter.mine.MyCarListAdapter;

/**
 * 我的爱车列表界面
 * 
 * @author Administrator
 * 
 */
public class CarMyCarListActivity extends CarMineBaseActivity implements OnClickListener {
	@ViewInject(R.id.listView)
	private ListView listView;
	private MyCarListAdapter carListAdapter;
	private List<String> list = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_my_car_list_activity);
		initializeHead("返回", "我的爱车", "");
		ViewUtils.inject(this);
		View view = LayoutInflater.from(this).inflate(R.layout.add_mine_car_list_item_bottom, null);
		Button btn = (Button) view.findViewById(R.id.btn_addCar);
		btn.setOnClickListener(this);
		listView.addFooterView(view);
		setadapter();
	}
	private void setadapter() {
		if(carListAdapter == null){
			list.add("");
			list.add("");
			carListAdapter = new MyCarListAdapter(list, this);
			listView.setAdapter(carListAdapter);
		}
		carListAdapter.notifyDataSetChanged();
	}
	@OnItemClick(R.id.listView)
	private void onItemClick_car(AdapterView<?> arg0, View arg1, int arg2, long arg3){
		openActivityAfterLogin(new Intent(this,CarDetailActivity.class));
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_addCar:
			Intent intent = new Intent(this,CarAddCarActivity.class);
			openActivityAfterLogin(intent);
			break;

		default:
			break;
		}
	}
	
}
