package com.jiuquanlife.module.love;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;
import com.jiuquanlife.module.love.LoveSayHiAdapter.ButtonOnclick;
import com.jiuquanlife.module.love.LoveSayHiAdapter.ViewHolder;
import com.jiuquanlife.module.love.entity.SayHi;
import com.jiuquanlife.module.love.entity.SayHiInfos;

public class LoveSayHiActivity extends BaseActivity {
	private ListView lv;
	private LoveSayHiAdapter adapter;
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_say_hi);
		back=(ImageView) findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		lv = (ListView) findViewById(R.id.lv_pl);
		adapter = new LoveSayHiAdapter(this, null);
		adapter.setOnButtonClick(buttonOnclick);
		lv.setAdapter(adapter);
		loadData();
	}

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:
				stopProgressDialog();
				Toast.makeText(LoveSayHiActivity.this, "未能找到数据",
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

	private void initViewData() {
		if (null == sayHiInfos || null == sayHiInfos.getHiList()) {
			return;
		}

		adapter.refreshData(sayHiInfos.getHiList());
	}

	private String uid = Common.getUid();
	private SayHiInfos sayHiInfos;

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_SAY_HI_LIST);
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

	private void parse(String response) {
		System.out.println("result:" + response);
		try {

			Log.d(Common.TAG, "Detail data:" + response);
			JSONObject jsonObject = new JSONObject(response);

			if (jsonObject.has("data")) {
				jsonObject = jsonObject.getJSONObject("data");

				Gson gson = new Gson();
				sayHiInfos = gson.fromJson(jsonObject.toString(),
						SayHiInfos.class);
				if (null != sayHiInfos) {
					System.out.println("userInfo:" + sayHiInfos.toString());
					mHandler.sendEmptyMessage(MSG_INIT_DATA_SUCCESS);

				} else {
					mHandler.sendEmptyMessage(MSG_INIT_DATA_FAILED);

				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private LoveSayHiAdapter.ButtonOnclick buttonOnclick = new ButtonOnclick() {

		@Override
		public void handleClick(String uid, String fromuid,
				final String action, final ViewHolder holder,
				final int posistion, final SayHi sayHi) {
			startProgressDialog("打招呼中...");
			StringBuilder sb = new StringBuilder();
			sb.append(Common.LOVE_HANDLE_SAY_HI);
			sb.append("/uid/");
			sb.append(uid);
			sb.append("/fromuid/");
			sb.append(fromuid);
			sb.append("/action/");
			sb.append(action);
			System.out.println("Urlll:" + sb.toString());
			RequestHelper.getInstance().getRequest(getApplicationContext(),
					sb.toString(), new Listener<String>() {

						@Override
						public void onResponse(String response) {
							stopProgressDialog();
							if (TextUtils.isEmpty(response)) {
								Log.d(Common.TAG, "Failed to get Publish info");

								return;
							}
							System.out.println("Handle click：" + response);
							try {
								JSONObject jsonObject = new JSONObject(response);
								if (jsonObject.has("code")) {
									String code = jsonObject.getString("code");
									if ("0".equals(code)) {
										// holder.iv_status
										// .setVisibility(View.VISIBLE);
										// holder.iv_pass.setVisibility(View.GONE);
										// holder.iv_deny.setVisibility(View.GONE);
										if ("0".equals(action)) {
											sayHi.setStatus("1");
										} else {
											sayHi.setStatus("2");
										}

										adapter.replaceData(posistion, sayHi);

									}
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
		}
	};
}
