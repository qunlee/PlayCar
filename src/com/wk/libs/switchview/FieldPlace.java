package com.wk.libs.switchview;

import android.graphics.*;
import android.graphics.Paint.Style;
import android.view.View;

/**
 * FieldPlace.java 2015年5月22日 背景的绘制
 */
public class FieldPlace implements ICanvas {
	private View parentView;

	private Path sPath = new Path();
	private Paint paint = new Paint();

	private int mWidth, mHeight;

	private float sLeft, sTop, sRight, sBottom;
	private float sWidth, sHeight;
	private float sCenterX, sCenterY;

	private float sAnim;
	private boolean isON = false;

	private boolean isFirst = true; // 是否是第一次

	public FieldPlace(View view) {
		super();
		this.parentView = view;
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		if (isFirst) {
			if (isON) {
				isFirst = false;
				paint.setColor(ColorField);
				canvas.drawPath(sPath, paint); // 画出田径场
				canvas.save();
				canvas.scale(0.98f, 0.98f, sCenterX, sCenterY);
				// paint.setColor(Color.WHITE);
				paint.setColor(ColorField);
				canvas.drawPath(sPath, paint);
				canvas.restore();
			} else {
				paint.setColor(ColorField);
				canvas.drawPath(sPath, paint); // 画出田径场
				canvas.save();
				canvas.scale(0.98f, 0.98f, sCenterX, sCenterY);
				paint.setColor(Color.WHITE);
				canvas.drawPath(sPath, paint);
				canvas.restore();
			}
		} else {
			if (isON) {
				paint.setColor(ColorField);
				canvas.drawPath(sPath, paint); // 画出田径场
				canvas.save();
				sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0; // 动画标示 ，重绘10次
				final float scale = 0.98f * (isON ? sAnim : 1 - sAnim); // 缩放大小参数随sAnim变化而变化
				canvas.scale(scale, scale, sCenterX, sCenterY);
				paint.setColor(Color.WHITE);
				canvas.drawPath(sPath, paint);
				canvas.restore();
				if (sAnim > 0)
					parentView.invalidate(); // 继续重绘
			} else {
				paint.setColor(ColorField);
				canvas.drawPath(sPath, paint); // 画出田径场
				canvas.save();
				sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0; // 动画标示 ，重绘10次
				final float scale = 0.98f * (isON ? sAnim : 1 - sAnim); // 缩放大小参数随sAnim变化而变化
				canvas.scale(scale, scale, sCenterX, sCenterY);
				paint.setColor(Color.WHITE);
				canvas.drawPath(sPath, paint);
				canvas.restore();
				if (sAnim > 0)
					parentView.invalidate(); // 继续重绘
			}
		}
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w; // 视图自身宽高
		mHeight = h;
		sLeft = sTop = 0; // 田径场左上坐标
		sRight = mWidth;
		// sBottom = mHeight * 0.8f;
		sBottom = mHeight;
		sWidth = sRight - sLeft; // 田径场宽度高度
		sHeight = sBottom - sTop;
		sCenterX = (sRight + sLeft) / 2; // 田径场中心坐标
		sCenterY = (sTop + sBottom) / 2;

		RectF sRectF = new RectF(sLeft, sTop, sBottom, sBottom);
		sPath.arcTo(sRectF, 90, 180); // 左半边
		sRectF.left = sRight - sBottom;
		sRectF.right = sRight;
		sPath.arcTo(sRectF, 270, 180); // 有半边
		sPath.close();
	}

	@Override
	public void onTouchEventUp() {
		sAnim = 1;
		isON = !isON;
		parentView.invalidate();
	}

	/**
	 * @return 获取状态
	 */
	public boolean getStatus() {
		return isON;
	}

	/**
	 * @param status
	 *            设置当前状态
	 */
	public void setStatus(boolean status) {
		if (isON != status) {
			isON = status;
			sAnim = 1;
			parentView.invalidate();
		}
	}

}