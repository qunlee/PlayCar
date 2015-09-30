package com.playcar.activity.friends;

import com.playcar.R;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class ActivityFriendInfo extends Activity implements OnClickListener {
	
	private View line_view;
	private Button setting_btn;
	private RelativeLayout attention;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_near_friend_info);
		line_view = (View) findViewById(R.id.line_view);
		setting_btn = (Button) findViewById(R.id.setting_btn);
		attention = (RelativeLayout) findViewById(R.id.attention);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.back_btn:
			finish();
			break;
		case R.id.setting_btn:
			showCompanySelection();
			break;
		case R.id.conversation:
			
			break;
		case R.id.attention:
			setSettingBtn(true);
			break;
//		case R.id.cancle:
//			if (popSelection != null) {
//				popSelection.dismiss();
//			}
//			setSettingBtn(false);
//			break;

		default:
			break;
		}
	}
	
	private void setSettingBtn(boolean set) {
		if (set) {
			attention.setVisibility(View.GONE);
			line_view.setVisibility(View.GONE);
			setting_btn.setVisibility(View.VISIBLE);
		} else {
			attention.setVisibility(View.VISIBLE);
			line_view.setVisibility(View.VISIBLE);
			setting_btn.setVisibility(View.GONE);
		}
	}
	
	/**
     * 选择
     */
    private PopupWindow popSelection;

    private void showCompanySelection() {
        View sView = View.inflate(this, R.layout.car_friend_setting_pupop, null);
        ((LinearLayout) sView.findViewById(R.id.cancle)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (popSelection != null) {
					popSelection.dismiss();
				}
				setSettingBtn(false);
			}
		});
        popSelection = new PopupWindow(sView, AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        popSelection.setBackgroundDrawable(new BitmapDrawable());
        popSelection.setOutsideTouchable(true);
        popSelection.setFocusable(true);
        popSelection.showAsDropDown(setting_btn, 0, 0);
    }
}
