package com.jiuquanlife.module.forum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.UserCenterActivity;
import com.jiuquanlife.module.forum.activity.UserListActivity;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class ForumMineFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getContent() == null) {
			View content = inflater.inflate(R.layout.fragment_forum_mine, null);
			setContent(content);
			initViews();
		}

		ViewGroup parent = (ViewGroup) getContent().getParent();
		if (parent != null) {
			parent.removeView(getContent());
		}
		return getContent();
	}

	private void initViews() {

		initClickListener(R.id.tv_to_user_center_, onClickListener);
		initClickListener(R.id.tv_to_user_nearby, onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.tv_to_user_center_:
				onClickUserCenter();
				break;
			case R.id.tv_to_user_nearby:
				onClickUserNearby();
				break;
			default:
				break;
			}
		}

		private void onClickUserNearby() {
			
			Intent intent = new Intent(getActivity(), UserListActivity.class);
			intent.putExtra(UserListActivity.EXTRA_TYPE, UserListActivity.TYPE_ALL);
			intent.putExtra(UserListActivity.EXTRA_TITLE, "¸½½üÓÃ»§");
			User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
			intent.putExtra(UserListActivity.EXTRA_UID, user.uid);
			startActivity(intent);
		}

		private void onClickUserCenter() {
			
			User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
			Intent intent = new Intent();
			if(user!=null) {
				intent.setClass(getActivity(), UserCenterActivity.class);
			} else {
				intent.setClass(getActivity(), LoginActivity.class);
			}
			startActivity(intent);
		}
	};
}
