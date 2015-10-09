package com.jiuquanlife.module.love;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.company.BaseActivity;
import com.jiuquanlife.module.company.Common;

public class ConnectActivity extends BaseActivity {
	private String fromToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_connect);
		findViewById(R.id.connect).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						handleConnect();
					}
				});
		loadData();

		findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				RongIM.getInstance()
						.startConversationList(ConnectActivity.this);

			}
		});

		findViewById(R.id.start2).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						RongIM.getInstance().startConversation(
								ConnectActivity.this,
								Conversation.ConversationType.PRIVATE, "203",
								"Demo for test");

					}
				});

		RongIM.setUserInfoProvider(new UserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String userId) {
				

				return LoveFriendListActivity.UserInfos.get(userId);
			}
		}, true);

	}

	private void handleConnect() {
		RongIM.connect(fromToken, new RongIMClient.ConnectCallback() {

			@Override
			public void onSuccess(String arg0) {
				Toast.makeText(getApplicationContext(), "Connect success",
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onError(ErrorCode arg0) {
				Toast.makeText(getApplicationContext(), "Connect Error",
						Toast.LENGTH_LONG).show();

			}

			@Override
			public void onTokenIncorrect() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private static final int MSG_INIT_DATA_FAILED = 1;
	private static final int MSG_INIT_DATA_SUCCESS = 2;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_DATA_FAILED:

				break;
			case MSG_INIT_DATA_SUCCESS:

				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private void parseToken(String result) {
		System.out.println("Result:" + result);
		try {
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("data")) {
				fromToken = jsonObject.getString("data");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String uid = "126";
	private String username = "tt";

	private void loadData() {
		StringBuilder sb = new StringBuilder();
		sb.append(Common.LOVE_GET_TOKEN);
		sb.append("/uid/");
		sb.append(uid);
		sb.append("/username/");
		sb.append(username);
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

}
