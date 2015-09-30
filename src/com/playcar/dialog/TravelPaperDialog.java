package com.playcar.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.lidroid.xutils.ViewUtils;
import com.playcar.R;
/**
 * 行驶证选择提示框
 * @author Administrator
 *
 */
public class TravelPaperDialog extends AlertDialog{

	protected TravelPaperDialog(Context context, int theme) {
		super(context, theme);
	}
	public static class Builder{
		private Context context;
		public TravelPaperDialog create(){
			View currentLayout = LayoutInflater.from(context).inflate(R.layout.car_travel_paper_dialog, null);
			TravelPaperDialog dialog = new TravelPaperDialog(context, R.style.MyDialog);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			dialog.setContentView(currentLayout, params);
			ViewUtils.inject(this, currentLayout);
			return dialog;
		}
	}

}
