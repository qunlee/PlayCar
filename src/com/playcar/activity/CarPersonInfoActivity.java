package com.playcar.activity;

import android.os.Bundle;
import android.view.View;

import com.playcar.R;


public class CarPersonInfoActivity extends CarBaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_person_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_back_tv:
                finish();
                break;

            case R.id.setting:
                jumpTo(CarSettingActivity.class);
                break;
        }
    }
}
