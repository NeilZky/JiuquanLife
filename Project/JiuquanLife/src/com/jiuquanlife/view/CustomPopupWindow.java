package com.jiuquanlife.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

public class CustomPopupWindow extends PopupWindow {

	public CustomPopupWindow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(View contentView, int width, int height,
			boolean focusable) {
		super(contentView, width, height, focusable);
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
		// TODO Auto-generated constructor stub
	}

	public CustomPopupWindow(View contentView) {
		super(contentView);
		// TODO Auto-generated constructor stub
	}
	
	private IntercepterDismissListener intercepterDismissListener;
	

	public void setIntercepterDismissListener(IntercepterDismissListener intercepterDismissListener) {
		this.intercepterDismissListener = intercepterDismissListener;
	}

	@Override
	public void dismiss() {
		
		if(intercepterDismissListener!=null) {
			intercepterDismissListener.onIntercept();
		} else {
			super.dismiss();
		}
	}
	
	public void superDismiss() {
		
		super.dismiss();
	}

	public interface IntercepterDismissListener {

		public void onIntercept();
	}
}
