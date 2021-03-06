package com.jiuquanlife.module.forum.fragment;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PostAdapter;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.forum.activity.PostListActivity;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.view.xlistview.XListView.IXListViewListener;
import com.jiuquanlife.vo.forum.Border;
import com.jiuquanlife.vo.forum.PostListVo;

public class PostListFragment extends ForumBaseFragment {

	private XListView lv_essence_forum;
	private PostAdapter postAdapter;
	private int page;
	private String sortBy;
	private Border border;
	private String r;
	private String type;
	private int uid;
	
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
		borderChagned();
	}
	
	public void borderChagned() {
		
		if(getActivity() instanceof PostListActivity) {
			border =((PostListActivity)getActivity()).getBorder();
		}
		lv_essence_forum.setRefreshing();
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
		if(r!=null) {
			map.put("r", r);
			map.put("type", type);
			map.put("uid", uid+"");
			String mAppHash = AppUtils.getAppHash();
			User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
			map.put("accessToken", user.token);
			map.put("accessSecret", user.secret);
			map.put("appHash", mAppHash);
		} else {
			map.put("r", "forum/topiclist");
			map.put("sortby", sortBy);
			map.put("isImageList", String.valueOf(1));
			map.put("boardId", String.valueOf(border.board_id) );
		}
		map.put("page", String.valueOf(page));
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map,new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						System.out.println(response);
						PostListVo info = GsonUtils.toObj(response,
								PostListVo.class);
						if (info == null || info.list == null) {
							// 请求数据失败
							return;
						}
						if(info.has_next == 1) {
							lv_essence_forum.setPullLoadEnable(true);
						} else{
							lv_essence_forum.setPullLoadEnable(false);
						}
						postAdapter.refresh(info.list);
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
		if(r!=null) {
			map.put("r", r);
			map.put("type", type);
			map.put("uid", uid+"");
			String mAppHash = AppUtils.getAppHash();
			User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
			map.put("accessToken", user.token);
			map.put("accessSecret", user.secret);
			map.put("appHash", mAppHash);
		} else {
			map.put("r", "forum/topiclist");
			map.put("sortby", sortBy);
			map.put("isImageList", String.valueOf(1));
			map.put("boardId", String.valueOf(border.board_id) );
		}
		map.put("page", String.valueOf(page));
		
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL,map,new Listener<String>() {

					@Override
					public void onResponse(String response) {

						PostListVo info = GsonUtils.toObj(response,
								PostListVo.class);
						if (info == null || info.list == null) {
							// 请求数据失败
							return;
						}
						postAdapter.addList(info.list);
						if(info.has_next == 1) {
							lv_essence_forum.setPullLoadEnable(true);
						} else{
							lv_essence_forum.setPullLoadEnable(false);
						}
					}
				},
				new RequestHelper.OnFinishListener() {
					
					@Override
					public void onFinish() {
						lv_essence_forum.stopLoadMore();
					}
				});
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public void setR(String r) {
		
		this.r = r;
	}
	
	public void setType(String type) {
		
		this.type = type;
	}
	
	public void setUid(int uid) {
		
		this.uid = uid;
	}
}
