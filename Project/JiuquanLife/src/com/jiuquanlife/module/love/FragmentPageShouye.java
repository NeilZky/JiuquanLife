package com.jiuquanlife.module.love;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.Util;
import com.jiuquanlife.module.forum.activity.PostDetailActivity;
import com.jiuquanlife.module.love.PeopleDetailInfoActivity.MyLocationListener;
import com.jiuquanlife.module.love.customview.SlideShowView;
import com.jiuquanlife.module.love.entity.HeaderImgInfo;
import com.jiuquanlife.module.love.entity.LoveInfo;
import com.jiuquanlife.module.love.entity.ShoueYeInfo;
import com.jiuquanlife.module.publish.PullToRefreshView;
import com.jiuquanlife.module.publish.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.module.publish.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.module.publish.entity.PublishContent;
import com.jiuquanlife.module.publish.entity.PublishInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class FragmentPageShouye extends Fragment {

	private PullToRefreshView pullToRefreshView;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		System.out.println("onCreateView");
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_love_shouye, null);
			initView(rootView);
		}

		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("onCreate");
		initMap();
		// loadData();
	}

	private SlideShowView slideShowView;
	private TextView tv_zhaoren, tv_huodong, tv_shangjia;
	private ListView lv_love;

	private void initView(View view) {
		slideShowView = (SlideShowView) view.findViewById(R.id.slideShowView);
		tv_zhaoren = (TextView) view.findViewById(R.id.tv_zhaoren);
		tv_zhaoren.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SearchPeopleActivity.class);
				startActivity(intent);

			}
		});
		tv_huodong = (TextView) view.findViewById(R.id.tv_huodong);
		tv_shangjia = (TextView) view.findViewById(R.id.tv_shangjia);
		pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pl_pub);
		pullToRefreshView.setPullDownEnable(true);
		pullToRefreshView.setPullUpEnable(true);
		pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
		pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);
		lv_love = (ListView) view.findViewById(R.id.lv_love);
		lv_love.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LoveInfo info = shouyeAdapter.getItem(position);
				System.out.println("onItemClick:" + position);
				try {
					Intent intent = new Intent(getActivity(),
							PostDetailActivity.class);
					intent.putExtra(PostDetailActivity.EXTRA_TOPIC_ID,
							Integer.valueOf(info.getTid()));
					getActivity().startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		shouyeAdapter = new ShouyeAdapter(getActivity(), loveInfos);
		lv_love.setAdapter(shouyeAdapter);
	}

	private List<LoveInfo> loveInfos = new ArrayList<LoveInfo>();
	private ShouyeAdapter shouyeAdapter;
	private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			System.out.println("onheader refresh");
			page = 0;
			loveInfos = new ArrayList<LoveInfo>();

			shouyeAdapter.refreshData(loveInfos);
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
	private int page = 0;

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private List<HeaderImgInfo> imgInfos;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				break;
			case MSG_INIT_DATA_SUCCESS:
				if (null == imgInfos) {
					imgInfos = shouYeInfos.getMarryImgs();
					slideShowView.setImagePath(imgInfos);

				}
				shouyeAdapter.refreshData(loveInfos);
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				Util.setListViewHeightBasedOnChildren(lv_love);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void loadData() {

		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SHOUYE_URL);
		sb.append("/page/");
		sb.append(page);
		if (page == 0) {
			sb.append("/currentLogitude/");
			sb.append(geoLng);
			sb.append("/currentLatitude/");
			sb.append(geoLat);
		}

		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(getActivity(), sb.toString(),
				new Listener<String>() {

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

	ShoueYeInfo shouYeInfos;

	private void parse(String response) {
		System.out.println("response is:" + response.toString());
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");

				Gson gson = new Gson();
				shouYeInfos = gson.fromJson(jsonObject.toString(),
						ShoueYeInfo.class);
				if (null != shouYeInfos) {
					if (null != shouYeInfos.getMarryPost()
							&& !shouYeInfos.getMarryPost().isEmpty()) {
						loveInfos.addAll(shouYeInfos.getMarryPost());
					}
					mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

				} else {
					mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

				}
				System.out.println("publishContent:" + shouYeInfos);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	Double geoLat;
	Double geoLng;

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
		mLocationClient = new LocationClient(getActivity()
				.getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
}
