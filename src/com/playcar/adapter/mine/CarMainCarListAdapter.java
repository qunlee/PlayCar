package com.playcar.adapter.mine;

import java.util.List;

import com.playcar.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主界面我的爱车列表适配器
 * @author Administrator
 *
 */
public class CarMainCarListAdapter extends AdapterManager<String>{

	public CarMainCarListAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if(arg1 == null){
			arg1 = this.inflater.inflate(R.layout.car_main_car_list_adapter, null);
		}
		return arg1;
	}

}
