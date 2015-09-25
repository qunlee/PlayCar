package com.playcar.activity;

import android.os.Bundle;
import android.view.View;

import com.playcar.R;


public class CarPersonCenterActivity extends CarBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_person_center);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                this.finish();
                break;
            case R.id.setting:
                jumpTo(CarSettingActivity.class);
                break;
            case R.id.data_info:
                jumpTo(CarPersonInfoActivity.class);
                break;
            case R.id.friends_count:
                jumpTo(CarFriendsActivity.class);
                break;
            case R.id.add_friend:
                jumpTo(CarAddFriendActivity.class);
                break;
        }
    }
}
