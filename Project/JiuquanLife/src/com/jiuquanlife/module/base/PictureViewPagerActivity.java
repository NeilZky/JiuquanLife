package com.jiuquanlife.module.base;

import java.util.ArrayList;

import android.os.Bundle;

import com.jiuquanlife.R;
import com.jiuquanlife.view.touchview.ExtendedViewPager;
import com.jiuquanlife.view.touchview.TouchImageAdapter;

public class PictureViewPagerActivity extends BaseActivity{
	
	public static final String EXTRA_IMAGE_URLS = "EXTRA_IMAGE_URLS";
	private ExtendedViewPager evp_apvp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		setContentView(R.layout.activity_picture_view_pager);
		evp_apvp = (ExtendedViewPager) findViewById(R.id.evp_apvp);
		TouchImageAdapter adapter = new TouchImageAdapter();
		ArrayList<String> imageUrls = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_IMAGE_URLS);
		adapter.refresh(imageUrls);
		evp_apvp.setAdapter(adapter);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
}
