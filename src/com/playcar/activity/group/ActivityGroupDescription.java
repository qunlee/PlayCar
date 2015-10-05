package com.playcar.activity.group;

import com.playcar.R;
import com.playcar.activity.trands.ActivityLocal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 附近——创建新群组，群介绍
 * @author qunlee_mr
 * 
 */
public class ActivityGroupDescription extends Activity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_near_group_description);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back_btn:
			finish();
			break;
		case R.id.commit_btn:

			break;

		default:
			break;
		}
	}

}
