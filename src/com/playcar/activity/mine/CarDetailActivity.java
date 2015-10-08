package com.playcar.activity.mine;

import android.content.Intent;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;
import com.playcar.R;
import com.playcar.activity.mine.imp.OpenMenuAfterLogin;

public class CarDetailActivity extends CarMineBaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_detail_activity);
		Intent intent= new Intent(this,CarEditActivity.class);
		setMenuStrategy(new OpenMenuAfterLogin(intent, this));
		initializeHead("返回", "爱车详情", "编辑");
		ViewUtils.inject(this);	
	}
}
