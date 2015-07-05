package com.jiuquanlife.module.forum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.UserCenterActivity;

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
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.tv_to_user_center_:
				onClickUserCenter();
				break;

			default:
				break;
			}
		}

		private void onClickUserCenter() {

			Intent intent = new Intent(getActivity(), UserCenterActivity.class);
			startActivity(intent);
		}
	};
}
