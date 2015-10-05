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
 * 附近——群组详细
 * @author qunlee_mr
 *
 */
public class ActivityGroupInfo extends Activity implements OnClickListener {
	
	private TextView group_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_near_group_info);
		
		group_title = (TextView) findViewById(R.id.group_title);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back_btn:
			finish();
			break;
		case R.id.commit_btn:
			startActivity(new Intent(this, ActivityGroupSetting.class));
			break;
		
		default:
			break;
		}
		
	}
}
