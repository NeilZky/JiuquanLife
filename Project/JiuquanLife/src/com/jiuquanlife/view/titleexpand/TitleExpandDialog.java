package com.jiuquanlife.view.titleexpand;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.view.CustomPopupWindow;

 class TitleExpandDialog {

	private CustomPopupWindow dialog;
	private Context context;
	private View belowView;
	private ViewGroup content;
	private TextView titleTv;
	private ImageView badgeIv;
	private ImageView arrowIv;
	private OnDismissListener onDismissListener;
	private Handler handler = new Handler();
	private int selectedPosition;
	
	public TitleExpandDialog(Context context, TextView titleTv, ImageView badgeIv, ImageView arrowIv) {

		this.context = context;
		this.titleTv = titleTv;
		this.badgeIv = badgeIv;
		this.arrowIv = arrowIv;
		createDialog();
	}
	

	public void show() {
		
		if (dialog == null) {
			return;
		}
		if (dialog.isShowing()) {
			dialog.dismiss();
		}	
		rotateArrow();
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.popshow_anim);
		content.startAnimation(animation);
		dialog.showAsDropDown(belowView);
	}
	
	public boolean isShowing() {

		return dialog == null ? false : dialog.isShowing();
	}

	public void dismiss() {

		if (dialog != null) {
			dialog.dismiss();
			ronateArrowBack();
		}
	}
	
	public void setBelowView(View belowView) {

		this.belowView = belowView;
	}

	public void setOnDimissListener(OnDismissListener onDismissListener) {
		
		this.onDismissListener = onDismissListener;
	}

	private void createDialog() {
		
		View contentView = LayoutInflater.from(context).inflate(
				R.layout.title_dialog_expand_view, null);
		View shadowView = contentView.findViewById(R.id.v_shadow_title_expand_dialog);
		content = (ViewGroup) contentView.findViewById(R.id.ll_content_title_expand_dialog);
		shadowView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dismiss();
			}
		});
		dialog = new CustomPopupWindow(contentView);
		dialog.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		dialog.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		dialog.setAnimationStyle(0);
		dialog.setIntercepterDismissListener(intercepterDismissListener); 
		dialog.setBackgroundDrawable(new BitmapDrawable());
		dialog.setFocusable(true);
		dialog.setOutsideTouchable(true);
	}
	
	private void rotateArrow() {

		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.ronate_0_180);
		arrowIv.startAnimation(animation);
	}
	
	private void ronateArrowBack() {

		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.ronate_180_360);
		arrowIv.startAnimation(animation);
	}
	
	private CustomPopupWindow.IntercepterDismissListener intercepterDismissListener = new CustomPopupWindow.IntercepterDismissListener() {
		
		@Override
		public void onIntercept() {
			
			if(!dialog.isShowing()){
				return;
			}
			if(onDismissListener !=null) {
				onDismissListener.onDismiss();
			}
			Animation animation = AnimationUtils.loadAnimation(context,
					R.anim.pophidden_anim);
			content.startAnimation(animation);
			ronateArrowBack();
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if(dialog!=null) {
						dialog.superDismiss();
					}
				}
			}, 300L);
		}
	};
	
	void addItem(String title, final OnClickListener onClickListener) {
		
		View item = LayoutInflater.from(context).inflate(R.layout.title_dialog_item_expand_view, null);
		TextView itemTv = (TextView) item.findViewById(R.id.tv_title_tdiev);
		itemTv.setText(title);
		content.addView(item);
		item.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				selectedPosition = getIndexOfContent(v, content);
				onClickListener.onClick(v);
				changeItemColor(v);
				TextView clickedTv = (TextView) v.findViewById(R.id.tv_title_tdiev);
				titleTv.setText(clickedTv.getText().toString());
				intercepterDismissListener.onIntercept();
			}
		});
	}
	
	private int getIndexOfContent(View v, ViewGroup content){
		
		if(v == null||content ==null){
			return 0;
		}
		int count = content.getChildCount();
		for(int i =0 ; i <count ; i++) {
			View item = content.getChildAt(i);
			if(item == v) {
				return i;
			}
		}
		return 0;
	}
	
	private void changeItemColor(View clickedItemView) {
		
		int count = content.getChildCount();
		for(int i =0 ; i< count ; i++) {
			View item = content.getChildAt(i);
			TextView itemTv = (TextView) item.findViewById(R.id.tv_title_tdiev);
			itemTv.setTextColor(context.getResources().getColorStateList(R.color.text_drop_down_menu_unselected));
		}
		TextView clickedTv = (TextView) clickedItemView.findViewById(R.id.tv_title_tdiev);
		clickedTv.setTextColor(context.getResources().getColorStateList(R.color.text_drop_down_menu));
	}
	
	public void changeItemColor(int position) {
		
		View view = content.getChildAt(position);
		changeItemColor(view);
	}
	
	
	void refreshBadge(int position, boolean isShow) {
		
		if(dialog==null) {
			return;
		}
		View itemView = content.getChildAt(position);
		ImageView badgeItemIv = (ImageView) itemView.findViewById(R.id.iv_badge_tdiev);
		if(isShow){
			badgeItemIv.setVisibility(View.VISIBLE);
			badgeIv.setVisibility(View.VISIBLE);
		} else {
			badgeItemIv.setVisibility(View.GONE);
			badgeIv.setVisibility(View.GONE);
		} 
	}

	void performClickAtPosition(int position) {
		
		View itemView = content.getChildAt(position);
		itemView.performClick();
	}


	int getSelectedPosition() {
		
		return selectedPosition;
	}
	
}
