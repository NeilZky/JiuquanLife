package com.jiuquanlife.module.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseFragment;

public class ForumMineFragment extends BaseFragment{


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View content = inflater.inflate(R.layout.fragment_forum_mine, null);
		setContent(content);
		initViews();
		return content;
	}

	private void initViews() {
		
		
	}
    
}
