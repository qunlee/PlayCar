package com.playcar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.bean.Friend;

/**
 * Created by dan.liu on 9/24/15.
 */
public class CarFriendsAdapter extends BaseListAdapter<Friend>{
    public CarFriendsAdapter(Context context) {
        super(context);
    }

    @Override
    protected View createViewWithResource(int position, View convertView, ViewGroup parent, int resource) {
        return null;
    }
}
