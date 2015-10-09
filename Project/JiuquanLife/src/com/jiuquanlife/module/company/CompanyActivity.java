package com.jiuquanlife.module.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.android.volley.Response.Listener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.activity.CategoryDetailActivity;
import com.jiuquanlife.module.company.activity.CompanyDetailActivity;
import com.jiuquanlife.module.company.activity.CompanySearchActivity;
import com.jiuquanlife.module.company.adapter.CompanyAdapter;
import com.jiuquanlife.module.company.entity.CompanyInfo;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.jiuquanlife.module.publish.PullToRefreshView;
import com.jiuquanlife.module.publish.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.module.publish.PullToRefreshView.OnHeaderRefreshListener;
import com.jiuquanlife.utils.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

public class CompanyActivity extends BaseActivity {
	private static final int MESSAGE_INIT_DATA_SUCCESS = 1;
	private static final int MSG_INIT_DATA_FAILED = 2;
	private static final int MSG_INIT_DATA_FINISHED = 3;
	private ImageView iv_map;
	private ImageView iv_search;
	private ListView lv_favorite;
	private List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();
	private CompanyAdapter companyAdapter;
	// private TextView tv_loading;
	private ImageView et_search;
	private PullToRefreshView pullToRefreshView;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	Double geoLat;
	Double geoLng;
	private GridView gv_type;
	private ArrayList<Integer> types;
	private String texts[] = null;
	private int images[] = null;
	private BitmapUtils bitmapUtils;
	private LocationManagerProxy mLocationManagerProxy;
	private ScrollViewCustom hc_category;

	private ImageView first, second;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_INIT_DATA_SUCCESS:
				// tv_loading.setVisibility(View.GONE);
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				if (null != companyAdapter) {
					companyAdapter.updateData(companyInfos);
					Util.setListViewHeightBasedOnChildren2(lv_favorite);
				}
				// Util.setListViewHeightBasedOnChildren(lv_favorite);
				stopProgressDialog();
				break;
			case MSG_INIT_DATA_FAILED:
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				stopProgressDialog();
				break;
			case MSG_INIT_DATA_FINISHED:
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				Toast.makeText(getApplicationContext(), "没有更多数据",
						Toast.LENGTH_SHORT).show();
				stopProgressDialog();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			geoLat = location.getLatitude();
			geoLng = location.getLongitude();
			loadData();

			mLocationClient.unRegisterLocationListener(myListener);
			mLocationClient.stop();

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		initViews();
		initCategoy();

