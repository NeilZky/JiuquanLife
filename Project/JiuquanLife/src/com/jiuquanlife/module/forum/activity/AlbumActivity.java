package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.AlbumAdapter;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.vo.forum.album.AlbumData;
import com.jiuquanlife.vo.forum.album.AlbumJson;

public class AlbumActivity extends BaseActivity{
	
	private PullToRefreshView ptrv_album;
	private GridView gv_album;
	private int page = 1;
	private AlbumAdapter albumAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	
	
	private void init() {
		
		initViews();
		ptrv_album.setRefreshing();
	}
	
	
	private PullToRefreshView.OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {
		
		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			getData();
		}
	};
	
	private PullToRefreshView.OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {
		
		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			
			addData();
		}
	};
	
	
	private void initViews() {
		
		setContentView(R.layout.activity_album);
		ptrv_album = (PullToRefreshView) findViewById(R.id.ptrv_album);
		gv_album = (GridView) findViewById(R.id.gv_album);
		albumAdapter = new AlbumAdapter(getActivity());
		gv_album.setAdapter(albumAdapter);
		gv_album.setOnItemClickListener(onItemClickListener);
		ptrv_album.setPullDownEnable(true);
		ptrv_album.setPullUpEnable(false);
		ptrv_album.setOnHeaderRefreshListener(onHeaderRefreshListener);
		ptrv_album.setOnFooterRefreshListener(onFooterRefreshListener);
	}
	
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
		map.put("r", "user/albumlist");
		page = 1;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						AlbumJson json = GsonUtils.toObj(response, AlbumJson.class);
						if(json!=null) {
							albumAdapter.refresh(json.list);
							ptrv_album.setPullUpEnable(json.has_next ==1);
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						ptrv_album.onHeaderRefreshComplete();
					}
				});
	}
	
	private void addData() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/albumlist");
		page++;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						AlbumJson json = GsonUtils.toObj(response, AlbumJson.class);
						if(json!=null) {
							albumAdapter.add(json.list);
							ptrv_album.setPullUpEnable(json.has_next ==1);
						}
						
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						ptrv_album.onFooterRefreshComplete();
					}
				});
	}
}
