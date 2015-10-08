package com.playcar.view.carlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.playcar.R;

/**
 * 汽车品牌对应的类型视图
 * 
 * @author Administrator
 * 
 */
public class CarTypeView extends LinearLayout {

	public CarTypeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		View currentLayout = LayoutInflater.from(context).inflate(R.layout.car_type_view, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(currentLayout, params);
		ViewUtils.inject(this, currentLayout);
	}

}
