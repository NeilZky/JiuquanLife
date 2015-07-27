package com.jiuquanlife.module.im;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.activity.SelectFirendActivity;
import com.jiuquanlife.vo.forum.usercenter.UserInfo;

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
	
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_start_conversation:
			onClickStartConversation();
			break;
		default:
			break;
		}
	}

	private void onClickStartConversation() {
		
		startActivityForResult(SelectFirendActivity.class, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			onSelectFriendResult(resultCode, data);
			break;
		default:
			break;
		}
	}
	
	private void onSelectFriendResult(int resultCode, Intent data) {
		
		if(resultCode == Activity.RESULT_OK) {
			UserInfo employee = (UserInfo) data.getSerializableExtra(SelectFirendActivity.EXTRA_USER_INFO);
			if(employee!=null) {
				RongIM.getInstance().startPrivateChat(getActivity(), String.valueOf(employee.uid), employee.name);
			}
		}
		
	}
}
