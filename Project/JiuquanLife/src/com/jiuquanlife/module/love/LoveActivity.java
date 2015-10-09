package com.jiuquanlife.module.love;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.love.entity.SearchUserInfo;

public class LoveActivity extends FragmentActivity {
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;

	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { FragmentPageShouye.class,
			FragmentPageQuanzi.class, FragmentPageXiaoxi.class,
			FragmentPageWode.class };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_quanzi_btn, R.drawable.tab_message_btn,
			R.drawable.tab_selfinfo_btn };

	// Tab选项卡的文字
	private String mTextviewArray[] = { "首页", "圈子", "消息", "我的" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_love_main);

		initView();
		loadData();
		initRongInfoProvider();
		loadUserData();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);

		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}

	private void parseToken(String result) {
		System.out.println("Result:" + result);
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("data")) {
				fromToken = jsonObject.getString("data");
			}

			if (!TextUtils.isEmpty(fromToken)) {
				mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initRongInfoProvider() {
		RongIM.setUserInfoProvider(new UserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String userId) {
				System.out.println("call getUserInfo:" + userId);
				return LoveFriendListActivity.UserInfos.get(userId);
			}
		}, true);
	}

	public static String fromToken;

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:

				break;
			case MSG_INIT_DATA_SUCCESS:
				initConnectRong();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void initConnectRong() {
		System.out.println("initConnectRong");
		RongIM.connect(fromToken, new RongIMClient.ConnectCallback() {

			@Override
			public void onSuccess(String arg0) {
				System.out.println("Success connect");
				Toast.makeText(getApplicationContext(), "Connect success",
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onError(ErrorCode arg0) {
				System.out.println("Failed connect");
				Toast.makeText(getApplicationContext(), "Connect Error",
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onTokenIncorrect() {
				// TODO Auto-generated method stub

			}
		});
	}

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_GET_TOKEN);
		sb.append("/uid/");
		sb.append(Common.getUid());
		sb.append("/username/");
		sb.append(Common.getUserName());
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
						parseToken(response);
					}
				});
	}

	private void loadUserData() {
		if (TextUtils.isEmpty(Common.getUid()))
			return;

		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_WO_DE);
		// sb.append("/mid/");
		// sb.append(mid);
		sb.append("/uid/");
		sb.append(Common.getUid());

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
				if (jsonObject.has("info")) {
					jsonObject = jsonObject.getJSONObject("info");
					System.out.println("detialInfo:::" + jsonObject);
					Gson gson = new Gson();
					userInfo = gson.fromJson(jsonObject.toString(),
							SearchUserInfo.class);
					if (null != userInfo) {
						System.out.println("userInfo:" + userInfo.toString());

						Common.saveMid(userInfo.getMaid());
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			e.printStackTrace();
		}
	}

}
