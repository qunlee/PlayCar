package com.playcar.activity.mine.imp;

import com.wqdsoft.im.BaseActivity;

public class FinishActivityStrategy implements MenuStrategy{
	private BaseActivity activity;
	public FinishActivityStrategy(BaseActivity activity){
		this.activity = activity;
	}
	@Override
	public void openActivity() {
		this.activity.finish();
	}
	
}
