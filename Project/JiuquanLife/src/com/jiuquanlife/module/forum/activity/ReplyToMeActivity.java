package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.os.Bundle;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.ReplyToMeAdapter;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.vo.forum.replytome.ReplyToMeJson;

public class ReplyToMeActivity extends BaseActivity{
	
	private XListView xlv_reply_to_me;
	private int page;
	private ReplyToMeAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	
	
	private void init() {
		
		initViews();
		xlv_reply_to_me.setRefreshing();
		getData();
	}
	
	private void initViews() {
		
		setContentView(R.layout.activity_reply_to_me);
		xlv_reply_to_me = (XListView) findViewById(R.id.xlv_reply_to_me);
		xlv_reply_to_me.setPullRefreshEnable(true);
		xlv_reply_to_me.setPullLoadEnable(false);
		xlv_reply_to_me.setXListViewListener(xListener);
		adapter = new ReplyToMeAdapter(getActivity());
		xlv_reply_to_me.setAdapter(adapter);
	}
	
	private XListView.IXListViewListener xListener = new XListView.IXListViewListener() {
		
		@Override
		public void onRefresh() {
			
			getData();
		}
		
		@Override
		public void onLoadMore() {
			
			addData();
		}
	};
	
	
	
	private void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "message/notifylist");
		page = 1;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid+ "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						ReplyToMeJson json = GsonUtils.toObj(response, ReplyToMeJson.class);
						if(json!=null) {
							adapter.refresh(json.list);
							xlv_reply_to_me.setPullLoadEnable(json.has_next ==1);
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						xlv_reply_to_me.stopRefresh();
					}
				});
	}
	
	private void addData() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "message/notifylist");
		page++;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid+ "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						ReplyToMeJson json = GsonUtils.toObj(response, ReplyToMeJson.class);
						if(json!=null) {
							adapter.add(json.list);
							xlv_reply_to_me.setPullLoadEnable(json.has_next ==1);
						}
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						xlv_reply_to_me.stopLoadMore();
					}
				});
	}
}
