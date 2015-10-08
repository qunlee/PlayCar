package com.playcar.adapter.news;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.playcar.R;
import com.playcar.adapter.mine.AdapterManager;

public class ChattingAdapter extends AdapterManager<String>{

	public ChattingAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		arg1 = getView(arg0, arg1);
		
		return arg1;
	}
	private View getView(int arg0, View arg1){
		return arg0 % 2 == 0 ? this.inflater.inflate(R.layout.car_chat_left_item, null) : this.inflater.inflate(R.layout.car_chat_right_item, null); 
	}

}
