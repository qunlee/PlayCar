package com.playcar.activity;

import android.os.Bundle;
import android.view.View;

import com.playcar.R;


public class CarSettingActivity extends CarBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back_tv:
                finish();
                break;
            case R.id.secure:
                break;
            case R.id.mode:
                break;
            case R.id.message_remind:
                break;
            case R.id.black_list:
                break;
            case R.id.about:
                break;
            case R.id.exit:
                break;
        }
    }
}
