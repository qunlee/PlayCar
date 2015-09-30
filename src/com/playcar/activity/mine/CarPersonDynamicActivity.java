package com.playcar.activity.mine;

import android.os.Bundle;

import com.playcar.R;
import com.playcar.adapter.mine.PersionDynamicAdapter;

public class CarPersonDynamicActivity extends CarMineBaseActivity {
	private PersionDynamicAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.car_person_dynamic_activity);
	}
}
