package com.playcar.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.playcar.R;

public class CarAddFriendsActivity extends CarMineBaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_add_friends_activity);
		initializeHead("返回", "添加好友", "");
		ViewUtils.inject(this);
	}

	@OnClick(R.id.txt_add_contact_friends)
	private void onclick_add_contact_friends(View v) {
		Intent intent = new Intent(this, CarAddContactFriendsActivity.class);
		openActivityAfterLogin(intent);
	}

	@OnClick(R.id.txt_add_wechat_friends)
	private void onclick_add_wechat_friends(View v) {
		Intent intent = new Intent(this, CarAddContactFriendsActivity.class);
		openActivityAfterLogin(intent);
	}

	@OnClick(R.id.txt_add_qq_friends)
	private void onclick_add_qq_friends(View v) {
		Intent intent = new Intent(this, CarAddContactFriendsActivity.class);
		openActivityAfterLogin(intent);
	}
}
