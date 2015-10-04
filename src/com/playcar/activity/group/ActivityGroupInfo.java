package com.playcar.activity.group;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.playcar.R;

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

		default:
			break;
		}
		
	}
}
