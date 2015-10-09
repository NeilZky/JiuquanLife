package com.jiuquanlife.module.company.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
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
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.adapter.AddressCategoryAdapter;
import com.jiuquanlife.module.company.adapter.CategoryAdapter;
import com.jiuquanlife.module.company.adapter.CompanyAdapter;
import com.jiuquanlife.module.company.adapter.OrderCategoryAdapter;
import com.jiuquanlife.module.company.entity.AddressGroupInfo;
import com.jiuquanlife.module.company.entity.AddressInfo;
import com.jiuquanlife.module.company.entity.CategoryGroupInfo;
import com.jiuquanlife.module.company.entity.CategoryInfo;
import com.jiuquanlife.module.company.entity.CompanyInfo;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.jiuquanlife.module.company.entity.IntelligenSortInfo;
import com.jiuquanlife.module.publish.PullToRefreshView;
import com.jiuquanlife.module.publish.PullToRefreshView.OnFooterRefreshListener;
import com.jiuquanlife.module.publish.PullToRefreshView.OnHeaderRefreshListener;

public class CategoryDetailActivity extends BaseActivity {
	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private static final int MSG_LOAD_DATA = 3;
	private static final int MSG_INIT_DATA_FINISHED = 4;
	private List<AddressGroupInfo> addressGroupInfos = new ArrayList<AddressGroupInfo>();
	private List<IntelligenSortInfo> intelligenSortInfos = new ArrayList<IntelligenSortInfo>();
	private List<CategoryGroupInfo> categoryGroupInfos = new ArrayList<CategoryGroupInfo>();
	private List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();
	private FrameLayout flChild;
	private PopupWindow mPopWin;
	private LinearLayout ll_category_spinner, ll_category_detail, ll_back,
			ll_distance, ll_category, ll_order, ll_popup;
	private ListView listView, popRootList, popChildList;
	private CompanyAdapter companyAdapter;
	private TextView tv_order, tv_distance, tv_category;
	private boolean sDistance = true, sCategory = true, sOrder = true,
			isFirst = true;
	private int rootCategoryPosition = 0, rootAddressPosition = 0, orderId = 0,
			categoryId = 0, distanceId = 0, rootCategoryId = 0,
			rootDistanceId = -1;
	private AddressGroupInfo rootAddressGroupInfo;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	Double geoLat;
	Double geoLng;
	private String search;
	private EditText et_search;
	private PullToRefreshView pullToRefreshView;

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

	private LocationManagerProxy mLocationManagerProxy;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				stopProgressDialog();
				break;
			case MSG_INIT_DATA_SUCCESS:
				pullToRefreshView.onHeaderRefreshComplete();
				pullToRefreshView.onFooterRefreshComplete();
				stopProgressDialog();
				handleShowData();
				break;
			case MSG_LOAD_DATA:
				handleLoadData();
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

	private void handleLoadData() {
		if (null != mPopWin)
			mPopWin.dismiss();
		System.out.println("order:" + orderId + " distanceId:" + distanceId
				+ " typeId:" + categoryId);
		companyInfos = new ArrayList<CompanyInfo>();
		page = 0;
		if (null != companyAdapter) {
			companyAdapter.updateData(companyInfos);
		}
		loadData();
	}

	private void handleShowData() {
		if (null == companyInfos || companyInfos.isEmpty()) {
			Toast.makeText(getBaseContext(), "未找到数据", Toast.LENGTH_LONG).show();
			return;
		}
		companyAdapter.updateData(companyInfos);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_category_detail);

		initView();

