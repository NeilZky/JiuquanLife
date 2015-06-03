package com.jiuquanlife.module.base;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jiuquanlife.R;
import com.jiuquanlife.view.touchview.ExtendedViewPager;
import com.jiuquanlife.view.touchview.TouchImageAdapter;

public class PictureViewPagerActivity extends BaseActivity{
	
	public static final String EXTRA_IMAGE_URLS = "EXTRA_IMAGE_URLS";
	public static final String EXTRA_CURRENT_ITEM = "EXTRA_CURRENT_ITEM";
	
	private ExtendedViewPager evp_apvp;
	private LinearLayout ll_apvp;
	private ProgressBar pb_apvp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		setContentView(R.layout.activity_picture_view_pager);
		evp_apvp = (ExtendedViewPager) findViewById(R.id.evp_apvp);
		ll_apvp = (LinearLayout) findViewById(R.id.ll_apvp);
		pb_apvp = (ProgressBar) findViewById(R.id.pb_apvp);
		TouchImageAdapter adapter = new TouchImageAdapter(this, ll_apvp, pb_apvp);
		ArrayList<String> imageUrls = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_IMAGE_URLS);
		adapter.refresh(imageUrls);
		evp_apvp.setAdapter(adapter);
		evp_apvp.setOnPageChangeListener(adapter);
		int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
		evp_apvp.setCurrentItem(currentItem);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
}
