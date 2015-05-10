package com.jiuquanlife.module.forum.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.focus.adapter.JhtAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.BaseData;
import com.jiuquanlife.vo.PostInfo;

public class EssenceForumFragment extends ForumBaseFragment {

	private ListView lv_essence_forum;
	private JhtAdapter jhtAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View content = getContent();
		if (content == null) {
			content = inflater.inflate(R.layout.fragment_forum_essence,
					null);
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
		jhtAdapter = new JhtAdapter(getActivity());
		lv_essence_forum.setAdapter(jhtAdapter);
	}

	public void getData() {

		RequestHelper.getInstance().getRequest(getActivity(),
				UrlConstance.GET_ESSENCE_FORUM_LIST, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						Type type = new TypeToken<BaseData<ArrayList<PostInfo>>>() {
						}.getType();
						BaseData<ArrayList<PostInfo>> info = GsonUtils.toObj(
								response, type);
						if (info == null
								|| info.data == null
								|| !CommonConstance.REQUEST_CODE_SUCCESS
										.equals(info.code)) {
							// ÇëÇóÊý¾ÝÊ§°Ü
							return;
						}
						jhtAdapter.refresh(info.data);
					}
				});
	}

}
