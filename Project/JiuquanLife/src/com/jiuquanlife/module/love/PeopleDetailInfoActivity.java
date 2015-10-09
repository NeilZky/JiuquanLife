package com.jiuquanlife.module.love;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.company.activity.LargePicActivity;
import com.jiuquanlife.module.company.entity.ImgInfo;
import com.jiuquanlife.module.love.entity.LifeImg;
import com.jiuquanlife.module.love.entity.SearchUserInfo;
import com.jiuquanlife.module.publish.ShowServerImageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PeopleDetailInfoActivity extends BaseActivity {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	Double geoLat;
	Double geoLng;
	private ImageView iv_back, iv_user, iv_pic1, iv_pic2, iv_pic3, iv_pic4,
			iv_daren, iv_email, iv_phone;
	private TextView tv_name, tv_distance, tv_base, tv_search, tv_love,
			tv_self, tv_number, tv_edit, tv_hobby, tv_verify, say_hi, zan,
			chat, shoucang;
	private LinearLayout ll_iv, ll_operate;
	private RelativeLayout rl_base;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_info);
		parseIntent();
		initView();
		initMap();
		IntentFilter intentFilter = new IntentFilter(
				"com.jiuquan.updateBaseInfo");
		registerReceiver(updateInfoReceiver, intentFilter);

	}

	private void parseIntent() {
		Intent intent = getIntent();
		if (null != intent) {
			mid = intent.getStringExtra("mid");
			uid = intent.getStringExtra("uid");
		}

		if (TextUtils.isEmpty(mid)) {
			mid = Common.getMid();
			uid = Common.getUid();
			Common.checkLogin(this);
		}

	}

	private BroadcastReceiver updateInfoReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			loadData();
		}
	};

	private void sayHi(String uid) {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SAY_HI);
		sb.append("/fromUid/");
		sb.append(Common.getUid());
		sb.append("/uid/");
		sb.append(uid);
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(this, sb.toString(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");

							return;
						}
						System.out.println("Say hi response:" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.has("info")) {
								Toast.makeText(getApplicationContext(),
										jsonObject.getString("info"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private void dianzan(String maid) {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_DIAN_ZAN);
		sb.append("/uid/");
		sb.append(Common.getUid());
		sb.append("/maid/");
		sb.append(maid);
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(this, sb.toString(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");

							return;
						}
						System.out.println("Dian zan response:" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.has("info")) {
								Toast.makeText(getApplicationContext(),
										jsonObject.getString("info"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	private void shoucang(String maid) {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SHOUCHANGE);
		sb.append("/uid/");
		sb.append(Common.getUid());
		sb.append("/infoId/");
		sb.append(maid);
		System.out.println("Urlll:" + sb.toString());
		RequestHelper.getInstance().getRequest(this, sb.toString(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (TextUtils.isEmpty(response)) {
							Log.d(Common.TAG, "Failed to get Publish info");

							return;
						}
						System.out.println("shoucang response:" + response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (jsonObject.has("info")) {
								Toast.makeText(getApplicationContext(),
										jsonObject.getString("info"),
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	private void initView() {
		ll_operate = (LinearLayout) findViewById(R.id.ll_operate);
		if (Common.getUid().equals(uid)) {
			ll_operate.setVisibility(View.INVISIBLE);
		}

		say_hi = (TextView) findViewById(R.id.say_hi);
		say_hi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(PeopleDetailInfoActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
					return;
				}

				// TODO Auto-generated method stub
				if (null != userInfo) {
					sayHi(userInfo.getUid());
//					Drawable drawable = null;
//					drawable = getResources().getDrawable(R.drawable.more_hi);
//					drawable.setBounds(0, 0, 80, 80);
//					say_hi.setCompoundDrawables(null, drawable, null, null);
					say_hi.setClickable(false);
				}
			}
		});
		zan = (TextView) findViewById(R.id.zan);
		zan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(PeopleDetailInfoActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (null != userInfo) {
					dianzan(userInfo.getMaid());
//					Drawable drawable = null;
//					drawable = getResources().getDrawable(R.drawable.more_zan);
//					drawable.setBounds(0, 0, 80, 80);
//					zan.setCompoundDrawables(null, drawable, null, null);
					zan.setClickable(false);
				}

			}
		});
		chat = (TextView) findViewById(R.id.chat);
		chat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(PeopleDetailInfoActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (null != userInfo && "1".equals(userInfo.getIsFriend())) {
					RongIM.getInstance().startConversation(
							PeopleDetailInfoActivity.this,
							Conversation.ConversationType.PRIVATE,
							userInfo.getUid(), "Demo for test");
				} else {
					Toast.makeText(getApplicationContext(),
							"对方还不是您的好友，请先向对方打招呼，等对方通过验证后才能在线聊天。",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		shoucang = (TextView) findViewById(R.id.shoucang);
		shoucang.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(Common.getUid())) {
					Toast.makeText(PeopleDetailInfoActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (null != userInfo) {
					shoucang(userInfo.getMaid());
//					Drawable drawable = null;
//					drawable = getResources().getDrawable(
//							R.drawable.shoucangnew1);
//					drawable.setBounds(0, 0, 80, 80);
//					shoucang.setCompoundDrawables(null, drawable, null, null);
					shoucang.setClickable(false);
				}

			}
		});

		iv_daren = (ImageView) findViewById(R.id.iv_daren);
		iv_email = (ImageView) findViewById(R.id.iv_email);
		iv_phone = (ImageView) findViewById(R.id.iv_phone);

		tv_hobby = (TextView) findViewById(R.id.tv_hobby);
		tv_verify = (TextView) findViewById(R.id.tv_verify);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		tv_edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PeopleDetailInfoActivity.this,
						PeopleDetailBaseInfoActivity.class);
				intent.putExtra("uid", uid);
				intent.putExtra("mid", mid);
				if (Common.getUid().equals(uid)) {
					intent.putExtra("edit", true);
				}

				startActivity(intent);

			}
		});

		if (!Common.getUid().equals(uid)) {
			tv_edit.setVisibility(View.INVISIBLE);
		}

		iv_back = (ImageView) findViewById(R.id.iv_back);

		iv_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		rl_base = (RelativeLayout) findViewById(R.id.rl_base);

		iv_user = (ImageView) findViewById(R.id.iv_user);
		iv_user.setOnClickListener(onClickListener);
		iv_pic1 = (ImageView) findViewById(R.id.iv_pic1);
		iv_pic2 = (ImageView) findViewById(R.id.iv_pic2);
		iv_pic3 = (ImageView) findViewById(R.id.iv_pic3);
		iv_pic4 = (ImageView) findViewById(R.id.iv_pic4);
		//
		// iv_pic1.setOnClickListener(onClickListener);
		// iv_pic2.setOnClickListener(onClickListener);
		// iv_pic3.setOnClickListener(onClickListener);
		// iv_pic4.setOnClickListener(onClickListener);

		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		tv_base = (TextView) findViewById(R.id.tv_base);
		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_love = (TextView) findViewById(R.id.tv_love);

		tv_self = (TextView) findViewById(R.id.tv_self);
		tv_number = (TextView) findViewById(R.id.tv_number);

		ll_iv = (LinearLayout) findViewById(R.id.ll_iv);

		rl_base.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PeopleDetailInfoActivity.this,
						PeopleDetailBaseInfoActivity.class);

				intent.putExtra("uid", uid);
				intent.putExtra("mid", mid);

				intent.putExtra("edit", false);

				startActivity(intent);

			}

		});
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			String path = "";
			switch (v.getId()) {
			case R.id.iv_user:
				path = pathHeader;
				break;
			case R.id.iv_pic1:
				path = path1;
				break;
			case R.id.iv_pic2:
				path = path2;
				break;
			case R.id.iv_pic3:
				path = path3;
				break;
			case R.id.iv_pic4:
				path = path4;
				break;
			default:
				break;
			}
			if (TextUtils.isEmpty(path))
				return;
			Intent intent = new Intent(PeopleDetailInfoActivity.this,
					LargePicActivity.class);
			intent.putExtra("picPath", path);
			startActivity(intent);
		}
	};

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(PeopleDetailInfoActivity.this, "未能找到数据",
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			case MSG_INIT_DATA_SUCCESS:
				initViewData();
				stopProgressDialog();

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	String path1, path2, path3, path4, pathHeader;

	private void initViewData() {
		if (null == userInfo)
			return;

		if (!Common.getUid().equals(uid)) {
			Drawable drawable = null;
			if ("0".equals(userInfo.getHasHi())) {
//				drawable = getResources().getDrawable(R.drawable.more_hi2);
//				drawable.setBounds(0, 0, 80, 80);
//				say_hi.setCompoundDrawables(null, drawable, null, null);
			} else {
//				drawable = getResources().getDrawable(R.drawable.more_hi);
//				drawable.setBounds(0, 0, 80, 80);
//				say_hi.setCompoundDrawables(null, drawable, null, null);
				say_hi.setClickable(false);
			}

			if ("0".equals(userInfo.getHasMyZan())) {
//				drawable = getResources().getDrawable(R.drawable.more_zan2);
//				drawable.setBounds(0, 0, 80, 80);
//				zan.setCompoundDrawables(null, drawable, null, null);
			} else {
//				drawable = getResources().getDrawable(R.drawable.more_zan);
//				drawable.setBounds(0, 0, 80, 80);
//				zan.setCompoundDrawables(null, drawable, null, null);
				zan.setClickable(false);
			}

			if ("0".equals(userInfo.getIsFriend())) {
//				drawable = getResources().getDrawable(R.drawable.more_chat2);
//				drawable.setBounds(0, 0, 80, 80);
//				chat.setCompoundDrawables(null, drawable, null, null);
			} else {
//				drawable = getResources().getDrawable(R.drawable.more_chat);
//				drawable.setBounds(0, 0, 80, 80);
//				chat.setCompoundDrawables(null, drawable, null, null);
				chat.setClickable(false);
			}

		}

		String baseInfo = userInfo.getLocation() + " " + userInfo.getBirthday()
				+ " " + userInfo.getStature() + "cm " + userInfo.getEdu();
		tv_base.setText(baseInfo);
		tv_name.setText(userInfo.getNickname());
		Drawable genderDrawable = null;
		if ("男" == userInfo.getSex()) {
			genderDrawable = getResources().getDrawable(R.drawable.shouye_male);

		} else {

			genderDrawable = getResources().getDrawable(
					R.drawable.shouye_female);
		}
		genderDrawable.setBounds(0, 0, 40, 40);
		tv_name.setCompoundDrawables(null, null, genderDrawable, null);
		tv_search.setText(userInfo.getMateselection());
		tv_love.setText(userInfo.getLoveglow());
		tv_self.setText(userInfo.getResume());
		if (!"0".equals(userInfo.getTelphone())) {
			tv_number.setText(userInfo.getTelphone());
		}
		if (!Common.getUid().equals(uid)
				&& !TextUtils.isEmpty(userInfo.getTelphone())
				&& !"0".equals(userInfo.getTelphone())) {
			tv_number.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent3 = new Intent(Intent.ACTION_DIAL);
					Uri data = Uri.parse("tel:" + userInfo.getTelphone());
					intent3.setData(data);
					startActivity(intent3);

				}
			});

		}
		tv_hobby.setText(userInfo.getHobbyValue());

		if ("1".equals(userInfo.getVerifyStatus())) {
			tv_verify.setText("已认证");
		} else {
			tv_verify.setText("未认证");
		}

		if ("1".equals(userInfo.getStarVerifyStatus())) {
			iv_daren.setImageResource(R.drawable.daren_verify2);
			;
		}

		if ("1".equals(userInfo.getEmailVerifyStatus())) {
			iv_email.setImageResource(R.drawable.email_verify2);
			;
		}

		if ("1".equals(userInfo.getMobileVerifyStatus())) {
			iv_phone.setImageResource(R.drawable.mobile_verify2);
		}
		String imagePath = userInfo.getHeadsavepath()
				+ userInfo.getHeadsavename();
		System.out.println("imagePath:" + "http://www.5ijq.cn/public/uploads/"
				+ imagePath);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_default_house)
				.showImageForEmptyUri(R.drawable.ic_default_house)
				.showImageOnFail(R.drawable.ic_default_house)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(1000)).build();
		ImageLoader.getInstance().displayImage(
				"http://www.5ijq.cn/public/uploads/" + imagePath, iv_user,
				options);

		pathHeader = imagePath;

		List<LifeImg> imgs = userInfo.getLifePhotos();
		if (null != imgs && !imgs.isEmpty()) {
			String tempPath;
			final ArrayList<ImgInfo> imgInfos = new ArrayList<ImgInfo>();
			for (int i = 0; i < imgs.size(); i++) {
				LifeImg img = imgs.get(i);
				tempPath = img.getSavepath() + img.getSavename();
				ImgInfo info = new ImgInfo();
				info.setPic(tempPath);
				imgInfos.add(info);
				if (i == 0) {

					ImageLoader.getInstance().displayImage(
							"http://www.5ijq.cn/public/uploads/" + tempPath,
							iv_pic1, App.getOptions());
					path1 = tempPath;
				} else if (i == 1) {
					ImageLoader.getInstance().displayImage(
							"http://www.5ijq.cn/public/uploads/" + tempPath,
							iv_pic2, App.getOptions());
					path2 = tempPath;
				} else if (i == 2) {
					ImageLoader.getInstance().displayImage(
							"http://www.5ijq.cn/public/uploads/" + tempPath,
							iv_pic3, App.getOptions());
					path3 = tempPath;
				} else if (i == 3) {
					ImageLoader.getInstance().displayImage(
							"http://www.5ijq.cn/public/uploads/" + tempPath,
							iv_pic4, App.getOptions());
					path4 = tempPath;
				}
			}

			ll_iv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PeopleDetailInfoActivity.this,
							ShowServerImageActivity.class);
					// intent.putExtra("fromWhere",
					// ShowServerImageActivity.FROM_LOVE);
					intent.putParcelableArrayListExtra("imgInfos", imgInfos);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplicationContext().startActivity(intent);

				}
			});
		} else {
			ll_iv.setVisibility(View.GONE);
		}

		String distance = Utils.getDistance(userInfo.getDistance());
		tv_distance.setText(distance);

		if (Common.getUid().equals(uid)) {
			tv_distance.setVisibility(View.INVISIBLE);
		}

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

	private String mid = "54";
	private String uid = "53";

	private void loadData() {
		startProgressDialog("加载中...");
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_DETAIL_URL);
		sb.append("/mid/");
		sb.append(mid);
		sb.append("/currentLon/");
		sb.append(geoLng);
		sb.append("/currentLat/");
		sb.append(geoLat);
		sb.append("/uid/");
		sb.append(uid);
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

	private SearchUserInfo userInfo;

	private void parse(String response) {
		System.out.println("result:" + response);
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");
				if (jsonObject.has("detialInfo")) {
					jsonObject = jsonObject.getJSONObject("detialInfo");
					System.out.println("detialInfo:::" + jsonObject);
					Gson gson = new Gson();
					userInfo = gson.fromJson(jsonObject.toString(),
							SearchUserInfo.class);
					if (null != userInfo) {
						System.out.println("userInfo:" + userInfo.toString());
						mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

					} else {
						mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
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
}
