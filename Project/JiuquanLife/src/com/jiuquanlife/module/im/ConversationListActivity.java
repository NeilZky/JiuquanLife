package com.jiuquanlife.module.im;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;

public class ConversationListActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		setContentView(R.layout.activity_conversation_list);
		reconnectIfNeed();
	}

	private void reconnectIfNeed() {

		Intent intent = getIntent();
		if (intent != null && intent.getData() != null
				&& intent.getData().getScheme().equals("rong")
				&& intent.getData().getQueryParameter("push") != null) {
			// 通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
			if (intent.getData().getQueryParameter("push").equals("true")) {
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
		}
	}

	private void setFragment() {

		ConversationListFragment fragment = new ConversationListFragment();
		Uri uri = Uri.parse("rong://" +getActivity().getApplicationInfo().packageName).buildUpon()
		        .appendPath("conversationlist")
		        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
		        .build();
		fragment.setUri(uri);

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//xxx 为你要加载的 Id
		transaction.add(R.id.conversation_list, fragment);
		transaction.commit();
	}
}
