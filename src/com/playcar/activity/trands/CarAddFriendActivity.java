package com.playcar.activity.trands;

import android.os.Bundle;
import android.view.View;

import com.playcar.R;
import com.playcar.activity.CarBaseActivity;


public class CarAddFriendActivity extends CarBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_add_friend);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back_tv:
                finish();
                break;
            case R.id.add_from_contants:
                break;
            case R.id.add_from_weixin:
                break;
            case R.id.add_from_qq:
                break;
        }
    }
}
