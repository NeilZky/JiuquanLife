package com.jiuquanlife.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;

public class DoubleRowLinearlayout extends LinearLayout{

	public DoubleRowLinearlayout(Context context, AttributeSet attrs) {

		super(context, attrs);
	}
	
	private LinearLayout lastItemContent;
	
	public void addItem(String label, String value) {
		
		boolean initedLastItemContent = false;
		if(lastItemContent == null) {
			lastItemContent = new LinearLayout(getContext());
			lastItemContent.setOrientation(LinearLayout.HORIZONTAL);
			initedLastItemContent = true;
		}
		LinearLayout item = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_house_detail, null);
		TextView labelTv = (TextView) item.findViewById(R.id.tv_label_ihd);
		TextView valTv = (TextView) item.findViewById(R.id.tv_value_ihd);
		labelTv.setText(label);
		valTv.setText(value);
		LinearLayout.LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.weight = 1;
		lastItemContent.addView(item, params);
		
		if(!initedLastItemContent) {
			lastItemContent = null;
		} else{
			addView(lastItemContent);
		}
	}
	
}
