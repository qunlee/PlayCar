package com.playcar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.R;
import com.playcar.activity.CarFriendsActivity;

/**
 * Created by Aure on 15/9/23.
 */
public class CarFollowsFragment extends Fragment implements View.OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.car_fragment_follow, container, false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.follow:
                break;
            case R.id.fans:
                break;
        }
    }
}
