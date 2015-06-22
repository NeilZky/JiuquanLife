package com.jiuquanlife.module.forum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.activity.CreatePostActivity;
import com.jiuquanlife.utils.ToastHelper;

public class CreatePostTypeFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_create_post_type,
				null);
		setContent(content);
		initViews();
		return content;
	}

	private void initViews() {

		initClickListener(R.id.tv_create_post_text, onClickListener);
		initClickListener(R.id.tv_create_post_local_photo, onClickListener);
		initClickListener(R.id.tv_create_post_camera, onClickListener);
		initClickListener(R.id.tv_create_post_voice, onClickListener);

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(), CreatePostActivity.class);
			switch (v.getId()) {
			case R.id.tv_create_post_text:
				intent.putExtra(CreatePostActivity.EXTRA_CREATE_TYPE,
						CreatePostActivity.TYPE_TEXT);
				break;
			case R.id.tv_create_post_local_photo:
				intent.putExtra(CreatePostActivity.EXTRA_CREATE_TYPE,
						CreatePostActivity.TYPE_LOCAL_PHOTO);
				break;
			case R.id.tv_create_post_camera:
				intent.putExtra(CreatePostActivity.EXTRA_CREATE_TYPE,
						CreatePostActivity.TYPE_CAMERA);
				break;
			case R.id.tv_create_post_voice:
				ToastHelper.showS("本功能暂不支持");
				return;
			}
			startActivity(intent);
		}
	};

}
