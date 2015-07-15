package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.UserListAdapter;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.view.xlistview.XListView.IXListViewListener;
import com.jiuquanlife.vo.forum.album.AlbumData;
import com.jiuquanlife.vo.forum.usercenter.UserInfoJson;

public class UserListActivity extends BaseActivity{
	
	public static final String EXTRA_TYPE= "EXTRA_TYPE";
	public static final String EXTRA_UID= "EXTRA_UID";
	public static final String EXTRA_TITLE= "EXTRA_TITLE";
	public static final int TYPE_FIREND = 1;//ºÃÓÑ
	public static final int TYPE_ALL = 2;//¸½½ü
	public static final int TYPE_FOLLOW = 3;//¹Ø×¢
	public static final int TYPE_FOLLOWED = 4;//·ÛË¿
	private int uid;
	private XListView xlv_user_list;
	private UserListAdapter userListAdapter;
	private int page = 1;
	private String orderBy = "dateline";
	private String type;
	private TextView tv_title_user_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
	}
	
	
	private void init() {
		
		initViews();
		xlv_user_list.setRefreshing();
		initData();
		getData();
	}
	
	
	
	private void initData() {
		
		int queryType = getIntent().getIntExtra(EXTRA_TYPE, 0);
		switch (queryType) {
		case TYPE_FIREND:
			type = "friend";
			break;
		case TYPE_FOLLOW:
			type = "follow";
			break;
		case TYPE_FOLLOWED:
			type = "followed";
			break;
		case TYPE_ALL:
			type = "all";
			orderBy = "distance";
			break;
		}
		uid = getIntent().getIntExtra(EXTRA_UID, 0);
		tv_title_user_list.setText(getIntent().getStringExtra(EXTRA_TITLE));
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
			AlbumData albumData = (AlbumData) parent.getItemAtPosition(position);
			if(albumData!=null) {
				Intent intent = new Intent(getActivity(), AlbumPhotoListActivity.class);
				intent.putExtra(AlbumPhotoListActivity.EXTRA_ALBUM, albumData);
				startActivity(intent);
			}
		}
	};
	
	private void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userlist");
		page = 1;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", uid + "");
		map.put("orderBy", orderBy);
		map.put("type", type);
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						UserInfoJson json = GsonUtils.toObj(response, UserInfoJson.class);
						if(json!=null) {
							userListAdapter.refresh(json.list);
							xlv_user_list.setPullLoadEnable(json.has_next ==1);
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
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", uid + "");
		map.put("orderBy", orderBy);
		map.put("type", type);
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

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