		handleIntent();
		initMap();

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
				startProgressDialog("加载中...");
				loadData();
			}

		}
	};

	private void stopLocation() {
		if (mLocationManagerProxy != null) {
			mLocationManagerProxy.removeUpdates(aMapLocationListener);
			mLocationManagerProxy.destory();
		}
		mLocationManagerProxy = null;
	}

	private void initView() {
		et_search = (EditText) findViewById(R.id.etSearch);
		et_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CategoryDetailActivity.this,
						CompanySearchActivity.class);
				startActivity(intent);
				finish();

			}
		});
		tv_order = (TextView) findViewById(R.id.tv_order);
		tv_category = (TextView) findViewById(R.id.tv_category);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		ll_category_spinner = (LinearLayout) findViewById(R.id.ll_category_spinne);
		ll_category_detail = (LinearLayout) findViewById(R.id.ll_category_detail);
		ll_order = (LinearLayout) findViewById(R.id.ll_order);
		ll_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (null == intelligenSortInfos
						|| intelligenSortInfos.isEmpty()) {
					return;
				}
				if (sDistance) {
					// imageView.setImageDrawable(getApplicationContext()
					// .getResources().getDrawable(
					// R.drawable.takeout_ic_sort_condition_up));
					sDistance = false;
					showSortPopupWindow(ll_order.getWidth() + 30,
							ll_category_detail.getHeight(), 1, 1);
				} else {
					sDistance = true;
					// imageView.setImageDrawable(getApplicationContext()
					// .getResources().getDrawable(
					// R.drawable.takeout_ic_sort_condition_down));
				}
			}
		});

		ll_category = (LinearLayout) findViewById(R.id.ll_category);
		ll_category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (null == categoryGroupInfos || categoryGroupInfos.isEmpty()) {
					return;
				}
				if (sCategory) {
					// imageView.setImageDrawable(getApplicationContext()
					// .getResources().getDrawable(
					// R.drawable.takeout_ic_sort_condition_up));
					sCategory = false;
					showCategoyPopupWindow(ll_category_detail.getWidth(),
							ll_category_detail.getHeight(), 1, 1);
				} else {
					sCategory = true;
					// imageView.setImageDrawable(getApplicationContext()
					// .getResources().getDrawable(
					// R.drawable.takeout_ic_sort_condition_down));
				}

			}
		});

		ll_distance = (LinearLayout) findViewById(R.id.ll_distance);
		ll_distance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				System.out.println("sOrder:" + sOrder);
				if (null == addressGroupInfos || addressGroupInfos.isEmpty()) {
					return;
				}

				if (sOrder) {
					System.out.println("1");
					//
					// imageView.setImageDrawable(getApplicationContext()
					// .getResources().getDrawable(
					// R.drawable.takeout_ic_sort_condition_up));
					sOrder = false;
					showAddressPopupWindow(ll_category_detail.getWidth(),
							ll_category_detail.getHeight(), 1, 1);
				} else {
					System.out.println("2");
					sOrder = true;
					// imageView.setImageDrawable(getApplicationContext()
					// .getResources().getDrawable(
					// R.drawable.takeout_ic_sort_condition_down));
					// if(null!=mPopWin)
					// {
					// mPopWin.dismiss();
					// }
				}

			}
		});

		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});

		listView = (ListView) findViewById(R.id.ll_category_list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				CompanyInfo companyInfo = (CompanyInfo) companyAdapter
						.getItem(position);
				Intent intent = new Intent(CategoryDetailActivity.this,
						CompanyDetailActivity.class);
				intent.putExtra("companyId", companyInfo.getEntid());
				startActivity(intent);

			}
		});

		pullToRefreshView = (PullToRefreshView) findViewById(R.id.pl_pub);
		pullToRefreshView.setPullDownEnable(true);
		pullToRefreshView.setPullUpEnable(true);
		pullToRefreshView.setOnHeaderRefreshListener(onHeaderRefreshListener);
		pullToRefreshView.setOnFooterRefreshListener(onFooterRefreshListener);

		companyAdapter = new CompanyAdapter(this, companyInfos);
		listView.setAdapter(companyAdapter);
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

	private void handleIntent() {
		Intent intent = getIntent();
		if (null != intent) {
			search = intent.getStringExtra("search");
			if (TextUtils.isEmpty(search)) {

				int cid = intent.getIntExtra("cid", 0);
				String cname = intent.getStringExtra("cname");
				rootCategoryId = cid;
				categoryId = cid;
				tv_category.setText(cname);
				tv_distance.setText("全城");
			} else {
				rootCategoryId = 0;
				categoryId = 0;
				tv_category.setText("全部");
				et_search.setText(search);
			}
		}
	}

	AddressCategoryAdapter<AddressGroupInfo> addressCategoryAdapter;
	AddressCategoryAdapter<AddressInfo> childAddressCategoryAdapter;

	private void showAddressPopupWindow(int width, int height, int px, int py) {
		ll_popup = (LinearLayout) LayoutInflater.from(
				CategoryDetailActivity.this).inflate(
				R.layout.popup_two_category, null);
		popRootList = (ListView) ll_popup.findViewById(R.id.rootcategory);
		addressCategoryAdapter = new AddressCategoryAdapter<AddressGroupInfo>(
				addressGroupInfos, this);
		addressCategoryAdapter.setDistanceId(rootDistanceId);
		popRootList.setAdapter(addressCategoryAdapter);
		addressCategoryAdapter.notifyDataSetChanged();
		flChild = (FrameLayout) ll_popup.findViewById(R.id.child_lay);
		popChildList = (ListView) ll_popup.findViewById(R.id.childcategory);
		flChild.setVisibility(View.INVISIBLE);

		mPopWin = new PopupWindow(ll_popup, width, height * 2 / 3, true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(ll_category_spinner, px, py);
		mPopWin.update();

		popRootList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				List<AddressInfo> childCategoryInfo = addressGroupInfos.get(
						position).getSubAddressList();

				rootAddressPosition = position;
				rootDistanceId = addressGroupInfos.get(position).getAid();
				addressCategoryAdapter.setDistanceId(rootDistanceId);
				if (null == childCategoryInfo || childCategoryInfo.isEmpty()) {
					flChild.setVisibility(View.INVISIBLE);

					distanceId = rootDistanceId;
					tv_distance.setText(addressGroupInfos.get(position)
							.getAddressName());
					mHandler.sendEmptyMessage(MSG_LOAD_DATA);
					return;
				}

				flChild.setVisibility(View.VISIBLE);

				childAddressCategoryAdapter = new AddressCategoryAdapter<AddressInfo>(
						childCategoryInfo, getApplicationContext());
				childAddressCategoryAdapter.setDistanceId(distanceId);
				popChildList.setAdapter(childAddressCategoryAdapter);
				childAddressCategoryAdapter.notifyDataSetChanged();
				addressCategoryAdapter.notifyDataSetChanged();

			}
		});
		popChildList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				List<AddressInfo> childCategoryInfo = addressGroupInfos.get(
						rootAddressPosition).getSubAddressList();

				System.out.println("category info:"
						+ childCategoryInfo.get(position));
				distanceId = childCategoryInfo.get(position).getAid();
				childAddressCategoryAdapter.setDistanceId(distanceId);
				mHandler.sendEmptyMessage(MSG_LOAD_DATA);
				if (distanceId == rootDistanceId) {
					tv_distance.setText(addressGroupInfos.get(
							rootAddressPosition).getAddressName());
				} else {
					tv_distance.setText(childCategoryInfo.get(position)
							.getAddressName());
				}

			}
		});
	}

	OrderCategoryAdapter orderCategoryAdapter;

	private void showSortPopupWindow(int width, int height, int px, int py) {
		ll_popup = (LinearLayout) LayoutInflater.from(
				CategoryDetailActivity.this).inflate(
				R.layout.popup_one_category, null);
		System.out.println("intelligenSortInfos:"
				+ intelligenSortInfos.toString());
		popRootList = (ListView) ll_popup.findViewById(R.id.rootcategory);
		orderCategoryAdapter = new OrderCategoryAdapter(intelligenSortInfos,
				this);
		orderCategoryAdapter.setOrderId(orderId);
		popRootList.setAdapter(orderCategoryAdapter);
		popRootList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println("sort:"
						+ orderCategoryAdapter.getItem(position).toString());
				orderId = orderCategoryAdapter.getItem(position).getId();
				orderCategoryAdapter.setOrderId(orderId);
				mHandler.sendEmptyMessage(MSG_LOAD_DATA);
				tv_order.setText(orderCategoryAdapter.getItem(position)
						.getIntelligentSort());

			}
		});
		orderCategoryAdapter.notifyDataSetChanged();

		mPopWin = new PopupWindow(ll_popup, width, height * 2 / 3, true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(ll_category_spinner, 1, 1);
		mPopWin.update();

	}

	CategoryAdapter<CategoryGroupInfo> categoryAdapter;

	CategoryAdapter<CategoryInfo> childCategoryAdapter;

	private void showCategoyPopupWindow(int width, int height, int px, int py) {
		ll_popup = (LinearLayout) LayoutInflater.from(
				CategoryDetailActivity.this).inflate(
				R.layout.popup_two_category, null);
		popRootList = (ListView) ll_popup.findViewById(R.id.rootcategory);
		categoryAdapter = new CategoryAdapter<CategoryGroupInfo>(
				categoryGroupInfos, this);
		categoryAdapter.setCid(rootCategoryId);
		popRootList.setAdapter(categoryAdapter);
		categoryAdapter.notifyDataSetChanged();
		flChild = (FrameLayout) ll_popup.findViewById(R.id.child_lay);
		popChildList = (ListView) ll_popup.findViewById(R.id.childcategory);
		flChild.setVisibility(View.INVISIBLE);

		mPopWin = new PopupWindow(ll_popup, width, height * 2 / 3, true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(ll_category_spinner, px, py);
		mPopWin.update();

		popRootList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				List<CategoryInfo> childCategoryInfo = categoryGroupInfos.get(
						position).getCatInfos();
				flChild.setVisibility(View.VISIBLE);
				rootCategoryPosition = position;
				rootCategoryId = categoryGroupInfos.get(position).getCatid();
				categoryAdapter.setCid(rootCategoryId);
				childCategoryAdapter = new CategoryAdapter<CategoryInfo>(
						childCategoryInfo, getApplicationContext());
				childCategoryAdapter.setCid(categoryId);
				popChildList.setAdapter(childCategoryAdapter);
				categoryAdapter.notifyDataSetChanged();

			}
		});
		popChildList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				List<CategoryInfo> childCategoryInfo = categoryGroupInfos.get(
						rootCategoryPosition).getCatInfos();

				System.out.println("category info:"
						+ childCategoryInfo.get(position));
				categoryId = childCategoryInfo.get(position).getCatid();
				childCategoryAdapter.setCid(categoryId);
				if (categoryId == rootCategoryId) {
					tv_category.setText(categoryGroupInfos.get(
							rootCategoryPosition).getCatname());
				} else {
					tv_category.setText(childCategoryInfo.get(position)
							.getCatname());

				}
				mHandler.sendEmptyMessage(MSG_LOAD_DATA);
			}
		});
	}

	private void loadData() {

		StringBuilder sb = new StringBuilder();
		sb.append(Common.CATEGORY_DETAIL_URL);
		sb.append("/");
		sb.append("address/");
		sb.append(distanceId);
		sb.append("/order/");
		sb.append(orderId);
		sb.append("/type/");
		sb.append(categoryId);
		if (!TextUtils.isEmpty(search)) {
			sb.append("/word/");
			sb.append(Uri.encode(search));
		}
		sb.append("/currentLon/");
		sb.append(geoLng);
		sb.append("/currentLat/");
		sb.append(geoLat);

		sb.append("/page/");
		sb.append(page);
		System.out.println("Request url:" + sb.toString());
		RequestHelper.getInstance().getRequest(getApplicationContext(),
				sb.toString(), responseListener);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private Listener<String> responseListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			if (TextUtils.isEmpty(response)) {
				Log.d(Common.TAG, "Failed to get company info");
				mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
				return;
			}

			parse(response);

		}
	};

	public void parse(String result) {
		try {

			Log.d(Common.TAG, "Detail data:" + result);
			JSONObject jsonObject = new JSONObject(result);

			if (jsonObject.has("data")) {
				JSONObject dataJsonObject = (JSONObject) jsonObject.get("data");

				if (isFirst) {
					isFirst = false;
					if (dataJsonObject.has("addressList")) {
						JSONArray jsonArray = dataJsonObject
								.getJSONArray("addressList");
						addressGroupInfos = parseAddressInfo(jsonArray);
						System.out.println("addressGroupInfos:"
								+ addressGroupInfos);

					}

					if (dataJsonObject.has("categorList")) {
						JSONArray jsonArray = dataJsonObject
								.getJSONArray("categorList");
						categoryGroupInfos = parseCategoryInfo(jsonArray);

					}

					if (dataJsonObject.has("intelligentSortList")) {
						JSONArray jsonArray = dataJsonObject
								.getJSONArray("intelligentSortList");

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject intelligentJsonObject = jsonArray
									.getJSONObject(i);
							int id = intelligentJsonObject.getInt("id");
							String intelligentSort = intelligentJsonObject
									.getString("intelligentSort");

							IntelligenSortInfo intelligenSortInfo = new IntelligenSortInfo(
									id, intelligentSort);
							intelligenSortInfos.add(intelligenSortInfo);

						}
					}

				}

				if (dataJsonObject.has("companyList")) {

					JSONArray jsonArray = dataJsonObject
							.getJSONArray("companyList");
					List<CompanyInfo> infos = parseCompanyInfo(jsonArray);
					if (infos.size() != 0) {
						companyInfos.addAll(infos);

					} else {
						mHandler.sendEmptyMessage(MSG_INIT_DATA_FINISHED);
						return;
					}
				}

			}
			mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

		} catch (Exception e) {
			mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);
			e.printStackTrace();
		}
	}

	private List<CategoryGroupInfo> parseCategoryInfo(JSONArray jsonArray) {
		List<CategoryGroupInfo> categoryGroupInfos = new ArrayList<CategoryGroupInfo>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject categoryJsonObject = jsonArray.getJSONObject(i);

				int catid = categoryJsonObject.getInt("catid");
				String catname = categoryJsonObject.getString("catname");
				System.out.println("catname" + catname);
				if (categoryJsonObject.has("subList")) {
					JSONArray subArray = categoryJsonObject
							.getJSONArray("subList");
					System.out.println("subArray:" + subArray.toString());
					List<CategoryInfo> categoryInfos = new ArrayList<CategoryInfo>();
					for (int j = 0; j < subArray.length(); j++) {
						JSONObject subCategoryJsonObject = subArray
								.getJSONObject(j);
						int subCatid = subCategoryJsonObject.getInt("catid");
						String subCatname = subCategoryJsonObject
								.getString("catname");
						CategoryInfo categoryInfo = new CategoryInfo(subCatid,
								subCatname);

						categoryInfos.add(categoryInfo);
					}

					CategoryGroupInfo categoryGroupInfo = new CategoryGroupInfo(
							catid, catname, categoryInfos);

					categoryGroupInfos.add(categoryGroupInfo);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryGroupInfos;
	}

	private List<AddressGroupInfo> parseAddressInfo(JSONArray jsonArray) {
		List<AddressGroupInfo> categoryGroupInfos = new ArrayList<AddressGroupInfo>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject categoryJsonObject = jsonArray.getJSONObject(i);

				int aid = categoryJsonObject.getInt("aid");
				int level = categoryJsonObject.getInt("level");
				int pid = categoryJsonObject.getInt("pid");
				String addressName = categoryJsonObject
						.getString("addressName");
				List<AddressInfo> categoryInfos = new ArrayList<AddressInfo>();
				if (categoryJsonObject.has("subAddressList")
						&& !categoryJsonObject.isNull("subAddressList")) {
					try {
						JSONArray subArray = categoryJsonObject
								.getJSONArray("subAddressList");
						System.out.println("subArray:" + subArray.toString());
						categoryInfos = new ArrayList<AddressInfo>();
						for (int j = 0; j < subArray.length(); j++) {
							JSONObject subCategoryJsonObject = subArray
									.getJSONObject(j);
							int subAid = subCategoryJsonObject.getInt("aid");
							int subLevel = 0;
							if (subCategoryJsonObject.has("level"))
								subLevel = subCategoryJsonObject
										.getInt("level");

							int subPid = 0;
							if (subCategoryJsonObject.has("pid"))
								subPid = subCategoryJsonObject.getInt("pid");
							String subAddressName = subCategoryJsonObject
									.getString("addressName");

							AddressInfo addressInfo = new AddressInfo(subAid,
									subPid, subLevel, subAddressName);
							categoryInfos.add(addressInfo);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				AddressGroupInfo addressGroupInfo = new AddressGroupInfo(aid,
						pid, level, addressName, categoryInfos);
				categoryGroupInfos.add(addressGroupInfo);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryGroupInfos;
	}

	private List<CompanyInfo> parseCompanyInfo(JSONArray jsonArray) {
		List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.get(i);
				Log.d(Common.TAG, "单个json对象：" + jo.toString());
				String entid = jo.getString("entid");
				String company = jo.getString("company");
				String addressId = jo.getString("addressId");
				String address = jo.getString("address");
				int visitNum = jo.getInt("visitNum");
				String goodCommentRate = jo.getString("goodCommentRate");
				int uid = jo.getInt("uid");
				String token = jo.getString("token");
				String longitude = jo.getString("longitude");
				String latitude = jo.getString("latitude");
				String addressName = jo.getString("addressName");
				String subAddressName = jo.getString("subAddressName");
				String commentCount = jo.getString("commentCount");
				String starCount = jo.getString("starCount");
				String totalAverage = jo.getString("totalAverage");
				String distanceValue = "";
				if (jo.has("distance"))
					distanceValue = jo.getString("distance");
				int distance = 0;
				if (!TextUtils.isEmpty(distanceValue)) {
					distance = Integer.valueOf(distanceValue);
				}
				ImgInfo imgInfo = new ImgInfo();
				if (jo.has("img")) {
					try {
						JSONObject img = jo.getJSONObject("img");

						Gson gson = new Gson();
						imgInfo = gson.fromJson(img.toString(), ImgInfo.class);
						String imgInfoStr = imgInfo.toString();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				int imgCount = jo.getInt("imgCount");
				int isFavorite = jo.getInt("isFavorite");

				CompanyInfo companyInfo = new CompanyInfo(entid, company,
						addressId, address, visitNum, goodCommentRate, uid,
						token, longitude, latitude, addressName,
						subAddressName, commentCount, starCount, totalAverage,
						imgInfo, imgCount, distance, isFavorite);
				companyInfos.add(companyInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Collections.sort(companyInfos);
		return companyInfos;
	}

}
