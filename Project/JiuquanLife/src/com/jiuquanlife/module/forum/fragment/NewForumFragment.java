package com.jiuquanlife.module.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiuquanlife.R;

public class NewForumFragment extends ForumBaseFragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View content = inflater.inflate(R.layout.fragment_forum_new, null);
		setContent(content);
		init();
		return content;
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {
		
	}
}
