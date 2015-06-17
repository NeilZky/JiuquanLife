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
import com.jiuquanlife.module.forum.fragment.pageradapter.ForumFragmentAdapter;

public class BorderTabContentFragment extends BaseFragment {

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private ForumFragmentAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View content = inflater.inflate(R.layout.fragment_border, null);
		setContent(content);
		initViews();
		return content;
	}
    
	
	private void initViews(){
	
		tabs = (PagerSlidingTabStrip) findViewById(R.id.pts_forum);
		pager = (ViewPager) findViewById(R.id.vp_forum);
		ArrayList<ForumBaseFragment> fragments = new ArrayList<ForumBaseFragment>();
		
		PostListFragment allPostFragment = new PostListFragment();
		allPostFragment.setTitle(getActivity().getString(R.string.title_forum_all));
		allPostFragment.setSortBy("all");
		
		PostListFragment newForumFragment = new PostListFragment();
		newForumFragment.setTitle(getActivity().getString(R.string.title_forum_new));
		newForumFragment.setSortBy("new");
		
		PostListFragment essenceForumFragment = new PostListFragment();
		essenceForumFragment.setTitle(getActivity().getString(R.string.title_forum_essence));
		essenceForumFragment.setSortBy("essence");

		PostListFragment topForumFragment = new PostListFragment();
		topForumFragment.setTitle(getActivity().getString(R.string.title_forum_top));
		topForumFragment.setSortBy("top");
		
		fragments.add(allPostFragment);
		fragments.add(newForumFragment);
		fragments.add(essenceForumFragment);
		fragments.add(topForumFragment);
		adapter = new ForumFragmentAdapter(getFragmentManager(), fragments);
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
	}

}
