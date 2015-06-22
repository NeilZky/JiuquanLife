package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.http.RequestHelper.OnFinishListener;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.TopicListAdapter;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.view.xlistview.XListView;
import com.jiuquanlife.vo.forum.TopicListVo;

public class SelectTopicActivity extends BaseActivity{
	
	private XListView xlv_topic_list;
	private TopicListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		initViews();
		getData();
	}

	private void initViews() {
		
		setContentView(R.layout.activity_select_topic_list);
		xlv_topic_list = (XListView) findViewById(R.id.xlv_topic_list);
		xlv_topic_list.setPullRefreshEnable(true);
		xlv_topic_list.setPullLoadEnable(false);
		adapter = new TopicListAdapter(getActivity());
		adapter.setSelectBorder(true);
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
