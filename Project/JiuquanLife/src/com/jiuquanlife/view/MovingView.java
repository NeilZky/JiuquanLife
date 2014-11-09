package com.jiuquanlife.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class MovingView extends View {

	private Bitmap bitmap;
	private int margin;
	private int dx;
	private boolean moveToRight = true;
	private Matrix matrix;
	private boolean isFirst = true;

	public MovingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setBitmap(int resId) {

		bitmap = ((BitmapDrawable) getContext().getResources().getDrawable(
				resId)).getBitmap();
		matrix = new Matrix();
		invalidate();
	}

	
	private void move() {

		if (moveToRight) {
			margin += 3;
			matrix.postTranslate(3, 0);
			if ((margin +3) >= dx) {
				margin = dx;
				moveToRight = false;
			}
		} else {
			matrix.postTranslate(-3, 0);
			margin -= 3;
			if ((margin -3)< 0) {
				margin = 0;
				moveToRight = true;
			}
		}
		postDelayed(new Runnable() {

			@Override
			public void run() {
				move();
			}
		}, 100L);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		
		if (bitmap != null) {
			if(isFirst) {
				isFirst = false;
				int height = getMeasuredHeight();
				int width = getMeasuredWidth();
				float scale = height * 1.0f / bitmap.getHeight();
				matrix.postScale(scale, scale);
				dx = (int) (bitmap.getWidth() * scale - width);
				matrix.postTranslate(-dx, 0);
				move();
			}
			canvas.drawBitmap(bitmap, matrix, null);
		}
	}

}
