package com.jiuquanlife.module.forum.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.ProfileAdapter;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.vo.forum.usercenter.UserData;

public class ProfileAcitivity extends BaseActivity{
	
	public static final String EXTRA_DATA = "EXTRA_DATA";
	public static final String EXTRA_TITLE = "EXTRA_TITLE";
	private ListView lv_profile;
	private TextView tv_title_profile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	
	
	private void init() {
		
		initViews();
		initData();
	}


	private void initViews() {
		
		setContentView(R.layout.activity_profile);
		lv_profile = (ListView) findViewById(R.id.lv_profile);
		tv_title_profile = (TextView) findViewById(R.id.tv_title_profile);
		
	}
	
	private void initData() {
		
		String title = getIntent().getStringExtra(EXTRA_TITLE);
		TextViewUtils.setText(tv_title_profile, title);
		ArrayList<UserData> profileList = (ArrayList<UserData>) getIntent().getSerializableExtra(EXTRA_DATA);
		ProfileAdapter adapter =new ProfileAdapter(getActivity());
		lv_profile.setAdapter(adapter);
		adapter.refresh(profileList);
	}
	
}
