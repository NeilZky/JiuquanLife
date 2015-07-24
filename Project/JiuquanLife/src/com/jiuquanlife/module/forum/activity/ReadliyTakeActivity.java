package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.os.Bundle;

import com.android.volley.Response.Listener;
import com.etsy.android.grid.StaggeredGridView;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.ReadilyTakeAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.forum.readliytake.ReadliytakeJson;
import com.jiuquanlife.vo.forum.usercenter.UserInfoJson;

public class ReadliyTakeActivity extends BaseActivity{
	
	private StaggeredGridView sgv_readliy_take;
	private ReadilyTakeAdapter adapter;
	private int page;
	
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
		
		setContentView(R.layout.activity_readily_take);
		sgv_readliy_take = (StaggeredGridView) findViewById(R.id.sgv_readliy_take);
		adapter = new ReadilyTakeAdapter(getActivity());
		sgv_readliy_take.setAdapter(adapter);
	}
	
	private void initData() {
		
		getData();
	}
	
	
	private void getData() {
	
		HashMap<String, String> map = new HashMap<String, String>();
		page = 1;
		map.put("page", page+"");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_READLIY_TAKE, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						ReadliytakeJson json = GsonUtils.toObj(response, ReadliytakeJson.class);
						if(json!=null && json.data!=null && json.data.postList!=null) {
							adapter.refresh(json.data.postList);
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}
	
	private void addData() {
		
		page++;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", page+"");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_READLIY_TAKE, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						UserInfoJson json = GsonUtils.toObj(response, UserInfoJson.class);
						if(json!=null) {
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}

	
}
