package com.jiuquanlife.module.forum.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.ProfileAdapter;
import com.jiuquanlife.vo.forum.usercenter.UserData;

public class ProfileAcitivity extends BaseActivity{
	
	public static final String EXTRA_DATA = "EXTRA_DATA";
	private ListView lv_profile;
	
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
		
	}
	
	private void initData() {
		
		ArrayList<UserData> profileList = (ArrayList<UserData>) getIntent().getSerializableExtra(EXTRA_DATA);
		ProfileAdapter adapter =new ProfileAdapter(getActivity());
		lv_profile.setAdapter(adapter);
		adapter.refresh(profileList);
	}
	
}
