package com.jiuquanlife.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jiuquanlife.R;

/**
 * 
 * ¾Û½¹Ò³Ãæ
 *
 */
public class FocusActivity extends BaseActivity{

	private ViewPager topVp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_focus);
		topVp = (ViewPager) findViewById(R.id.vp_top_focus);
	}
	
	
}
