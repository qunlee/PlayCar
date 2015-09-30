package com.playcar.activity.trands;

import com.playcar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAddTrand extends Activity implements OnClickListener {

//	private Button cancle_btn;
//	private Button commit_btn;
//	private Button local_btn;
//	private CheckBox show_check;

	private boolean if_show = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_near_trands_add);
	}

	@Override
	public void onClick(View view) {
		int vid = view.getId();
		switch (vid) {
		case R.id.cancle_btn:
			finish();
			break;
		case R.id.commit_btn:
			Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					finish();
				}
			}, 1000);
			break;
		case R.id.local_btn:		//获取地理位置
			startActivityForResult(new Intent(this, ActivityLocal.class), 1);
			break;
		case R.id.show_check:
			if_show = if_show ? false : true;
			Toast.makeText(this, if_show ? "显示" : "隐藏", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String add = data.getStringExtra("address");
		((TextView) findViewById(R.id.local_btn)).setText(add);
	}

}
