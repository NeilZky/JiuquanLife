package com.jiuquanlife.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;

public class NSHorizontalListView extends HorizontalListView {

	private boolean outBound = false;

	private int distance;

	private int firstOut;

	Context mContext;

	public NSHorizontalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	GestureDetector gestureDetector = new GestureDetector(
			new OnGestureListener() {
				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void onShowPress(MotionEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2,
						float distanceX, float distanceY) {
					int itemCount = getCount();
					// outbound Top
					if (outBound && mCurrentX != 0 && mCurrentX != mMaxX) {
						scrollTo(0, 0);
						return false;
					}
					if (!outBound)
						firstOut = (int) e2.getRawX();
					if (((outBound || (mCurrentX == 0 && distanceX < 0)))
							|| ((outBound || (mCurrentX == mMaxX && distanceX > 0)))) {
						// Record the length of each slide
						distance = firstOut - (int) e2.getRawX();
						scrollBy(distance / 2, 0);
						return true;
					}
					// outbound Bottom
					return false;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onDown(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}
			});

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mCurrentX == 0 || mCurrentX == mMaxX) {
			int act = event.getAction();
			if ((act == MotionEvent.ACTION_UP || act == MotionEvent.ACTION_CANCEL)
					&& outBound) {
				outBound = false;
				// scroll back
			}
			if (!gestureDetector.onTouchEvent(event)) {
				outBound = false;
			} else {
				outBound = true;
			}
			System.out.println("begian animation");
			Rect rect = new Rect();
			getLocalVisibleRect(rect);

			TranslateAnimation am = new TranslateAnimation(-rect.left, 0, 0, 0);
			am.setDuration(300);
			startAnimation(am);
			scrollTo(0, 0);

		}
		return super.dispatchTouchEvent(event);
	}
}
