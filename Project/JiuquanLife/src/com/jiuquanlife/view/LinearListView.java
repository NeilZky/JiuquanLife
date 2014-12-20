package com.jiuquanlife.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 *替代不滚动的listview，注意更新数据的时候要用notifyDataSetChanged 方法，不能用adaper中的notifiyDataSetChanged
 */
public class LinearListView extends LinearLayout {

	private BaseAdapter adapter;
	
	public LinearListView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public void notifyDataSetChanged() {
		
		this.removeAllViews();
		int count = adapter.getCount();
		for(int i = 0 ; i < count; i++) {
			View child = adapter.getView(i, null, this);
			if(child!=null) {
				this.addView(child);
			}
		}
	}
	
	public void setAdapter(BaseAdapter adapter) {
		
		this.adapter = adapter;
	}
	
}
