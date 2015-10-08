package com.playcar.activity.mine;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.playcar.R;
import com.playcar.adapter.mine.CarHorizontalAdapter;
import com.playcar.view.HorizontalListView;

/**
 * 爱车详情编辑
 * 
 * @author Administrator
 * 
 */
public class CarEditActivity extends CarMineBaseActivity {
	@ViewInject(R.id.hlv_car_imgs)
	private HorizontalListView hListView;
	private CarHorizontalAdapter hAdapter;
	private List<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_activity_car_edit);
		initializeHead("返回", "爱车详情", "保存");
		ViewUtils.inject(this);
		setAdapter();
	}

	private void setAdapter() {
		if (hAdapter == null) {
			list.add("");
			list.add("");
			list.add("");
			hAdapter = new CarHorizontalAdapter(list, this);
			hListView.setAdapter(hAdapter);
		}
		hAdapter.notifyDataSetChanged();
	}
	@OnClick(R.id.layout_car_type)
	private void onclick_carType(View v){
		openActivityAfterLogin(new Intent(this,CarListActivity.class));
	}
	@OnClick(R.id.layout_car_params)
	private void onclick_carParams(View v){
		openActivityAfterLogin(new Intent(this,CarParamActivity.class));
	}
}
