package com.jiuquanlife.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	private float x;
	private float y;
	
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			return super.onInterceptTouchEvent(event);
		case MotionEvent.ACTION_MOVE:
			boolean value = super.onInterceptTouchEvent(event);
			if(value)
			{
			    float deltax = event.getX() - x;

			    float deltay = event.getY() - y;

			    if( Math.abs(deltay)> Math.abs(deltax))//说明确实是上下滑动的

			    {
			        return true;
			    }

			} else {
				return false;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
}
