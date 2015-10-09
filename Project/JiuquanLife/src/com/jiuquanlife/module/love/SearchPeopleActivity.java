package com.jiuquanlife.module.love;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.love.entity.FilterDatas;
import com.jiuquanlife.module.love.entity.SearchPageInfo;
import com.jiuquanlife.module.love.entity.SearchUserInfo;
import com.jiuquanlife.module.publish.PullToRefreshView;
import com.jiuquanlife.module.publish.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.module.publish.PullToRefreshView.OnHeaderRefreshListener;

public class SearchPeopleActivity extends BaseActivity {
	private PullToRefreshView pullToRefreshView;
	private ListView mListView;
	public static FilterDatas filterDatas;
	private SearchPeopleAdapter searchPeopleAdapter;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	Double geoLat;
	Double geoLng;
	private String uid = Common.getUid();
	private FrameLayout firstF, secondF, thirdF;
	private ImageView iv_indicator1, iv_indicator2, iv_indicator3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_search);
		initView();
		startProgressDialog("努力加载中...");
		initMap();
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			System.out.println("Location is:" + location);
			geoLat = location.getLatitude();
			geoLng = location.getLongitude();
			loadData();

			mLocationClient.unRegisterLocationListener(myListener);
			mLocationClient.stop();

		}
	}

	void initMap() {
		// mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		//
		// // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// // 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
		// // 在定位结束后，在合适的生命周期调用destroy()方法
		// // 其中如果间隔时间为-1，则定位只定一次
		// mLocationManagerProxy
		// .requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15,
		// aMapLocationListener);
		//
		// mLocationManagerProxy.setGpsEnable(false);
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	private ImageView iv_back, iv_select;
	public static int requestCode = 1;
	private int selectedItem = 1;

	private void initView() {

		firstF = (FrameLayout) findViewById(R.id.firstF);
		secondF = (FrameLayout) findViewById(R.id.secondF);
		thirdF = (FrameLayout) findViewById(R.id.thirdF);
		iv_indicator1 = (ImageView) findViewById(R.id.iv_indicator1);
		iv_indicator2 = (ImageView) findViewById(R.id.iv_indicator2);
		iv_indicator3 = (ImageView) findViewById(R.id.iv_indicator3);
		firstF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedItem = 1;
				page = 0;
				iv_indicator1.setVisibility(View.VISIBLE);

				iv_indicator2.setVisibility(View.INVISIBLE);
				iv_indicator3.setVisibility(View.INVISIBLE);
				initParam();
			}
		});

		secondF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedItem = 3;
				page = 0;
				iv_indicator2.setVisibility(View.VISIBLE);

				iv_indicator1.setVisibility(View.INVISIBLE);
				iv_indicator3.setVisibility(View.INVISIBLE);
				initParam();
			}
		});

		thirdF.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				selectedItem = 2;
				page = 0;
				iv_indicator1.setVisibility(View.INVISIBLE);

				iv_indicator2.setVisibility(View.INVISIBLE);
				iv_indicator3.setVisibility(View.VISIBLE);
				initParam();
			}
		});

		iv_select = (ImageView) findViewById(R.id.iv_select);
		iv_select.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SearchPeopleActivity.this,
						SearchFilterActivity.class);
				startActivityForResult(intent, requestCode);

			}
		});
		iv_back = (ImageView) findViewById(R.id.back);
		iv_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		mListView = (ListView) findViewById(R.id.lv_pl);
		pullToRefreshView = (PullToRefreshView) findViewById(R.id.pl_pub);
		pullToRefreshView.setPullDownEnable(true);
		pullToRefreshView.setPullUpEnable(true);
		pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
		pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);
		searchPeopleAdapter = new SearchPeopleAdapter(searchUserInfo,
				SearchPeopleActivity.this);
		searchPeopleAdapter.setFromUid(uid);
		mListView.setAdapter(searchPeopleAdapter);

	}

	private List<SearchUserInfo> searchUserInfo = new ArrayList<SearchUserInfo>();

	private int page = 0;
	private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			System.out.println("onheader refresh");
			page = 0;
			searchUserInfo = new ArrayList<SearchUserInfo>();

			searchPeopleAdapter.refreshData(searchUserInfo);
			loadData();

		}
	};

	private OnFooterRefreshListener onFooterRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			System.out.println("onFootRefresh");
			page++;
			loadData();

		}
	};

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private static final int MSG_INIT_DATA_FINISHED = 3;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				stopProgressDialog();
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				Toast.makeText(SearchPeopleActivity.this, "未能加载到数据",
						Toast.LENGTH_LONG).show();
				break;
			case MSG_INIT_DATA_SUCCESS:
				stopProgressDialog();
				searchPeopleAdapter.refreshData(searchUserInfo);
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				break;
			case MSG_INIT_DATA_FINISHED:
				stopProgressDialog();
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				Toast.makeText(SearchPeopleActivity.this, "没有更多数据",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	int findType = 1;
	String gender = "";
	String education = "";
	String location = "";
	String beginAge = "0";
	String endAge = "0";
	String beginHeight = "0";
	String endHeight = "0";

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SEARCH_URL);
		sb.append("/findType/");
		sb.append(selectedItem);
		sb.append("/page/");
		sb.append(page);
		sb.append("/currentLogitude/");
		sb.append(geoLng);
		sb.append("/currentLatitude/");
		sb.append(geoLat);

		if (!TextUtils.isEmpty(Common.getUid())) {
			sb.append("/uid/");
			sb.append(Common.getUid());
		}
		if (!TextUtils.isEmpty(gender)) {
			sb.append("/gender/");
			sb.append(Utils.encodeContent(gender));
		}
		if (!TextUtils.isEmpty(education)) {
			sb.append("/edu/");
			sb.append(Utils.encodeContent(education));
		}
		if (!TextUtils.isEmpty(location)) {
			sb.append("/location/");
			sb.append(Utils.encodeContent(location));
		}

		sb.append("/ageStart/");
		sb.append(beginAge);

		sb.append("/statureStart/");
		sb.append(beginHeight);

		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");
							mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

							return;
						}
						parse(response);

					}
				});
	}

	private SearchPageInfo searchPageInfo;

	private void parse(String response) {
		System.out.println("result:" + response);
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");

				Gson gson = new Gson();
				searchPageInfo = gson.fromJson(jsonObject.toString(),
						SearchPageInfo.class);
				if (null != searchPageInfo) {

					if (null != searchPageInfo.getFilterData()) {
						filterDatas = searchPageInfo.getFilterData();
						System.out.println("FilterData:" + filterDatas);
					}

					if (null != searchPageInfo.getMarryMemberList()
							&& !searchPageInfo.getMarryMemberList().isEmpty()) {
						searchUserInfo.addAll(searchPageInfo
								.getMarryMemberList());
					} else {
						mHandler.sendEmptyMessage(MSG_INIT_DATA_FINISHED);
						return;
					}
					mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

				} else {
					mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

				}
				System.out.println("publishContent:" + searchPageInfo);
			}
		} catch (Exception e) {

			e.printStackTrace();
			mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// it.putExtra("beginAge", beginAge);
		// it.putExtra("endAge", endAge);
		// it.putExtra("beginHeight", beginHeight);
		// it.putExtra("endHeight", endHeight);
		// it.putExtra("edu", education);
		// it.putExtra("location", location);
		System.out.println("onActivityResult");
		if (null == data) {
			return;
		}
		gender = data.getStringExtra("gender");
		education = data.getStringExtra("edu");
		location = data.getStringExtra("location");
		beginAge = data.getStringExtra("beginAge");
		endAge = data.getStringExtra("endAge");
		beginHeight = data.getStringExtra("beginHeight");
		endHeight = data.getStringExtra("endHeight");
		page = 0;
		searchUserInfo = new ArrayList<SearchUserInfo>();

		searchPeopleAdapter.refreshData(searchUserInfo);
		startProgressDialog("努力加载中...");
		loadData();
	};

	private void initParam() {
		gender = "";
		education = "";
		location = "";
		beginAge = "0";
		endAge = "0";
		beginHeight = "0";
		endHeight = "0";
		page = 0;
		searchUserInfo = new ArrayList<SearchUserInfo>();

		searchPeopleAdapter.refreshData(searchUserInfo);
		startProgressDialog("努力加载中...");
		loadData();
	}

}
