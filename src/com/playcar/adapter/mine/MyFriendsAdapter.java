package com.playcar.adapter.mine;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.R;

public class MyFriendsAdapter extends AdapterManager<String>{

	public MyFriendsAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = this.inflater.inflate(R.layout.car_my_friends_item, null);
		}
		return arg1;
	}

}
