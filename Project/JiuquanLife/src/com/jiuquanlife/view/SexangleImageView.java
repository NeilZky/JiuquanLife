package com.jiuquanlife.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.jiuquanlife.R;

public class SexangleImageView extends ImageView {

	private int mWidth;
	private int mHeight;
	private int mLenght;
	private Paint paint;
	
	private int bgColor;
	private int pressedColor;
	private float textsize=24;

	private String texts;
	private Bitmap scrBitmap;


	private OnSexangleImageClickListener listener;
	public SexangleImageView(Context context) {
		super(context);

	}
	
	@SuppressLint("Recycle")
	public SexangleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.sexangleImageView);
		bgColor = typedArray.getColor(R.styleable.sexangleImageView_bgcolor, Color.BLACK);
		pressedColor = typedArray.getColor(R.styleable.sexangleImageView_pressedcolor, Color.BLACK);
		textsize = typedArray.getDimension(R.styleable.sexangleImageView_textSize, 24);
		texts = typedArray.getString(R.styleable.sexangleImageView_texts);
		Drawable drawable = typedArray.getDrawable(R.styleable.sexangleImageView_src); 
		scrBitmap = ((BitmapDrawable) drawable).getBitmap();
	 
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mWidth = getWidth();
		mHeight = getHeight();

		//中心点
		mLenght = mWidth / 2;

		double radian30 = 30 * Math.PI / 180;
		float a = (float) (mLenght * Math.sin(radian30));
		float b = (float) (mLenght * Math.cos(radian30));
		float c = (mHeight - 2 * b) / 2;
 	
		//int color=Color.parseColor("#FFD700");//十六进制颜色代码,转为int类型
		if (null == paint) {
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Style.FILL);
			//paint.setColor(Color.BLACK);
			paint.setColor(bgColor);
			paint.setAlpha(255);
		}
		//画六边形
		Path path = new Path();
		path.moveTo(getWidth(), getHeight() / 2);
		path.lineTo(getWidth() - a, getHeight() - c);
		path.lineTo(getWidth() - a - mLenght, getHeight() - c);
		path.lineTo(0, getHeight() / 2);
		path.lineTo(a, c);
		path.lineTo(getWidth() - a, c);
		path.close();		
		canvas.drawPath(path, paint);
		
		
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(textsize);
		// 去锯齿
		paint.setAntiAlias(true);
		
		//画背景
		Matrix matrix = new Matrix();
		matrix.postTranslate(this.getWidth() / 2 - scrBitmap.getWidth() / 2,
				this.getHeight() / 2 - scrBitmap.getHeight() / 2 - textsize /2);
		canvas.drawBitmap(scrBitmap, matrix, paint);
		canvas.drawText(texts, getWidth()/2-textsize, getHeight()-textsize*3/2, paint);
		//canvas.drawText(texts,centreX,centreY, paint);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mWidth, mHeight);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float start = 1.0f;
		float end = 0.94f;
		Animation scaleAnimation = new ScaleAnimation(start, end, start, end,
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		Animation endAnimation = new ScaleAnimation(end, start, end, start,
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(100);
		scaleAnimation.setFillAfter(true);
		endAnimation.setDuration(100);
		endAnimation.setFillAfter(true);
			
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.startAnimation(scaleAnimation);
			float edgeLength = ((float) getWidth()) / 2;
			float radiusSquare = edgeLength * edgeLength * 3 / 4;
			float dist = (event.getX() - getWidth() / 2)
					* (event.getX() - getWidth() / 2)
					+ (event.getY() - getHeight() / 2)
					* (event.getY() - getHeight() / 2);
			if (dist <= radiusSquare) {// 点中六边形区域
				paint.setColor(pressedColor);
				paint.setAlpha(255);
				invalidate();
			}

			break;

		case MotionEvent.ACTION_UP:
			this.startAnimation(endAnimation);
			paint.setColor(bgColor);
			paint.setAlpha(255);
			if(listener!=null){
				listener.onClick(this);
			}
			invalidate();

			break;
			// 滑动出去不会调用action_up,调用action_cancel
		case MotionEvent.ACTION_CANCEL:
			this.startAnimation(endAnimation);
			paint.setColor(Color.BLACK);
			//paint.setColor(color);
			paint.setAlpha(255);

			invalidate();
			break;
					
		}

		return true;
	}

	public void setOnSexangleImageClick(OnSexangleImageClickListener listener ){
		this.listener=listener;
	}
	
	public interface  OnSexangleImageClickListener {
		public void onClick(View view);
	}
}

