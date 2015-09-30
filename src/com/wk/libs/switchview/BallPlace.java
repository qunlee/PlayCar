package com.wk.libs.switchview;

import android.graphics.*;
import android.graphics.Paint.Style;
import android.view.View;

/**
 * 
 * BallPlace.java 2015年5月22日 按钮的绘制
 */
public class BallPlace implements ICanvas {
	private View parentView;
	private Paint paint = new Paint();

	private float mWidth, mHeight;

	private float bRadius, bStrokeWidth;
	private float bTop, bLeft, bBottom, bRight;
	private float transDistance; // 平移总的距离

	private boolean isON = false;
	private float bAnim;

	private boolean isFirst = true; // 第一次加载

	public BallPlace(View parentView) {
		super();
		this.parentView = parentView;
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		if (isFirst) {
			canvas.translate(calcTranslate(0), 0);
			canvas.drawCircle(bRight / 2, bBottom / 2, bRadius, paint);
			// ///// = = = = = = = = = = = = =
			paint.setStyle(Style.STROKE);
			paint.setColor(ColorStroke);
			paint.setStrokeWidth(bStrokeWidth);
			canvas.drawCircle(bRight / 2, bRight / 2, bRadius, paint); // 绘制按钮边框
			canvas.restore();
		} else {
			// canvas.drawCircle(bRight/2, bBottom/2, bRadius, paint);
			// canvas.save();
			bAnim = bAnim - 0.1f > 0 ? bAnim - 0.1f : 0;
			canvas.translate(calcTranslate(bAnim), 0);
			canvas.drawCircle(bRight / 2, bBottom / 2, bRadius, paint);
			// ///// = = = = = = = = = = = = =
			paint.setStyle(Style.STROKE);
			paint.setColor(ColorStroke);
			paint.setStrokeWidth(bStrokeWidth);
			canvas.drawCircle(bRight / 2, bRight / 2, bRadius, paint); // 绘制按钮边框
			canvas.restore();
			if (bAnim > 0)
				parentView.invalidate();
		}
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w * 0.98f;
		mHeight = h;
		float halfHeight = h / 2f;
		bRadius = halfHeight * 0.95f;
		bStrokeWidth = halfHeight - bRadius;
		bTop = bLeft = 0;
		bBottom = bRight = h;
		transDistance = mWidth - 2 * bRadius;
	}

	@Override
	public void onTouchEventUp() {
		isON = !isON;
		bAnim = 1;
		parentView.invalidate();
	}

	/**
	 * @param percent
	 *            计算平移距离
	 */
	private float calcTranslate(float percent) {
		float result = 0;
		if (isON) {
			result = (transDistance) * (1 - percent);
		} else {
			result = (transDistance) * (percent);
		}
		return result;
	}

	/**
	 * @return 获取状态
	 */
	public boolean getStatus() {
		return isON;
	}

	/**
	 * @param status
	 *            设置当前状态 设置是否是默认初始化
	 */
	public void setStatus(boolean status) {
		if (isON != status) {
			isON = status;
			bAnim = 1;
			parentView.invalidate();
		}
	}
}