		initMap();
		System.out.println("Main thread id" + Thread.currentThread().getId());
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private AMapLocationListener aMapLocationListener = new AMapLocationListener() {

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			if (amapLocation != null
					&& amapLocation.getAMapException().getErrorCode() == 0) {
				// 获取位置信息
				geoLat = amapLocation.getLatitude();
				geoLng = amapLocation.getLongitude();
				System.out.println("Location:" + amapLocation);
				loadData();
			}

		}
	};

	private void initCategoy() {
		// tv_loading = (TextView) findViewById(R.id.tv_loading);

		images = new int[] { R.drawable.icn_category_food,
				R.drawable.icn_category_shopping,
				R.drawable.icn_category_hotel, R.drawable.icn_category_service,
				R.drawable.icn_category_newproduct,
				R.drawable.icn_category_travel,
				R.drawable.icn_category_entertainment,
				R.drawable.icn_category_pretty, R.drawable.car,
				R.drawable.money };
		texts = new String[] { "餐饮美食", "家居装修", "教育培训", "生活服务", "婚庆服务", "旅游出行",
				"休闲娱乐", "商贸物流", "汽车服务", "金融服务" };
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < texts.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("categoty_itemImage", images[i]);
			map.put("categoy_itemText", texts[i]);
			lstImageItem.add(map);
		}

		SimpleAdapter saImageItems = new SimpleAdapter(this,
				lstImageItem,// 数据源
				R.layout.company_category_item,// 显示布局
				new String[] { "categoty_itemImage", "categoy_itemText" },
				new int[] { R.id.categoty_itemImage, R.id.categoy_itemText });
		gv_type.setAdapter(saImageItems);

		gv_type.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gv_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				int cid = 0;
				String title = "";
				switch (position) {
				case 0:
					cid = 27;
					title = "餐饮美食";
					break;
				case 1:
					cid = 80;
					title = "家居装修";
					break;
				case 2:
					cid = 30;
					title = "教育培训";
					break;
				case 3:
					cid = 26;
					title = "生活服务";

					break;
				case 4:

					cid = 69;
					title = "婚庆服务";

					break;
				case 5:

					cid = 28;
					title = "旅游出行";
					break;
				case 6:
					cid = 31;
					title = "休闲娱乐";
					break;
				case 7:
					cid = 88;
					title = "商贸物流";
					break;
				case 8:
					cid = 76;
					title = "汽车服务";
					break;
				case 9:
					cid = 83;
					title = "金融服务";
					break;
				default:
					break;
				}

				Intent intent = new Intent(CompanyActivity.this,
						CategoryDetailActivity.class);
				intent.putExtra("cid", cid);
				intent.putExtra("cname", title);
				startActivity(intent);

			}
		});

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float density = dm.density;
		int allWidth = (int) (45 * 10 * density);
		int itemWidth = (int) (100 * density);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				allWidth, LinearLayout.LayoutParams.FILL_PARENT);
		gv_type.setLayoutParams(params);
		gv_type.setColumnWidth(itemWidth - 30);
		// gv_type.setHorizontalSpacing(10);
		gv_type.setStretchMode(GridView.NO_STRETCH);
		gv_type.setNumColumns(5);
	}

	private void initViews() {
		gv_type = (GridView) findViewById(R.id.gv_type);
		et_search = (ImageView) findViewById(R.id.search_iv);
		et_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CompanyActivity.this,
						CompanySearchActivity.class);
				startActivity(intent);

			}
		});
		iv_map = (ImageView) findViewById(R.id.iv_map);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		lv_favorite = (ListView) findViewById(R.id.lv_like_shop);
		lv_favorite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				CompanyInfo companyInfo = (CompanyInfo) companyAdapter
						.getItem(position);
				System.out.println("companyInfo:" + companyInfo.getEntid());
				System.out.println("");
				Intent intent = new Intent(CompanyActivity.this,
						CompanyDetailActivity.class);
				intent.putExtra("companyId", companyInfo.getEntid());
				startActivity(intent);

			}
		});

		bitmapUtils = BitmapHelper.getBitmapUtils(getApplicationContext());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);

		pullToRefreshView = (PullToRefreshView) findViewById(R.id.pl_pub);
		pullToRefreshView.setPullDownEnable(true);
		pullToRefreshView.setPullUpEnable(true);
		pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
		pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);

		companyAdapter = new CompanyAdapter(getBaseContext(), companyInfos);
		lv_favorite.setAdapter(companyAdapter);

		first = (ImageView) findViewById(R.id.first);
		second = (ImageView) findViewById(R.id.second);

		// hc_category = (ScrollViewCustom) findViewById(R.id.hc_category);
		// hc_category.setOnScrollStopListner(new OnScrollStopListner() {
		//
		// @Override
		// public void onScrollToRightEdge() {
		// System.out.println("onScrollToRightEdge");
		// second.setImageResource(R.drawable.dotcnew);
		// first.setImageResource(R.drawable.dotnnew);
		//
		// }
		//
		// @Override
		// public void onScrollToMiddle() {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onScrollToLeftEdge() {
		// System.out.println("onScrollToLeftEdge");
		//
		// first.setImageResource(R.drawable.dotcnew);
		// second.setImageResource(R.drawable.dotnnew);
		//
		// }
		//
		// @Override
		// public void onScrollStoped() {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		gv_type.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				System.out.println("onScrollStateChanged");
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// 滚动到底部
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						System.out.println("can view last item");
					}
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				System.out.println("Fist visible item:" + firstVisibleItem);

			}
		});
		
		

		gv_type.setClickable(true);
		gv_type.setPressed(true);
		gv_type.setEnabled(true);

	}

	private int page = 0;
	private OnHeaderRefreshListener onHeaderRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			System.out.println("onheader refresh");
			page = 0;

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

	private void loadData() {
		StringBuilder sb = new StringBuilder(Common.COMPANY_URL);

		sb.append("/currentLon/");
		sb.append(geoLng);
		sb.append("/currentLat/");
		sb.append(geoLat);
		sb.append("/page/");
		sb.append(page);
		startProgressDialog("加载中...");
		System.out.println("Url:" + sb.toString());
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), responseListener);
	}

	private Listener<String> responseListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			if (TextUtils.isEmpty(response)) {
				Log.d(Common.TAG, "Failed to get company info");
				mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
				return;
			}

			List<CompanyInfo> infos = parse(response);
			System.out.println(" infos size:" + infos.size());
			if (infos.size() != 0) {
				companyInfos.addAll(parse(response));
				mHandler.sendEmptyMessage(MESSAGE_INIT_DATA_SUCCESS);
			} else {
				mHandler.sendEmptyMessage(MSG_INIT_DATA_FINISHED);
			}

		}
	};

	public List<CompanyInfo> parse(String result) {
		List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();
		System.out.println("Parse thread id" + Thread.currentThread().getId());
		try {
			Log.d(Common.TAG, "Company data:" + result);
			JSONObject jsonObject = new JSONObject(result);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");

				if (jsonObject.has("companyList")) {
					JSONArray jsonArray = jsonObject
							.getJSONArray("companyList");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jo = (JSONObject) jsonArray.get(i);
						Log.d(Common.TAG, "单个json对象：" + jo.toString());
						String entid = jo.getString("entid");
						String company = jo.getString("company");
						String addressId = jo.getString("addressId");
						String address = jo.getString("address");
						int visitNum = jo.getInt("visitNum");
						String goodCommentRate = jo
								.getString("goodCommentRate");
						int uid = jo.getInt("uid");
						String token = jo.getString("token");
						String longitude = jo.getString("longitude");
						String latitude = jo.getString("latitude");
						String addressName = jo.getString("addressName");
						String subAddressName = jo.getString("subAddressName");
						String commentCount = jo.getString("commentCount");
						String starCount = jo.getString("starCount");
						String distanceValue = "";
						if (jo.has("distance"))
							distanceValue = jo.getString("distance");
						int distance = 0;
						if (!TextUtils.isEmpty(distanceValue)) {
							distance = Integer.valueOf(distanceValue);
						}
						String totalAverage = jo.getString("totalAverage");
						ImgInfo imgInfo = new ImgInfo();
						if (jo.has("img")) {
							try {
								JSONObject img = jo.getJSONObject("img");

								Gson gson = new Gson();
								imgInfo = gson.fromJson(img.toString(),
										ImgInfo.class);
								String imgInfoStr = imgInfo.toString();
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						int imgCount = jo.getInt("imgCount");
						int isFavorite = jo.getInt("isFavorite");

						CompanyInfo companyInfo = new CompanyInfo(entid,
								company, addressId, address, visitNum,
								goodCommentRate, uid, token, longitude,
								latitude, addressName, subAddressName,
								commentCount, starCount, totalAverage, imgInfo,
								imgCount, distance, isFavorite);
						companyInfos.add(companyInfo);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Collections.sort(companyInfos);
		return companyInfos;
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

}
