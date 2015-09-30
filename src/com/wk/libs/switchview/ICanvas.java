package com.wk.libs.switchview;

import android.graphics.Canvas;

public interface ICanvas {

	// public static int ColorField = 0xff178dcd;
	// public static int ColorStroke = 0xffc8c8c8;

	public static int ColorField = 0xffdc0a1f;
	public static int ColorStroke = 0xffdc0a1f;

	public void draw(Canvas canvas);

	public void onSizeChanged(int w, int h, int oldw, int oldh);

	public void onTouchEventUp();

}
