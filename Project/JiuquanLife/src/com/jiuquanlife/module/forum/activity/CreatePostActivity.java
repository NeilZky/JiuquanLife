package com.jiuquanlife.module.forum.activity;

import android.os.Bundle;
import android.view.View;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;

public class CreatePostActivity extends BaseActivity{
	
	public static final String EXTRA_CREATE_TYPE = "EXTRA_CREATE_TYPE";
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_LOCAL_PHOTO = 1;
	public static final int TYPE_CAMERA = 2;
	
	private static final int REQUEST_SELECT_TOPIC = 1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_create_post);
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_select_topic:
			onClickSelectTopic();
			break;

		default:
			break;
		}
	}

	private void onClickSelectTopic() {
		
		startActivityForResult(SelectTopicActivity.class, REQUEST_SELECT_TOPIC);
	}
}
