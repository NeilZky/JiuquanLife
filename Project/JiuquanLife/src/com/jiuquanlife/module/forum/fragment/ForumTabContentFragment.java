package com.jiuquanlife.module.forum.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.jiuquanlife.R;
import com.jiuquanlife.module.base.BaseFragment;
import com.jiuquanlife.module.forum.fragment.adapter.ForumFragmentAdapter;

public class ForumTabContentFragment extends BaseFragment {

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ForumFragmentAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View content = inflater.inflate(R.layout.fragment_forum, null);
		setContent(content);
		initViews();
		return content;
	}
	
	private void initViews(){
	
		tabs = (PagerSlidingTabStrip) findViewById(R.id.pts_forum);
		pager = (ViewPager) findViewById(R.id.vp_forum);
		ArrayList<ForumBaseFragment> fragments = new ArrayList<ForumBaseFragment>();
		
		NewForumFragment plateForumFragment = new NewForumFragment();
		plateForumFragment.setTitle(getActivity().getString(R.string.title_forum_plate));
		
		NewForumFragment newForumFragment = new NewForumFragment();
		newForumFragment.setTitle(getActivity().getString(R.string.title_forum_new));
		
		NewForumFragment essenceForumFragment = new NewForumFragment();
		essenceForumFragment.setTitle(getActivity().getString(R.string.title_forum_essence));
		
		fragments.add(plateForumFragment);
		fragments.add(newForumFragment);
		fragments.add(essenceForumFragment);
		adapter = new ForumFragmentAdapter(getFragmentManager(), fragments);
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
	}

}
