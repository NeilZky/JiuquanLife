package com.jiuquanlife.module.forum.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Response.Listener;
import com.etsy.android.grid.StaggeredGridView;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.adapter.ReadliyTakeAdapter;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.view.titleexpand.TitleExpandView;
import com.jiuquanlife.vo.forum.readliytake.ReadliyTakePost;
import com.jiuquanlife.vo.forum.readliytake.ReadliyType;
import com.jiuquanlife.vo.forum.readliytake.ReadliytakeJson;
import com.jiuquanlife.vo.house.CommonType;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout.OnRefreshListener;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

public class ReadliyTakeActivity extends BaseActivity implements
		AbsListView.OnScrollListener, OnItemClickListener, OnRefreshListener {

	private StaggeredGridView sgv_readliy_take;
	private ReadliyTakeAdapter adapter;
	private TitleExpandView tev_title_readliy;
	private TitleExpandView tev_order_readliy;
	private TitleExpandView tev_type_readliy;
	private SwipyRefreshLayout srl_sgv_readliy_take;

	private int page;
	private boolean initedExpandVIews;

	public static final int REQUEST_PUBLISH = 1;
	
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

		setContentView(R.layout.activity_readily_take);
		tev_title_readliy = (TitleExpandView) findViewById(R.id.tev_title_readliy);
		tev_order_readliy = (TitleExpandView) findViewById(R.id.tev_order_readliy);
		tev_type_readliy = (TitleExpandView) findViewById(R.id.tev_type_readliy);
		tev_title_readliy.setBelowView(findViewById(R.id.rl_title_readliy));
		tev_order_readliy
				.setBelowView(findViewById(R.id.ll_type_order_readliy));
		tev_type_readliy.setBelowView(findViewById(R.id.ll_type_order_readliy));
		tev_order_readliy.setTextSize(14);
		tev_order_readliy.setTextColor(Color.BLACK);
		tev_order_readliy.setArrow(R.drawable.ic_arrow_filter);
		tev_type_readliy.setTextSize(14);
		tev_type_readliy.setTextColor(Color.BLACK);
		tev_type_readliy.setArrow(R.drawable.ic_arrow_filter);
		// tev_type_readliy.setTextColor(getResources().getColor(R.color.blue_bg));

		tev_title_readliy.setTitle("∑÷¿‡");
		tev_order_readliy.setTitle("≈≈–Ú");
		tev_type_readliy.setTitle("…∏—°");

		sgv_readliy_take = (StaggeredGridView) findViewById(R.id.sgv_readliy_take);
		srl_sgv_readliy_take = (SwipyRefreshLayout) findViewById(R.id.srl_sgv_readliy_take);
		srl_sgv_readliy_take.setDirection(SwipyRefreshLayoutDirection.TOP);
		srl_sgv_readliy_take.setOnRefreshListener(this);
		adapter = new ReadliyTakeAdapter(getActivity());
		sgv_readliy_take.setAdapter(adapter);
		sgv_readliy_take.setOnScrollListener(this);
		sgv_readliy_take.setOnItemClickListener(this);
	}

	private void initExpandViews(ArrayList<CommonType> filterList,
			ArrayList<CommonType> orderList, ArrayList<ReadliyType> typeList) {

		if (!initedExpandVIews) {
			initTitleFilter(tev_title_readliy, filterList);
			initTitleFilter(tev_order_readliy, orderList);
			initTypeFilter(tev_type_readliy, typeList);
			initedExpandVIews = true;
		}
	}

	private void initTypeFilter(final TitleExpandView tev,
			ArrayList<ReadliyType> filterList) {

		if (filterList != null && !filterList.isEmpty()) {
			for (final ReadliyType temp : filterList) {
				tev.addItem(temp.name, new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						tev.setTag(temp);
						getData();
					}
				});
			}
		}
		tev.changeItemColor(0);
	}

	private void initTitleFilter(final TitleExpandView tev,
			ArrayList<CommonType> filterList) {

		if (filterList != null && !filterList.isEmpty()) {
			for (final CommonType temp : filterList) {
				tev.addItem(temp.name, new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						tev.setTag(temp);
						getData();
					}
				});
			}
		}

		tev.changeItemColor(0);
	}


	private void initData() {

		srl_sgv_readliy_take.setRefreshing(true);
		getData();
	}

	private void getData() {

		HashMap<String, String> map = new HashMap<String, String>();
		page = 1;
		map.put("page", page + "");
		if (tev_title_readliy.getTag() != null) {
			CommonType ct = (CommonType) tev_title_readliy.getTag();
			map.put("filter", ct.id + "");
		}
		if (tev_type_readliy.getTag() != null) {
			ReadliyType ct = (ReadliyType) tev_type_readliy.getTag();
			map.put("type", ct.typeid + "");
		}

		if (tev_order_readliy.getTag() != null) {
			CommonType ct = (CommonType) tev_order_readliy.getTag();
			map.put("order", ct.id + "");
		}
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_READLIY_TAKE, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {

						ReadliytakeJson json = GsonUtils.toObj(response,
								ReadliytakeJson.class);
						if (json != null && json.data != null
								&& json.data.postList != null) {
							mHasRequestedMore = false;
							page++;
							adapter.refresh(json.data.postList);
						}
						if (json != null && json.data != null) {
							initExpandViews(json.data.filterList,
									json.data.orderList, json.data.typeList);
						}

					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
						
						srl_sgv_readliy_take.setRefreshing(false);
					}
				});
	}

	private void addData() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", page + "");
		if (tev_title_readliy.getTag() != null) {
			CommonType ct = (CommonType) tev_title_readliy.getTag();
			map.put("filter", ct.id + "");
		}
		if (tev_type_readliy.getTag() != null) {
			ReadliyType ct = (ReadliyType) tev_type_readliy.getTag();
			map.put("type", ct.typeid + "");
		}

		if (tev_order_readliy.getTag() != null) {
			CommonType ct = (CommonType) tev_order_readliy.getTag();
			map.put("order", ct.id + "");
		}
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_READLIY_TAKE, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						
						ReadliytakeJson json = GsonUtils.toObj(response,
								ReadliytakeJson.class);
						if (json != null && json.data != null
								&& json.data.postList != null && !json.data.postList.isEmpty()) {
							adapter.add(json.data.postList);
							mHasRequestedMore = false;
							page++;
						}

					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		if (!mHasRequestedMore) {
			int lastInScreen = firstVisibleItem + visibleItemCount;
			if (lastInScreen >= totalItemCount && totalItemCount!=0) {
				mHasRequestedMore = true;
				addData();
			}
		}
	}

	private boolean mHasRequestedMore;

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_publish_readliy:
			onClickPublish();
			break;

		default:
			break;
		}
	}

	private void onClickPublish() {
		
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		Intent intent = new Intent();
		if(user!=null) {
			intent = new Intent(getActivity(), CreatePostActivity.class);
			intent.putExtra(CreatePostActivity.EXTRA_IS_READLIY_TAKE, true);
			startActivityForResult(intent, REQUEST_PUBLISH);
		} else {
			intent.setClass(getActivity(), LoginActivity.class);
			startActivity(intent);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		ReadliyTakePost post = (ReadliyTakePost) parent
				.getItemAtPosition(position);
		Intent intent = new Intent(this, PostDetailActivity.class);
		intent.putExtra(PostDetailActivity.EXTRA_TOPIC_ID, post.tid);
		startActivity(intent);
	}

	@Override
	public void onRefresh(SwipyRefreshLayoutDirection direction) {

		if (direction == SwipyRefreshLayoutDirection.TOP) {
			getData();
		} else {
			addData();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode== Activity.RESULT_OK && requestCode == REQUEST_PUBLISH) {
			srl_sgv_readliy_take.setRefreshing(true);
			getData();
		}
	}
}
