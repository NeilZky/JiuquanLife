package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.UserListAdapter;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.MulityLocationManager;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.ToastHelper;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.view.xlistview.XListView.IXListViewListener;
import com.jiuquanlife.vo.forum.usercenter.UserInfo;
import com.jiuquanlife.vo.forum.usercenter.UserInfoJson;

public class SelectFirendActivity extends BaseActivity{
	
	public static final String EXTRA_USER_INFO = "EXTRA_USER_INFO";
	
	public static final int TYPE_FIREND = 1;//好友
	public static final int TYPE_ALL = 2;//附近
	public static final int TYPE_FOLLOW = 3;//关注
	public static final int TYPE_FOLLOWED = 4;//粉丝
	public static final int TYPE_RANK = 5;//粉丝
	private XListView xlv_user_list;
	private UserListAdapter userListAdapter;
	private String orderBy = "dateline";
	private TextView tv_title_user_list;
	private String url;
	private int page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}
	
	
	private void init() {
		
		initViews();
		xlv_user_list.setRefreshing();
		getData();

	}
	


	private void initViews() {
		
		setContentView(R.layout.activity_user_list);
		xlv_user_list = (XListView) findViewById(R.id.xlv_user_list);
		xlv_user_list.setPullRefreshEnable(true);
		xlv_user_list.setPullLoadEnable(false);
		xlv_user_list.setXListViewListener(xListener);
		userListAdapter = new UserListAdapter(getActivity());
		xlv_user_list.setAdapter(userListAdapter);
		tv_title_user_list = (TextView) findViewById(R.id.tv_title_user_list);
		tv_title_user_list.setText("好友列表");
		xlv_user_list.setOnItemClickListener(onItemClickListener);
	}
	
	private XListView.IXListViewListener xListener = new IXListViewListener() {
		
		@Override
		public void onRefresh() {
			
			getData();

		}
		
		@Override
		public void onLoadMore() {
			
			addData();
		}
	};
	
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			UserInfo data = (UserInfo) parent.getItemAtPosition(position);
			if(data!=null) {
				Intent intent = new Intent();
				intent.putExtra(EXTRA_USER_INFO, data);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		}
	};
	
	private void getData() {
		page = 1;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userlist");
		map.put("page", page+"");
		map.put("pageSize", 20+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		map.put("orderBy", orderBy);
		map.put("type", "friend");
		String postUrl = url;
		if(postUrl == null) {
			postUrl = UrlConstance.FORUM_URL;
		} else {
			map = null;
		}
		RequestHelper.getInstance().getRequestMap(getActivity(),
				postUrl, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						UserInfoJson json = GsonUtils.toObj(response, UserInfoJson.class);
						if(json!=null) {
							userListAdapter.refresh(json.list);
							xlv_user_list.setPullLoadEnable(json.has_next ==1);
							if(json.list == null || json.list.isEmpty()) {
								xlv_user_list.setPullLoadEnable(false);
								ToastHelper.showS("没有数据");
							}
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						xlv_user_list.stopRefresh();
					}
				});
	}
	
	private void addData() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userlist");
		page++;
		map.put("page", page+"");
		map.put("pageSize", 20+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		map.put("orderBy", orderBy);
		map.put("type", "friend");
		String postUrl = url;
		if(postUrl == null) {
			postUrl = UrlConstance.FORUM_URL;
		} else {
			map = null;
		}
		RequestHelper.getInstance().getRequestMap(getActivity(),
				postUrl, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						UserInfoJson json = GsonUtils.toObj(response, UserInfoJson.class);
						if(json!=null) {
							userListAdapter.add(json.list);
							xlv_user_list.setPullLoadEnable(json.has_next ==1);
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						xlv_user_list.stopLoadMore();
					}
				});
	}


}
