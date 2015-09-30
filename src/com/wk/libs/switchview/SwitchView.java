package com.wk.libs.switchview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * SwitchDemoView.java 2015年5月22日 现在app的标配(虽然android原生的好一些，可android用户还是喜欢ios的体验)
 * 宽高比默认100:65，根据需要自己调整(可以取消比例设置，设置自己定义的宽度、高度) 当前只有点击切换，稍后加上滑动切换
 */
public class SwitchView extends View {

	private FieldPlace fieldPlace;
	private BallPlace ballPlace;

	public SwitchView(Context context) {
		this(context, null);
	}

	public SwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// setLayerType(LAYER_TYPE_SOFTWARE, null);
		fieldPlace = new FieldPlace(this);
		ballPlace = new BallPlace(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return true;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			fieldPlace.onTouchEventUp();
			ballPlace.onTouchEventUp();
			if (mSwitchListener != null)
				mSwitchListener.change(ballPlace.getStatus());
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		fieldPlace.draw(canvas);
		ballPlace.draw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		// int heightSize = (int) (widthSize*0.3); //宽高比例
		int heightSize = MeasureSpec.getSize(heightMeasureSpec); // 宽高比例
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		fieldPlace.onSizeChanged(w, h, oldw, oldh);
		ballPlace.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * @return 获取状态
	 */
	public boolean getStatus() {
		return ballPlace.getStatus();
	}

	/**
	 * @param status
	 *            设置当前状态
	 */
	public void setStatus(boolean status) {
		fieldPlace.setStatus(status);
		ballPlace.setStatus(status);
	}

	private OnSwitchListener mSwitchListener;

	/**
	 * @param listener
	 *            设置切换监听
	 */
	public void setOnSwitchListener(OnSwitchListener listener) {
		this.mSwitchListener = listener;
	}

	/**
	 * 切换开关状态接口
	 */
	public interface OnSwitchListener {
		public void change(boolean status);
	}

}