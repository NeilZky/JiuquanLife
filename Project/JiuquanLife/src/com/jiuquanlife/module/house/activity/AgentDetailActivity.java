package com.jiuquanlife.module.house.activity;

import android.os.Bundle;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.vo.house.out.AgentDetailOut;

public class AgentDetailActivity extends BaseActivity{
	
	private String id;
	public static final String INTENT_KEY_AGENT_ID = "INTENT_KEY_AGENT_ID";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		
		initViews();
		initData();
		getData();
	}
	


	private void initViews() {
		
		setContentView(R.layout.activity_agent_detail);
	}

	private void initData() {
		
		id = getIntent().getStringExtra(INTENT_KEY_AGENT_ID);
	}
	
	private void getData() {
		
		AgentDetailOut ado = new AgentDetailOut();
		ado.id = id;
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		if(user!=null) {
			ado.uid = String.valueOf(user.uid);
		}
		RequestHelper.getInstance().getRequestEntity(getActivity(), UrlConstance.AGENT_DETAIL, ado, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				ToastHelper.showL(response);
			}
		});
		
	}
	
}
