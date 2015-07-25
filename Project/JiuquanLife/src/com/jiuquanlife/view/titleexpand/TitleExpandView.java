package com.jiuquanlife.view.titleexpand;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.SizeUtils;

public class TitleExpandView extends LinearLayout {

	private TitleExpandDialog dialog;
	private TextView titleTv;
	private ImageView arrowIv;
	public TitleExpandView(Context context, AttributeSet attrs) {

		super(context, attrs);
		initView();
	}

	private void initView() {

		LayoutInflater inflater = LayoutInflater.from(getContext());
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER);
		RelativeLayout rl = (RelativeLayout) inflater.inflate(
				R.layout.title_rl_expand_view, null);
		addView(rl);
		arrowIv = (ImageView) inflater.inflate(
				R.layout.title_iv_expand_view, null);
		int marginLeft = SizeUtils.dip2px(getContext(), 2);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);// 重点部分
		params.setMargins(marginLeft, 0, 0, 0);
		addView(arrowIv, params);
		titleTv = (TextView) rl.findViewById(R.id.tv_title_trev);
		ImageView badgeIv = (ImageView) rl.findViewById(R.id.iv_badge_trev);
		dialog = new TitleExpandDialog(getContext(), titleTv, badgeIv, arrowIv);

		setOnClickListener(onClickListener);
	}

	public void addItem(String title, OnClickListener listener) {

		dialog.addItem(title, listener);
	}

	public void setTitle(String text) {
		titleTv.setText(text);
	}

	public void setTitle(int textId) {
		titleTv.setText(textId);
	}

	public void setBelowView(View belowView) {

		dialog.setBelowView(belowView);
	}

	public void addItem(int resId, OnClickListener listener) {

		String title = getContext().getString(resId);
		dialog.addItem(title, listener);
	}

	public void show() {

		if (dialog != null) {
			dialog.show();
		}
	}

	public int getSelectedPosition() {

		return dialog.getSelectedPosition();
	}

	public void performClickAtPosition(int position) {

		dialog.performClickAtPosition(position);
	}

	public void refreshBadge(int position, boolean isShow) {

		dialog.refreshBadge(position, isShow);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			dialog.show();
		}
	};
	
	public void changeItemColor(int position) {
		
		dialog.changeItemColor(position);
	}
	
	public void setTextColor(int color) {
		
		titleTv.setTextColor(color);
	}
	
	public void setTextSize(int sp) {
		
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
	}
	
	public void setArrow(int resId) {
		
		arrowIv.setImageResource(resId);
	}
	
}
