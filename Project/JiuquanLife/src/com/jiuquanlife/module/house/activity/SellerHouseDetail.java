package com.jiuquanlife.module.house.activity;

import android.os.Bundle;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;

public class SellerHouseDetail extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_seller_house_detail);
	}
	
}
