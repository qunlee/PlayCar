package com.playcar.view.adversiting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.playcar.util.Tools;

/**
 * 广告原点
 * 
 * @author Administrator
 * 
 */
public class AdvertisingCricle extends View {

	private Context context;
	private Paint nomalPaint;
	private Paint actionPaint;
	private int radius = 8;
	private final int MAX_COUNT = 10;
	private int count = 0;
	// Y轴的最表
	private int screenHeight;
	// 原点的X坐标的集合
	private float[] mCircleInactiveXCoordinate = null;
	// 每一个圆点的宽度
	private float mCircleSeparation = 0;
	private int selectItem = 0;
	private int horizontalSpacing = 0;

	public void setHorizontalSpacing(int horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
		initCircles(this.count);
		invalidate();
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
		invalidate();
	}

	public AdvertisingCricle(Context context) {
		super(context);
		init(context);
	}

	public AdvertisingCricle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AdvertisingCricle(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
  
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	private void init(Context context) {
		this.context = context;
		radius = Tools.dip2px(context, (float) 2.5);
		horizontalSpacing = radius + Tools.dip2px(context, (float) 1) ;
		nomalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		nomalPaint.setColor(Color.GRAY);
		nomalPaint.setStyle(Paint.Style.FILL);
		nomalPaint.setAntiAlias(true);
		actionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		actionPaint.setColor(Color.WHITE);
		actionPaint.setStyle(Paint.Style.FILL);
		actionPaint.setAntiAlias(true);
		initCircles(count);
	}

	/**
	 * 设置圆的数组
	 * 
	 * @param count
	 */
	private void initCircles(int count) {
		// 获得每个圆的宽度
		mCircleSeparation = 2 * this.radius + horizontalSpacing;
		// 获得当前试图高度
		screenHeight = getPaddingTop() + radius;
		// 将每个圆的宽度放入集合
		mCircleInactiveXCoordinate = new float[count];
		for (int i = 0; i < count; i++) {
			mCircleInactiveXCoordinate[i] = getPaddingLeft() + this.radius + (i * mCircleSeparation);
		}
	}

	/**
	 * 设置默认的颜色
	 * 
	 * @param color
	 */
	public void setNomalColor(int color) {
		nomalPaint.setColor(color);
		invalidate();
	}

	/**
	 * 设置选中的颜色
	 * 
	 * @param color
	 */
	public void setActionColor(int color) {
		actionPaint.setColor(color);
		invalidate();
	}

	/**
	 * 设置圆的大小
	 * 
	 * @param radius
	 */
	public void setCricleSize(int radius) {
		this.radius = radius;
		invalidate();
	}

	/**
	 * 设置圆的总数
	 */
	public void setCount(int count) {
		if (count > MAX_COUNT) {
			count = MAX_COUNT;
		}
		this.count = count;
		initCircles(count);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < this.count; i++) {
			if (i == this.selectItem) {
				canvas.drawCircle(mCircleInactiveXCoordinate[i], screenHeight, this.radius, actionPaint);
			} else {
				canvas.drawCircle(mCircleInactiveXCoordinate[i], screenHeight, this.radius, nomalPaint);
			}
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	/**
	 * 计算试图宽度
	 * 
	 * @param measureSpec
	 * @return
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (specMode) {
		case MeasureSpec.EXACTLY:
			result = specSize;
			break;
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			result = (int) (getPaddingLeft() + getPaddingRight() + (count * 2 * radius) + (count - 1) * horizontalSpacing + 1);
			break;

		default:
			break;
		}
		return result;
	}

	/**
	 * 计算试图高度
	 * 
	 * @param measureSpec
	 * @return
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		switch (specMode) {
		case MeasureSpec.EXACTLY:
			result = specSize;
			break;
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			result = (int) (2 * radius + getPaddingTop() + getPaddingBottom() + 1);
			break;

		default:
			break;
		}
		return result;
	}
	
	//context.startActivity(new Intent(context,AnnouncementListActivity.class));
	
}
