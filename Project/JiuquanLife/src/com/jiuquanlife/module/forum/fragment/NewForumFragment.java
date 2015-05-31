package com.jiuquanlife.module.forum.fragment;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.PostAdapter;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.forum.PostListVo;

public class NewForumFragment extends ForumBaseFragment{
	
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
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", "1");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.GET_NEW_FORUM_LIST, map,new Listener<String>() {

					@Override
					public void onResponse(String response) {

						PostListVo info = GsonUtils.toObj(response,
								PostListVo.class);
						if (info == null || info.list == null) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						postAdapter.refresh(info.list);
					}
				});
	}
	
}
