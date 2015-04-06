package com.jiuquanlife.module.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;

public class PublishHouseActivity extends BaseActivity{
	
	private LinearLayout rentMenuLl;
	private LinearLayout sellMenuLl;
	private LinearLayout applyRentMenuLl;
	private LinearLayout buyMenuLl;
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_house);
		init();
	}
	 
	 private void init() {
		 
		 initViews();
	}

	private void initViews() {
		
		rentMenuLl = (LinearLayout) findViewById(R.id.ll_menu_rent_house);
		sellMenuLl = (LinearLayout) findViewById(R.id.ll_menu_sell_house);
		applyRentMenuLl = (LinearLayout) findViewById(R.id.ll_menu_apply_rent_house);
		buyMenuLl = (LinearLayout) findViewById(R.id.ll_menu_buy_house);
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_rent_house:
			showSenconaryMenu(rentMenuLl);
			break;
		case R.id.ll_sell_house:
			showSenconaryMenu(sellMenuLl);
			break;
		case R.id.ll_apply_rent_house:
			showSenconaryMenu(applyRentMenuLl);
			break;
		case R.id.ll_buy_house:
			showSenconaryMenu(buyMenuLl);
			break;
		case R.id.btn_sell_secondary_house:
			onClickSellSecondaryHouse();
			break;
		default:
			break;
		}
	}
	
	private void onClickSellSecondaryHouse() {
		
		Intent intent = new Intent(this, PublishSecondaryHouseActivity.class);
		startActivity(intent);
		
	}

	private void showSenconaryMenu(View view) {
		
		closeOtherMenu(view);
		if(view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
		} else{
			view.setVisibility(View.VISIBLE);
		}
	}
	
	private void closeOtherMenu(View view) {
		
		ViewGroup parent = (ViewGroup)view.getParent();
		for(int i = 0 ; i < parent.getChildCount(); i ++) {
			View child = parent.getChildAt(i);
			if(child != view) {
				child.setVisibility(View.GONE);
			}
		}
	}
	
}
