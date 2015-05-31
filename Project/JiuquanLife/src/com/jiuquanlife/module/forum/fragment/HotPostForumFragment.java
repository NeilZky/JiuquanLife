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
import com.jiuquanlife.vo.BaseData;
import com.jiuquanlife.vo.PostInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;
import com.jiuquanlife.vo.forum.ForumIndexData;
import com.jiuquanlife.vo.forum.PostListVo;

public class HotPostForumFragment extends ForumBaseFragment{
	
	private ListView lv_essence_forum;
	private PostAdapter postAdapter;

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

		lv_essence_forum = (ListView) findViewById(R.id.lv_essence_forum);
		postAdapter = new PostAdapter(getActivity());
		lv_essence_forum.setAdapter(postAdapter);
	}

	public void getData() {
		RequestHelper.getInstance().getRequest(getActivity(),
				UrlConstance.GET_HOT_FORUM,new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						Type type = new TypeToken<BaseData<ForumIndexData>>() {
						}.getType();
						BaseData<ForumIndexData> info = GsonUtils.toObj(
								response, type);
						if (info == null || info.data == null) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						postAdapter.refresh(ConvertUtils.convertPosts(info.data.hotPosts));
					}
				});
	}
	
}
