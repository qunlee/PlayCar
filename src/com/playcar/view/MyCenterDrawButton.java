package com.playcar.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class MyCenterDrawButton extends Button {

	public MyCenterDrawButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyCenterDrawButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyCenterDrawButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		Drawable[] drawables = getCompoundDrawables();
		if (drawables != null) {
			Drawable drawableLeft = drawables[0];
			if (drawableLeft != null) {
				final float textWidth = getPaint().measureText(getText().toString());
				final int drawablePadding = getCompoundDrawablePadding();
				final int drawableWidth = drawableLeft.getIntrinsicWidth();
				final float bodyWidth = textWidth + drawableWidth + 2*drawablePadding;
				canvas.translate((getWidth() - bodyWidth) / 2, 0);
			}
		}
		super.onDraw(canvas);
	}

}
