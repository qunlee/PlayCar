package com.playcar.adapter.news;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.playcar.R;
import com.playcar.adapter.mine.AdapterManager;
import com.playcar.util.ViewHolder;

public class NewsAdapter extends AdapterManager<String> {

	private int mRightWidth = 0;

	/**
	 * 单击事件监听器
	 */
	private IOnItemRightClickListener mListener = null;

	public NewsAdapter(List<String> list, Context context) {
		super(list, context);
	}

	public NewsAdapter(List<String> list, Context ctx, int rightWidth, IOnItemRightClickListener l) {
		super(list, ctx);
		mRightWidth = rightWidth;
		mListener = l;
	}

	public interface IOnItemRightClickListener {
		void onRightClick(View v, int position);
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = this.inflater.inflate(R.layout.news_adapter, null);
		}
		RelativeLayout item_right = ViewHolder.get(arg1, R.id.item_right);
		TextView item = ViewHolder.get(arg1, R.id.item_right_txt);
		LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
		item_right.setLayoutParams(lp2);
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onRightClick(v, arg0);
				}
			}
		});
		return arg1;
	}

}
