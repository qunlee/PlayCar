package com.playcar.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.playcar.R;

/**
 * 省份简称提示框
 * @author Administrator
 *
 */
public class ProvinceShortNameDialog extends AlertDialog {

	protected ProvinceShortNameDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		@ViewInject(R.id.grideView)
		private GridView gridView;
		@ViewInject(R.id.btn_cancle)
		private Button btn_cancle;

		public ProvinceShortNameDialog create() {
			View currentLayout = LayoutInflater.from(context).inflate(R.layout.car_province_short_name_dialog, null);
			ProvinceShortNameDialog dialog = new ProvinceShortNameDialog(context, R.style.MyDialog);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			dialog.setContentView(currentLayout, params);
			ViewUtils.inject(this, currentLayout);
			return dialog;
		}
	}

}
