package com.jiuquanlife.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MovingView extends View {

	private Bitmap bitmap;
	private int margin;
	private int dx;
	private boolean moveToRight = true;
	private Matrix matrix;

	public MovingView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setBitmap(int resId, int fatherViewHeight, int fatherWidth) {

		bitmap = ((BitmapDrawable) getContext().getResources().getDrawable(
				resId)).getBitmap();
		matrix = new Matrix();
		float scale = fatherViewHeight * 1.0f / bitmap.getHeight();
		matrix.postScale(scale, scale);
		dx = (int) (bitmap.getWidth() * scale - fatherWidth);
		matrix.postTranslate(-dx, 0);
		move();
		
	}

	
	private void move() {

		if (moveToRight) {
			margin += 2;
			matrix.postTranslate(2, 0);
			if (margin >= dx) {
				margin = dx;
				moveToRight = false;
			}
		} else {
			matrix.postTranslate(-2, 0);
			margin -= 2;
			if (margin < 0) {
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
			canvas.drawBitmap(bitmap, matrix, null);
		}
	}

	public static int getScreenHeight(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		return metrics.heightPixels;
	}

	public static int getScreenWidth(Context context) {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		return metrics.widthPixels;
	}
}
