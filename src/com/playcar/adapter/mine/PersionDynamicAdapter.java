package com.playcar.adapter.mine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.R;
import com.playcar.util.ViewHolder;
import com.playcar.view.MyGridView;

public class PersionDynamicAdapter extends AdapterManager<String> {

	public PersionDynamicAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = this.inflater.inflate(R.layout.car_persion_dynamic_item, null);
		}
		MyGridView gridView = ViewHolder.get(arg1, R.id.gridView);
		List<String> list = new ArrayList<String>();
		list.add("");
		gridView.setAdapter(new GrideImageViewAdapter(list, context));
		return arg1;
	}

}
