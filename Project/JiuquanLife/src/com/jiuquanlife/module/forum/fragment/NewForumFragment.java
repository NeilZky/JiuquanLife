package com.jiuquanlife.module.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jiuquanlife.R;

public class NewForumFragment extends ForumBaseFragment{
	
	private ListView lv_new_forum;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View content = inflater.inflate(R.layout.fragment_forum_essence, null);
		setContent(content);
		init();
		return content;
	}

	private void init() {
		
		initViews();
	}

	private void initViews() {
		
		lv_new_forum = (ListView) findViewById(R.id.lv_essence_forum);
	}
	
	private void getData() {
		
	}
	
}
