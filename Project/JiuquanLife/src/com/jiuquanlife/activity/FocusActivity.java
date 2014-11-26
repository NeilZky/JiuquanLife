package com.jiuquanlife.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.activity.adapter.FocusTopAdapter;
import com.jiuquanlife.vo.PhotoInfo;

/**
 * 
 * ¾Û½¹Ò³Ãæ
 *
 */
public class FocusActivity extends BaseActivity{

	private ViewPager topVp;
	private FocusTopAdapter focusTopAdapter;
	private LinearLayout dotLl;
	private TextView vpTitleTv;
	
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
		dotLl = (LinearLayout) findViewById(R.id.ll_dot_top_focus);
		vpTitleTv = (TextView) findViewById(R.id.tv_vp_title_focus);
		focusTopAdapter = new FocusTopAdapter(FocusActivity.this, dotLl, topVp, vpTitleTv);
		topVp.setAdapter(focusTopAdapter);
		topVp.setOnPageChangeListener(focusTopAdapter);
		ArrayList<PhotoInfo> photoInfos = new ArrayList<PhotoInfo>();
		photoInfos.add(new PhotoInfo("http://bbs.unpcn.com/attachment.aspx?attachmentid=3927433", "title1"));
		photoInfos.add(new PhotoInfo("http://bbs.unpcn.com/attachment.aspx?attachmentid=3927433", "title2"));
		focusTopAdapter.setPhotoInfos(photoInfos);
	}
	
	
}
