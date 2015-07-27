package com.jiuquanlife.module.forum.fragment;

import io.rong.imkit.RongIM;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.ReplyToMeActivity;
import com.jiuquanlife.module.im.ConversationActivity;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class ForumMsgFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_forum_msg, null);
		setContent(content);
		initViews();
		return content;
	}

	private void initViews() {

		initClickListener(R.id.ll_reply_to_me, onClickListener);
		initClickListener(R.id.ll_conversation_with_firends, onClickListener);

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.ll_reply_to_me:
				onClickReplyToMe();
				break;
			case R.id.ll_conversation_with_firends:
				onClickConversation();
				break;
			default:
				break;
			}
		}

		private void onClickConversation() {

			User user = SharePreferenceUtils.getObject(
					SharePreferenceUtils.USER, User.class);
			if (user == null) {
				startActivity(LoginActivity.class);
			} else if (RongIM.getInstance() != null) {
				RongIM.getInstance().startConversationList(getActivity());
			}
		}

		private void onClickReplyToMe() {

			User user = SharePreferenceUtils.getObject(
					SharePreferenceUtils.USER, User.class);
			if (user == null) {
				startActivity(LoginActivity.class);
			} else {
				startActivity(ReplyToMeActivity.class);
			}
		}
	};
}
