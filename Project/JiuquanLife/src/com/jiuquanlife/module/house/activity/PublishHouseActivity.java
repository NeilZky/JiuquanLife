package com.jiuquanlife.module.house.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.constance.ActionRelationConstance;
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
		initExpandBtns(R.id.ll_menu_sell_house, ActionRelationConstance.SELL);
		initExpandBtns(R.id.ll_menu_rent_house,  ActionRelationConstance.RENT);
		initExpandBtns(R.id.ll_menu_apply_rent_house,  ActionRelationConstance.APPLY_RENT);
		initExpandBtns(R.id.ll_menu_buy_house,  ActionRelationConstance.APPLY_BUY);
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
		default:
			break;
		}
	}
	
	private void initExpandBtns(int menuResId, String actionRelation) {
		ViewGroup viewGroup = (ViewGroup) findViewById(menuResId);
		int count = viewGroup.getChildCount();
		for(int i =0 ; i<count ; i++) {
			View ll = viewGroup.getChildAt(i);
			if(ll instanceof LinearLayout) {
				initExpandBtns((ViewGroup)ll, actionRelation);
			}
		}
	}
	
	private void initExpandBtns(ViewGroup viewGroup, final String actionRelation) {
		
		int count = viewGroup.getChildCount();
		for(int i =0 ; i<count ; i++) {
			View btn = viewGroup.getChildAt(i);
			if(btn instanceof Button) {
				btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						TextView tv = (TextView) v; 
						String text = tv.getText().toString().trim();
						Intent intent = new Intent(getActivity(), PublishSecondaryHouseActivity.class);
						intent.putExtra(PublishSecondaryHouseActivity.EXTRA_ACTION_TYPE, text);
						intent.putExtra(PublishSecondaryHouseActivity.EXTRA_ACTION_RELATION, actionRelation);
						startActivity(intent);					
						}
				});
			}
		}
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
