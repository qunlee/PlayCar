package com.playcar.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.playcar.R;
import com.playcar.activity.mine.CarMineActivity;



public class CarMainActivity extends TabActivity implements CompoundButton.OnCheckedChangeListener {
    private TabHost mHost;
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private Intent intent;
    private int tab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_main);
        radioButton0 = ((RadioButton) findViewById(R.id.radio_button0));
        radioButton0.setOnCheckedChangeListener(this);
        radioButton1 = ((RadioButton) findViewById(R.id.radio_button1));
        radioButton1.setOnCheckedChangeListener(this);
        radioButton2 = ((RadioButton) findViewById(R.id.radio_button2));
        radioButton2.setOnCheckedChangeListener(this);
        radioButton3 = ((RadioButton) findViewById(R.id.radio_button3));
        radioButton3.setOnCheckedChangeListener(this);
        radioButton4 = ((RadioButton) findViewById(R.id.radio_button4));
        radioButton4.setOnCheckedChangeListener(this);
        setupIntent();
        mHost.setCurrentTab(tab);
        switch (tab) {
            case 0:
                radioButton0.setChecked(true);
                break;
            case 1:
                radioButton1.setChecked(true);
                break;
            case 2:
                radioButton2.setChecked(true);
                break;
            case 3:
                radioButton3.setChecked(true);
                break;
            case 4:
                radioButton4.setChecked(true);
                break;

        }
    }


    private void setupIntent() {
        intent = new Intent();
        mHost = getTabHost();
        intent = new Intent(CarMainActivity.this, CarNearActivity.class);
        TabHost.TabSpec tabSpec0 = mHost.newTabSpec("home");
        tabSpec0.setIndicator(getString(R.string.first_tab), getResources().getDrawable(R.drawable.ic_launcher));
        tabSpec0.setContent(intent);
        mHost.addTab(tabSpec0);

        intent = new Intent(CarMainActivity.this, CarClubActivity.class);
        TabHost.TabSpec tabSpec2 = mHost.newTabSpec("search");
        tabSpec2.setIndicator(getString(R.string.second_tab), getResources().getDrawable(R.drawable.ic_launcher));
        tabSpec2.setContent(intent);
        mHost.addTab(tabSpec2);

        intent = new Intent(CarMainActivity.this, CarFindActivity.class);
        TabHost.TabSpec tabSpec4 = mHost.newTabSpec("search_hotel");
        tabSpec4.setIndicator(getString(R.string.third_tab), getResources().getDrawable(R.drawable.ic_launcher));
        tabSpec4.setContent(intent);
        mHost.addTab(tabSpec4);

        intent = new Intent(CarMainActivity.this, CarPushMessageActivity.class);
        TabHost.TabSpec tabSpec1 = mHost.newTabSpec("scenicSpot");
        tabSpec1.setIndicator(getString(R.string.forth_tab), getResources().getDrawable(R.drawable.ic_launcher));
        tabSpec1.setContent(intent);
        mHost.addTab(tabSpec1);

        intent = new Intent(CarMainActivity.this, CarMineActivity.class);
        TabHost.TabSpec tabSpec3 = mHost.newTabSpec("myown");
        tabSpec3.setIndicator(getString(R.string.fifth_tab), getResources().getDrawable(R.drawable.ic_launcher));
        tabSpec3.setContent(intent);
        mHost.addTab(tabSpec3);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_button0:
                    mHost.setCurrentTab(0);
                    break;
                case R.id.radio_button1:
                    mHost.setCurrentTab(1);
                    break;
                case R.id.radio_button2:
                    mHost.setCurrentTab(2);
                    break;
                case R.id.radio_button3:
                    mHost.setCurrentTab(3);
                    break;
                case R.id.radio_button4:
                    mHost.setCurrentTab(4);
                    break;
            }
        }
    }

}
