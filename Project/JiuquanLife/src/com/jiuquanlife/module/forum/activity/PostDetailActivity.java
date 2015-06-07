package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import android.os.Bundle;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.PostDetailAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.vo.forum.PostDetailVo;

public class PostDetailActivity extends BaseActivity{
	
	public static final String EXTRA_TOPIC_ID = "EXTRA_TOPIC_ID";
	
	private XListView xlv_apd;
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
		xlv_apd = (XListView) findViewById(R.id.xlv_apd);
		xlv_apd.setPullLoadEnable(false);
		xlv_apd.setPullRefreshEnable(true);
		postDetailAdapter = new PostDetailAdapter(getActivity());
		xlv_apd.setAdapter(postDetailAdapter);
		xlv_apd.setXListViewListener(xListViewListener);
	}
	
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
		getData();
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
							xlv_apd.setPullLoadEnable(true);
						} else {
							xlv_apd.setPullLoadEnable(false);
						}
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						xlv_apd.stopRefresh();
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
							xlv_apd.setPullLoadEnable(true);
						} else {
							xlv_apd.setPullLoadEnable(false);
						}
						postDetailAdapter.add(info.list);
						
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						xlv_apd.stopLoadMore();
					}
				});
	}
	
	
}
