package com.playcar.activity.mine;

import android.os.Bundle;

import com.playcar.R;
import com.playcar.adapter.mine.MyCarListAdapter;
import com.wqdsoft.im.BaseActivity;

/**
 * 我的爱车列表界面
 * 
 * @author Administrator
 * 
 */
public class CarMyCarListActivity extends BaseActivity {
	private MyCarListAdapter carListAdapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_my_car_list_activity);
	}
}
