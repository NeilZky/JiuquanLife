package com.jiuquanlife.module.forum.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PostAdapter;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.view.xlistview.XListView.IXListViewListener;
import com.jiuquanlife.vo.BaseData;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.jiuquanlife.vo.forum.ForumIndexData;
import com.jiuquanlife.vo.forum.PostListVo;

public class HotPostForumFragment extends ForumBaseFragment{
	
	private XListView lv_essence_forum;
	private PostAdapter postAdapter;
	private int page;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = getContent();
		if (content == null) {
			content = inflater.inflate(R.layout.fragment_forum_essence, null);
			setContent(content);
			init();
		}
		ViewGroup parent = (ViewGroup) content.getParent();
		if (parent != null) {
			parent.removeView(content);
		}
		return content;
	}

	private void init() {

		initViews();
		getData();
	}

	private void initViews() {

		lv_essence_forum = (XListView) findViewById(R.id.lv_essence_forum);
		postAdapter = new PostAdapter(getActivity());
		lv_essence_forum.setAdapter(postAdapter);
		lv_essence_forum.setPullRefreshEnable(true);
		lv_essence_forum.setPullLoadEnable(false);
		lv_essence_forum.setXListViewListener(xListViewListener);
		lv_essence_forum.setOnItemClickListener(postAdapter);
	}
	
	private XListView.IXListViewListener xListViewListener = new IXListViewListener() {
		
		@Override
		public void onRefresh() {
			
			getData();
		}
		
		@Override
		public void onLoadMore() {
			
			addData();
		}
	};
	
	public void getData() {
		page = 1;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.GET_HOT_FORUM, map,new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						Type type = new TypeToken<BaseData<ForumIndexData>>() {
						}.getType();
						BaseData<ForumIndexData> info = GsonUtils.toObj(
								response, type);
						if (info == null || info.data == null) {
							// 请求数据失败
							return;
						}
						lv_essence_forum.setPullLoadEnable(true);
						postAdapter.refresh(ConvertUtils.convertPosts(info.data.hotPosts));
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						lv_essence_forum.stopRefresh();
					}
				});
	}

	
	public void addData() {
		page++;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.GET_HOT_FORUM, map,new Listener<String>() {

					@Override
					public void onResponse(String response) {

						Type type = new TypeToken<BaseData<ForumIndexData>>() {
						}.getType();
						BaseData<ForumIndexData> info = GsonUtils.toObj(
								response, type);
						if (info == null || info.data == null) {
							// 请求数据失败
							return;
						}
						postAdapter.addList(ConvertUtils.convertPosts(info.data.hotPosts));
						
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						lv_essence_forum.stopLoadMore();
					}
				});
	}
	
}
