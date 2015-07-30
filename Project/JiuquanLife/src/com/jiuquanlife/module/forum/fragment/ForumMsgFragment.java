package com.jiuquanlife.module.forum.fragment;

import io.rong.imkit.RongIM;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.ReplyToMeActivity;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class ForumMsgFragment extends BaseFragment {

	private ImageView iv_msg_arrow;
	private ImageView iv_im_msg_arrow;
	private TextView tv_msg_badge;
	private TextView tv_im_msg_badge;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_forum_msg, null);
		setContent(content);
		initViews();
		refresMsgBadge();
		return content;
	}
	
	
	private void initViews() {

		initClickListener(R.id.ll_reply_to_me, onClickListener);
		initClickListener(R.id.ll_conversation_with_firends, onClickListener);
		iv_msg_arrow = (ImageView) findViewById(R.id.iv_msg_arrow);
		iv_im_msg_arrow = (ImageView) findViewById(R.id.iv_im_msg_arrow);
		tv_msg_badge = (TextView) findViewById(R.id.tv_msg_badge);
		tv_im_msg_badge = (TextView) findViewById(R.id.tv_im_msg_badge);
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

	public void refresMsgBadge() {

		refreshMsgCount(App.getInstance().replyMsgCount, iv_msg_arrow,
				tv_msg_badge);
		refreshMsgCount(App.getInstance().imCount, iv_im_msg_arrow,
				tv_im_msg_badge);
	}

	private void refreshMsgCount(int count, ImageView iv, TextView badgeTv) {

		if (count > 0) {
			iv.setVisibility(View.GONE);
			badgeTv.setText(count + "");
			badgeTv.setVisibility(View.VISIBLE);
		} else {
			iv.setVisibility(View.VISIBLE);
			badgeTv.setText("");
			badgeTv.setVisibility(View.GONE);
		}

	}

}
