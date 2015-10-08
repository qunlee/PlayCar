package com.playcar.activity.mine;

import android.os.Bundle;

import com.playcar.R;
/**
 * 设置界面
 * @author Administrator
 *
 */
public class CarSettingActivity extends CarMineBaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_setting_activity);
		initializeHead("反回","设置", "");
	}
}
