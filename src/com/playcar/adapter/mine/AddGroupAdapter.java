package com.playcar.adapter.mine;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.R;

public class AddGroupAdapter extends AdapterManager<String>{

	public AddGroupAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = this.inflater.inflate(R.layout.car_add_group_item, arg2);
		}
		return arg1;
	}

}
