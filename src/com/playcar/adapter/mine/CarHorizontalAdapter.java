package com.playcar.adapter.mine;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.playcar.R;
import com.playcar.util.Tools;

public class CarHorizontalAdapter extends AdapterManager<String> {

	public CarHorizontalAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ImageView imageView = null;
		if(arg1 == null){
			 imageView = new ImageView(context);
		}
		int width = Tools.dip2px(context, 60);
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(width, width);
		imageView.setLayoutParams(params);
		imageView.setImageResource(R.drawable.car);
		imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}



}
