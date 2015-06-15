package com.jiuquanlife.module.forum.fragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.http.RequestHelper.OnFinishListener;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.adapter.TopicListAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.vo.forum.TopicListVo;

public class TopicListFragment extends BaseFragment {

	private XListView xlv_topic_list;
	private TopicListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_topic_list, null);
		setContent(content);
		initViews();
		getData();
		return content;
	}

	private void initViews() {

		xlv_topic_list = (XListView) findViewById(R.id.xlv_topic_list);
		xlv_topic_list.setPullRefreshEnable(true);
		xlv_topic_list.setPullLoadEnable(false);
		adapter = new TopicListAdapter(getActivity());
		xlv_topic_list.setAdapter(adapter);
		xlv_topic_list.setOnItemClickListener(adapter);
		xlv_topic_list.setXListViewListener(xListViewListener);
	}

	private XListView.IXListViewListener xListViewListener = new XListView.IXListViewListener() {

		@Override
		public void onRefresh() {

			xlv_topic_list.setRefreshing();
			getData();
		}

		@Override
		public void onLoadMore() {

		}
	};

	public void getData() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("r", "forum/forumlist");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						TopicListVo info = GsonUtils.toObj(response,
								TopicListVo.class);
						if (info == null || info.list == null
								|| info.list.isEmpty()) {
							return;
						}
						info.list.remove(5);
						adapter.refresh(info.list);
					}
				}, new OnFinishListener() {

					@Override
					public void onFinish() {
						xlv_topic_list.stopRefresh();
					}
				});
	}
}
