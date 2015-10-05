package com.playcar.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.playcar.R;
import com.playcar.activity.trands.ActivityLocal;

/**
 * 附近——群组设置
 * 
 * @author qunlee_mr
 *
 */
public class ActivityGroupSetting extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_near_group_setting);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back_btn:
			finish();
			break;
		case R.id.commit_btn:
			finish();
			break;
		case R.id.local:
			startActivity(new Intent(this, ActivityLocal.class));
			break;
		case R.id.description:
			startActivity(new Intent(this, ActivityGroupDescription.class));
			break;

		default:
			break;
		}

	}
}
