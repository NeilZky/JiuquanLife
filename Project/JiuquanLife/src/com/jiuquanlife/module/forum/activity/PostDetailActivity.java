package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.PostDetailAdapter;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.view.pulltorefresh.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.vo.forum.PostDetailVo;
import com.jiuquanlife.vo.forum.PostItem;

public class PostDetailActivity extends BaseActivity{
	
	public static final String EXTRA_TOPIC_ID = "EXTRA_TOPIC_ID";
	
	private PullToRefreshView ptrv_apd;
	private ListView xlv_apd;
	private PostDetailAdapter postDetailAdapter;
	private int page = 1;
	private int topicId;
	
	
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
		
		setContentView(R.layout.activity_post_detail2);
		ptrv_apd = (PullToRefreshView) findViewById(R.id.ptrv_apd);
		xlv_apd = (ListView) findViewById(R.id.xlv_apd);
		ptrv_apd.setPullDownEnable(true);
		ptrv_apd.setPullUpEnable(false);
		postDetailAdapter = new PostDetailAdapter(getActivity());
		xlv_apd.addHeaderView(postDetailAdapter.getPostDetailView());
		xlv_apd.setAdapter(postDetailAdapter);
		ptrv_apd.setOnHeaderRefreshListener(onHeaderRefreshListener);
		ptrv_apd.setOnFooterRefreshListener(onFooterRefreshListener);
	}
	
	private PullToRefreshView.OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {
		
		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			addData();
		}
	};
	
	private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {
		
		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			
			getData();
		}
	};
	
	private XListView.IXListViewListener xListViewListener = new XListView.IXListViewListener() {
		
		@Override
		public void onRefresh() {
			
			getData();
		}
		
		@Override
		public void onLoadMore() {
			
			addData();
		}
	};
	
	private void initData() {
		
		topicId = getIntent().getIntExtra(EXTRA_TOPIC_ID, 0);
		showProgressDialog();
		ptrv_apd.setRefreshing();
	}
	
	public void getData() {
		page = 1;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "forum/postlist");
		map.put("topicId", String.valueOf(topicId));
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map,new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						PostDetailVo info = GsonUtils.toObj(response,
								PostDetailVo.class);
						if (info == null || info.topic== null) {
							return;
						}
						postDetailAdapter.refresh(info.topic, info.list);
						if(info.has_next  == 1) {
							ptrv_apd.setPullUpEnable(true);
						} else {
							ptrv_apd.setPullUpEnable(false);
						}
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						dismissProgressDialog();
						ptrv_apd.onHeaderRefreshComplete();
					}
				});
	}
	
	private void addData() {
	
		page++;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "forum/postlist");
		map.put("topicId", String.valueOf(topicId));
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map,new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						PostDetailVo info = GsonUtils.toObj(response,
								PostDetailVo.class);
						if (info == null ) {
							return;
						}
						if(info.has_next  == 1) {
							ptrv_apd.setPullUpEnable(true);
						} else {
							ptrv_apd.setPullUpEnable(false);
						}
						postDetailAdapter.add(info.list);
						
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						ptrv_apd.onFooterRefreshComplete();
					}
				});
	}
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iv_collect_apd:
			onClickCollect();
			break;

		default:
			break;
		}
	}

	private void onClickCollect() {
		
		if(logined()) {
			collect(topicId);
		}
	}
	
	protected void collect(int topicId) {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userfavorite");
		map.put("action", "favorite");
		map.put("id", String.valueOf(topicId));
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		RequestHelper.getInstance().getRequestMap(this, UrlConstance.FORUM_URL, map, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				
				System.out.println(response);
				//TODO ¸ü¸ÄÊÕ²Ø×´Ì¬
			}
		});
	}

	private boolean logined() {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		if(user == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return false;
		} 
		return true;
	}
	
	
}
