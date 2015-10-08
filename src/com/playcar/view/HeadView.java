package com.playcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.playcar.R;

public class HeadView extends FrameLayout {

	@ViewInject(R.id.txt_back)
	private TextView txt_back;
	@ViewInject(R.id.txt_title)
	private TextView txt_title;
	@ViewInject(R.id.txt_right)
	private TextView txt_right;
	
	private Context context;
	private View currentLayout;
	public HeadView(Context context) {
		super(context);
		init(context);
	}

	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	void init(Context context){
		this.context = context;
		this.currentLayout = LayoutInflater.from(context).inflate(R.layout.car_head_view, null);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addView(currentLayout,param);
		ViewUtils.inject(this, currentLayout);
	}
	public void setBackValue(String value){
		if(txt_back != null){
			txt_back.setText(value);
		}
	}
	/**
	 * 设置标题
	 * @param R
	 */
	public void setTitle(int R){
		String title = context.getResources().getString(R);
		setTitle(title);
	}
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title){
		if(txt_title != null){
			txt_title.setText(title);
		}
	}
	/**
	 * 设置右边显示文字
	 * @param R
	 */
	public void setRightValue(int R){
		String title = context.getResources().getString(R);
		setRightValue(title);
	}
	/**
	 * 设置标题
	 * @param title
	 */
	public void setRightValue(String title){
		if(txt_right != null){
			txt_right.setText(title);
		}
	}
	public void onclickBack(OnClickListener l){
		if(txt_back != null){
			txt_back.setOnClickListener(l);
		}
	}
	public void onclickRight(OnClickListener l){
		if(txt_right != null){
			txt_right.setOnClickListener(l);
		}
	}

}
