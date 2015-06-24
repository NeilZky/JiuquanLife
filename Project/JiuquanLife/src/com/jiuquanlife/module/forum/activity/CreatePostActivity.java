package com.jiuquanlife.module.forum.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.MulityLocationManager;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.MulityLocationManager.OnLocationChangedListener;

public class CreatePostActivity extends BaseActivity{
	
	public static final String EXTRA_CREATE_TYPE = "EXTRA_CREATE_TYPE";
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_LOCAL_PHOTO = 1;
	public static final int TYPE_CAMERA = 2;
	
	private static final int REQUEST_SELECT_TOPIC = 1;
	
	private TextView tv_addr_create_post;
	
	private MulityLocationManager mulityLocationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
		requestLoc();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_create_post);
		tv_addr_create_post = (TextView) findViewById(R.id.tv_addr_create_post);
		mulityLocationManager = MulityLocationManager.getInstance(getApplicationContext());
		mulityLocationManager.setOnLocationChangedListener(onLocationChangedListener);
	}
	
	private OnLocationChangedListener onLocationChangedListener = new OnLocationChangedListener() {
		
		@Override
		public void onLocationChanged(double latitude, double longitude,
				double accyarcy, String addr) {
			TextViewUtils.setText(tv_addr_create_post, addr);
		}
	};
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_select_topic:
			onClickSelectTopic();
			break;
		case R.id.ll_request_loc:
			requestLoc();
			break;
		default:
			break;
		}
	}

	private void requestLoc() {
		
		tv_addr_create_post.setText("");
		mulityLocationManager.requestLocation();
		
	}

	private void onClickSelectTopic() {
		
		startActivityForResult(SelectTopicActivity.class, REQUEST_SELECT_TOPIC);
	}
}
