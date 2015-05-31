package com.jiuquanlife.module.forum.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.adapter.ImageViewPagerAdapter;
import com.jiuquanlife.constance.CommonConstance;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.forum.fragment.ForumTabContentFragment;
import com.jiuquanlife.module.post.PostDetailActivity;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.vo.FocusInfo;
import com.jiuquanlife.vo.PhotoInfo;
import com.jiuquanlife.vo.convertor.ConvertUtils;

public class ForumTabActvity extends FragmentActivity{
	
	public static final String INTENT_KEY_TAB_TAG = "tab_tag";
	
	private FragmentTabHost mTabHost;
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forumtab);
        initTabs();
        
    }
	
    private void initTabs() {
    	
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content_forumtab);
        View forumMainIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_main, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumTabContentFragment.class.getSimpleName()).setIndicator(forumMainIndicator),
        		ForumTabContentFragment.class, null);
        
        View forumPlateIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_plate, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumTabContentFragment.class.getSimpleName()).setIndicator(forumPlateIndicator),
        		ForumTabContentFragment.class, null);
        
        View newPlateIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_new, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumTabContentFragment.class.getSimpleName()).setIndicator(newPlateIndicator),
        		ForumTabContentFragment.class, null);
        
        View msgPlateIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_msg, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumTabContentFragment.class.getSimpleName()).setIndicator(msgPlateIndicator),
        		ForumTabContentFragment.class, null);
        
        View minePlateIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_mine, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumTabContentFragment.class.getSimpleName()).setIndicator(minePlateIndicator),
        		ForumTabContentFragment.class, null);
        
        mTabHost.setOnTabChangedListener(onTabChangeListener);
        mTabHost.getTabWidget().setDividerDrawable(null);
        String tag = getIntent().getStringExtra(INTENT_KEY_TAB_TAG);
        mTabHost.setCurrentTabByTag(tag);
    }
    
    
    private OnTabChangeListener onTabChangeListener = new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tag) {
			System.out.println(tag);
		}
	};
	
	

}