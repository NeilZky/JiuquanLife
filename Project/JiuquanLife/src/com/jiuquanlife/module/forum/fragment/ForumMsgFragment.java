package com.jiuquanlife.module.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.ReplyToMeActivity;

public class ForumMsgFragment extends BaseFragment{
	

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
		
	}
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.ll_reply_to_me:
				onClickReplyToMe();
				break;

			default:
				break;
			}
		}

		private void onClickReplyToMe() {
			
			startActivity(ReplyToMeActivity.class);
		}
	};
}
