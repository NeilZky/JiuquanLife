package com.jiuquanlife.module.community.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseFragment;

public class CommunityFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View content = inflater.inflate(R.layout.fragment_community, null);
		setContent(content);
		init();
		return content;
	}

	private void init() {

		initViews();
		initData();
	}

	private void initViews() {

	}

	private void initData() {

	}

}
