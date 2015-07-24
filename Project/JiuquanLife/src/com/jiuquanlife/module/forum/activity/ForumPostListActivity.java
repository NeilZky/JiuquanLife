package com.jiuquanlife.module.forum.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.fragment.PostListFragment;
import com.jiuquanlife.utils.TextViewUtils;

/**
 * Ìû×ÓÁÐ±í
 *
 */
public class ForumPostListActivity extends BaseActivity{
	
	public static final String EXTRA_TITLE = "EXTRA_TITLE";
	public static final String EXTRA_TYPE = "EXTRA_TYPE";
	public static final String EXTRA_URL_R = "EXTRA_URL_R";
	public static final String EXTRA_UID = "EXTRA_UID";
	
	private TextView titleTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		setContentView(R.layout.activity_forum_post_list);
		titleTv = (TextView) findViewById(R.id.tv_title_forum_post_list);
		String title = getIntent().getStringExtra(EXTRA_TITLE);
		TextViewUtils.setText(titleTv, title);
		PostListFragment fragment = new PostListFragment();
		String type = getIntent().getStringExtra(EXTRA_TYPE);
		fragment.setType(type);
		String r = getIntent().getStringExtra(EXTRA_URL_R);
		fragment.setR(r);
		int uid = getIntent().getIntExtra(EXTRA_UID, 0);
		fragment.setUid(uid);
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_content_forum_post_list, fragment).commit();
	}
	
}
