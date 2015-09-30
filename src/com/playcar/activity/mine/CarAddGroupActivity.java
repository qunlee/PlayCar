package com.playcar.activity.mine;

import android.os.Bundle;

import com.playcar.R;
import com.playcar.adapter.mine.AddGroupAdapter;

/**
 * 添加群组
 * @author Administrator
 *
 */
public class CarAddGroupActivity extends CarMineBaseActivity {
	private AddGroupAdapter addGroupAdapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_add_group_activity);
		
	}
}
