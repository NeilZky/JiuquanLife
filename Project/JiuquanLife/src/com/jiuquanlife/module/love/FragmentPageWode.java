package com.jiuquanlife.module.love;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.module.love.entity.SearchUserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FragmentPageWode extends Fragment {
	private RelativeLayout rl_selfinfo, rl_selfgallery, rl_myfriend, rl_sayhi;
	ImageView iv_user;
	TextView tv_name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_love_myself, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		rl_selfinfo = (RelativeLayout) view.findViewById(R.id.rl_selfinfo);
		rl_selfgallery = (RelativeLayout) view
				.findViewById(R.id.rl_selfgallery);
		rl_myfriend = (RelativeLayout) view.findViewById(R.id.rl_myfriend);
		rl_sayhi = (RelativeLayout) view.findViewById(R.id.rl_sayhi);

		rl_selfinfo.setOnClickListener(clickListener);
		rl_selfgallery.setOnClickListener(clickListener);
		rl_myfriend.setOnClickListener(clickListener);
		rl_sayhi.setOnClickListener(clickListener);

		iv_user = (ImageView) view.findViewById(R.id.iv_user);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		if (TextUtils.isEmpty(Common.getUid())) {
			tv_name.setVisibility(View.INVISIBLE);

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (TextUtils.isEmpty(Common.getUid())) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		}
		loadData();
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rl_selfinfo:
				Intent intent1 = new Intent(getActivity(),
						PeopleDetailInfoActivity.class);
				startActivity(intent1);
				break;
			case R.id.rl_selfgallery:
				Intent intent = new Intent(getActivity(),
						UserSelftGalleryActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_myfriend:
				Intent intent2 = new Intent(getActivity(),
						LoveFriendListActivity.class);
				startActivity(intent2);
				break;
			case R.id.rl_sayhi:
				Intent intent3 = new Intent(getActivity(),
						LoveSayHiActivity.class);
				startActivity(intent3);
				break;
			default:
				break;
			}

		}
	};

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:

				break;
			case MSG_INIT_DATA_SUCCESS:
				initViewData();

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void initViewData() {
		if (null == userInfo) {
			return;
		}
		tv_name.setText(userInfo.getNickname());

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

	}

	private String mid = Common.getMid();
	private String uid = Common.getUid();

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_WO_DE);
		// sb.append("/mid/");
		// sb.append(mid);
		sb.append("/uid/");
		sb.append(Common.getUid());

		System.out.println("Urlll:" + sb.toString());

		RequestHelper.getInstance().getRequest(
				getActivity().getApplicationContext(), sb.toString(),
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
						mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

					} else {
						mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			e.printStackTrace();
		}
	}
}