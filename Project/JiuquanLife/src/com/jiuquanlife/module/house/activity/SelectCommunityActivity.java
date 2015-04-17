package com.jiuquanlife.module.house.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.house.adapter.CommunityAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.house.Community;
import com.jiuquanlife.vo.house.CommunityInfo;

public class SelectCommunityActivity extends BaseActivity {

	public static final String INTENT_KEY_AID = "INTENT_KEY_AID";
	public static final String RESULT_DATA_COMUUNITY = "RESULT_DATA_COMUUNITY";
	private static final int REQUEST_SUB_AREA = 1;

	private ListView lv_activity_select_community;
	private CommunityAdapter communityAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {

		initViews();
		initData();
	}

	private void initViews() {

		setContentView(R.layout.activity_select_community);
		lv_activity_select_community = (ListView) findViewById(R.id.lv_activity_select_community);
		communityAdapter = new CommunityAdapter(this);
		lv_activity_select_community.setAdapter(communityAdapter);
		lv_activity_select_community.setOnItemClickListener(onItemClickListener);
	}

	private void initData() {

		String aid =getIntent().getStringExtra(INTENT_KEY_AID);
		if(aid!=null) {
			getData(aid);
		}
	}
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Community community = communityAdapter.getItem(position);
			Intent data = new Intent();
			data.putExtra(RESULT_DATA_COMUUNITY, community);
			setResult(Activity.RESULT_OK, data);
			finish();
		}
	};
	
	private void getData(String aid) {

		RequestHelper.getInstance().getRequest(getActivity(),
				UrlConstance.GET_COMMUNITY_BY_ADDRESS_ID + aid, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						CommunityInfo info = GsonUtils.toObj(response,
								CommunityInfo.class);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						communityAdapter.refresh(info.data);
					}
				});
	}

}
