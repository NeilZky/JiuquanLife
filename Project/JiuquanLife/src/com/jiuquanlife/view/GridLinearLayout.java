package com.jiuquanlife.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class GridLinearLayout extends LinearLayout {
	
	private BaseAdapter adapter;
	private int columnCount;

	public GridLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
	}

	public GridLinearLayout(Context context) {
		super(context);
		setOrientation(VERTICAL);
	}

	public void notifyDataSetChanged() {

		this.removeAllViews();
		int count = adapter.getCount();
		int linearCount = count/columnCount;
		if(count%columnCount > 0) {
			linearCount++;
		}
		for(int j = 0 ; j < linearCount ; j++) {
			LinearLayout ll = new LinearLayout(getContext());
			ll.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			addView(ll, params);
			ll.setWeightSum(columnCount);
			for(int i = columnCount*j; i<count&&i<columnCount*(j+1); i++) {
				View item = adapter.getView(i, null, this);
				LinearLayout.LayoutParams childParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
				childParams.weight = 1;
				ll.addView(item, childParams);
			}
		}
	}

	public void setAdapter(BaseAdapter adapter) {

		this.adapter = adapter;
	}
	
	public void setColumnCount(int columnCount) {
		
		this.columnCount = columnCount;
	}
	
}
