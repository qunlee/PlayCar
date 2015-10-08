package com.playcar.activity.mine.imp;

import com.wqdsoft.im.BaseActivity;

import android.content.Intent;

public class OpenMenuAfterLogin implements MenuStrategy{

	private Intent intent;
	private BaseActivity activity;
	public OpenMenuAfterLogin(Intent intent , BaseActivity activity){
		this.intent = intent;
		this.activity = activity;
	}
	@Override
	public void openActivity() {
		activity.openActivityAfterLogin(intent);
	}
	
}
