package com.playcar.adapter.news;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.playcar.R;
import com.playcar.adapter.mine.AdapterManager;
import com.playcar.util.Tools;
import com.playcar.util.ViewHolder;

public class ChattingAdapter extends AdapterManager<String>{

	private boolean isHideName;
	public void setHideName(boolean isHideName) {
		this.isHideName = isHideName;
	}

	public ChattingAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		arg1 = getView(arg0, arg1);
		TextView tv_sendtime = ViewHolder.get(arg1, R.id.tv_sendtime);
		ImageView imgHead = ViewHolder.get(arg1, R.id.iv_userhead);
		TextView tv_name = ViewHolder.get(arg1, R.id.tv_name);
		TextView tv_chatcontent = ViewHolder.get(arg1, R.id.tv_chatcontent);
		tv_sendtime.setText(Tools.getCurrentDate("yyyy-MM-dd HH:mm"));
		tv_name.setVisibility(isHideName ? View.GONE : View.VISIBLE);
		tv_chatcontent.setText("你猜我会说啥子");
		return arg1;
	}
	private View getView(int arg0, View arg1){
		return arg0 % 2 == 0 ? this.inflater.inflate(R.layout.car_chat_left_item, null) : this.inflater.inflate(R.layout.car_chat_right_item, null); 
	}

}
