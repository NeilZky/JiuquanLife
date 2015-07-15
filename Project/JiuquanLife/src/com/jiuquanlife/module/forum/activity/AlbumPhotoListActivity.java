package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.AlbumPhotoAdapter;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.vo.forum.album.AlbumData;
import com.jiuquanlife.vo.forum.photo.AlbumPhotoJson;

public class AlbumPhotoListActivity extends BaseActivity{

	public static final String EXTRA_ALBUM = "EXTRA_ALBUM";
	
	private PullToRefreshView ptrv_album;
	private GridView gv_album;
	private TextView title_album_photo_list;
	private int page = 1;
	private AlbumPhotoAdapter albumPhotoAdapter;
	private AlbumData albumData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
		albumData = (AlbumData) getIntent().getSerializableExtra(EXTRA_ALBUM);
		if(albumData!=null) {
			TextViewUtils.setText(title_album_photo_list, albumData.title);
		}
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
		title_album_photo_list = (TextView) findViewById(R.id.title_album_photo_list);
		ptrv_album = (PullToRefreshView) findViewById(R.id.ptrv_album);
		gv_album = (GridView) findViewById(R.id.gv_album);
		albumPhotoAdapter = new AlbumPhotoAdapter(getActivity());
		gv_album.setAdapter(albumPhotoAdapter);
		ptrv_album.setPullDownEnable(true);
		ptrv_album.setPullUpEnable(false);
		ptrv_album.setOnHeaderRefreshListener(onHeaderRefreshListener);
		ptrv_album.setOnFooterRefreshListener(onFooterRefreshListener);
	}
	
	public void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/photolist");
		page = 1;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		map.put("albumId", albumData.album_id + "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						AlbumPhotoJson json = GsonUtils.toObj(response, AlbumPhotoJson.class);
						if(json!=null) {
							albumPhotoAdapter.refresh(json.list);
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
	
	public void addData() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/photolist");
		page++;
		map.put("page", page+"");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("uid", user.uid + "");
		map.put("albumId", albumData.album_id + "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						AlbumPhotoJson json = GsonUtils.toObj(response, AlbumPhotoJson.class);
						if(json!=null) {
							albumPhotoAdapter.add(json.list);
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
