package com.playcar.activity.mine;

import android.os.Bundle;

import com.playcar.R;

public class CarAboutActivity extends CarMineBaseActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_about_activity);
		initializeHead("返回","关于", "");
	}
}
