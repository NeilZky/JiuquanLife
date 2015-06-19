package com.jiuquanlife.module.forum.fragment.pageradapter;

import java.util.ArrayList;

import com.jiuquanlife.module.forum.fragment.ForumBaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ForumFragmentAdapter extends FragmentPagerAdapter {

	private ArrayList<? extends ForumBaseFragment> fragments;

	public ForumFragmentAdapter(FragmentManager fm,
			ArrayList<? extends ForumBaseFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {

		return fragments == null ? null : fragments.get(position);
	}

	@Override
	public int getCount() {

		return fragments == null ? 0 : fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
	
		if (fragments == null) {
			return null;
		}
		return fragments.get(position).getTitle();
	}
	
}
