package com.wk.libs.utils;

import com.playcar.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 进度条,这个没好好整理。比较乱。
 */
public class WKProgressDlg {

	public static boolean if_showback = false;
	public static boolean if_cancle = false;
	public static Dialog loadingDialog;

	public static ProgressDialog progressDialog;

	/**
	 * 显示进度条，不可取消
	 */
	public static void show(Context act) {
		hide();
		progressDialog = ProgressDialog.show(act, "", "加载中...", true, true);
	}

	/**
	 * 显示进度条，不可取消
	 */
	public static void show(Context act, String info) {
		hide();
		progressDialog = ProgressDialog.show(act, "", info, true, true);
	}

	/**
	 * 显示进度条，可取消
	 */
	public static void show(Context act, boolean cancelable) {
		hide();
		progressDialog = ProgressDialog.show(act, "", "加载中...", true,
				cancelable);
	}

	public static void hide() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/*************************************************************************
	 * 自定义
	 *************************************************************************/

	public static Dialog showDialog(Context act, String message) {
		try {
			stopDialog();

			if (act == null)
				return null;

			LayoutInflater inflater = LayoutInflater.from(act);
			View v = inflater.inflate(R.layout.wk_loading, null);// 得到加载view
			
			if (if_showback) {
				// 将背景色设为显眼透明黑，加入文字
				message = "加载中...";
				v.setBackgroundColor(act.getResources().getColor(R.color.black));
				v.setAlpha((float) 0.5);
			}
			
			LinearLayout layout = (LinearLayout) v
					.findViewById(R.id.dialog_view);// 加载布局
			// main.xml中的ImageView
			ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
			TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
			// 加载动画
			AnimationDrawable animationDrawable;
			spaceshipImage.setBackgroundResource(R.anim.loading_animation);
			animationDrawable = (AnimationDrawable) spaceshipImage
					.getBackground();
			animationDrawable.start();
			loadingDialog = new Dialog(act, R.style.loading_dialog);
			// 创建自定义样式dialog
			loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
			// loadingDialog.setCanceledOnTouchOutside(true);// 是否能取消
			loadingDialog.setCanceledOnTouchOutside(if_cancle);// 是否能取消
			tipTextView.setText(message);// 设置加载信息
			tipTextView.setShadowLayer(1F, 1F, 1F, Color.GRAY);
			if (message == null || message.equals("")) {
				tipTextView.setVisibility(View.GONE);
			} else {
				tipTextView.setVisibility(View.VISIBLE);
			}

			loadingDialog.setCancelable(true);// 不可以用“返回键”取消
			loadingDialog.show();
			return loadingDialog;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void stopDialog() {
		try {
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
				loadingDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
