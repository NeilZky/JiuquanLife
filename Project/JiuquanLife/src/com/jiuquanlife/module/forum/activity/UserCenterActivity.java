package com.jiuquanlife.module.forum.activity;

import android.os.Bundle;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;

public class UserCenterActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
	}

	private void initViews() {
	
		setContentView(R.layout.activity_user_center);
	}
	
}
