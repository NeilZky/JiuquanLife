package com.jiuquanlife.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.SizeUtils;

public class RonateButton extends LinearLayout{

	private TextView nameTv;
	private ImageView arrowIv;
	private Context context;
	
	public RonateButton(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		
		this.context = context;
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER);
		nameTv = new TextView(context);
		int paddingTob =  SizeUtils.dip2px(context, 12);
		int paddiing = SizeUtils.dip2px(context, 7);
		nameTv.setPadding(paddiing, paddingTob, paddiing, paddingTob);
		nameTv.setTextColor(Color.WHITE);
		nameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		LinearLayout.LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addView(nameTv, param);
		arrowIv =  new ImageView(context);
		arrowIv.setBackgroundResource(R.drawable.arrow_down_normal);
		LinearLayout.LayoutParams ivParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ivParam.setMargins(0, 0, paddiing, 0);
		addView(arrowIv, ivParam);
	}
	
	@Override
	public void setOnClickListener(final OnClickListener l) {
		
		super.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rotateArrow();
				l.onClick(v);
			}
		});
	}
	
	public void setName(String name) {
		
		nameTv.setText(name);
	}
	
	private void rotateArrow() {

		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.ronate_0_180);
		arrowIv.startAnimation(animation);
	}
	
	public void ronateArrowBack() {

		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.ronate_180_360);
		arrowIv.startAnimation(animation);
	}
	
}
