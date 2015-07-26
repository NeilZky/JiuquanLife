package com.jiuquanlife.module.im;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.utils.TextViewUtils;


public class ConversationActivity extends com.jiuquanlife.module.base.BaseActivity {

	private TextView tv_title_conversation_activity;

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.activity_conversation);
		tv_title_conversation_activity = (TextView) findViewById(R.id.tv_title_conversation_activity);
		String title = getIntent().getData().getQueryParameter("title");
		TextViewUtils.setText(tv_title_conversation_activity, title);
		reconnectIfNeed();
	}

	private void reconnectIfNeed() {

		Intent intent = getIntent();
		if (intent != null && intent.getData() != null
				&& intent.getData().getScheme().equals("rong")
				&& intent.getData().getQueryParameter("push") != null) {
			if (intent.getData().getQueryParameter("push").equals("true")) {
				reconnect();
			}
		}
	}
	
	private void reconnect() {
		
		RongCloudBll.getInstance().reconnectRongCloud(
				new ConnectCallback() {

					@Override
					public void onSuccess(String arg0) {
						setFragment();
					}

					@Override
					public void onError(ErrorCode arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onTokenIncorrect() {
						// TODO Auto-generated method stub

					}
				});
		
	}

	private void setFragment() {

		String title = getIntent().getData().getQueryParameter("title");
		String targetId = getIntent().getData().getQueryParameter("targetId");
		ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager()
				.findFragmentById(R.id.conversation);
		Uri uri = Uri
				.parse("rong://" + getApplicationInfo().packageName)
				.buildUpon()
				.appendPath("conversation")
				.appendPath(
						io.rong.imlib.model.Conversation.ConversationType.PRIVATE
								.getName().toLowerCase())
				.appendQueryParameter("targetId", String.valueOf(targetId))
				.appendQueryParameter("title", title).build();
		fragment.setUri(uri);
	}
}
