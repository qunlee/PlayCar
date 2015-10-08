package com.playcar.activity.mine;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.playcar.R;

/**
 * 添加爱车
 * @author Administrator
 *
 */
public class CarAddCarActivity extends CarMineBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_activity_add_car);
		initializeHead("返回", "添加爱车", "");
		ViewUtils.inject(this);
	}
	@OnClick(R.id.txt_car_type)
	private void onclick_car_type(View v){
		Intent intent = new Intent(this,CarListActivity.class);
		openActivityAfterLogin(intent);
	}
	@OnClick(R.id.txt_car_param)
	private void onclick_car_param(View v){
		Intent intent = new Intent(this,CarParamActivity.class);
		openActivityAfterLogin(intent);
	}
	

}
