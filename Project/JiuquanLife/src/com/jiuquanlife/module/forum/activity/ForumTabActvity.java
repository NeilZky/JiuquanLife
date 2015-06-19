package com.jiuquanlife.module.forum.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.OnTabChangeListener;

import com.jiuquanlife.R;
import com.jiuquanlife.module.forum.fragment.CreatePostFragment;
import com.jiuquanlife.module.forum.fragment.ForumTabContentFragment;
import com.jiuquanlife.module.forum.fragment.TopicListFragment;

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
        mTabHost.addTab(mTabHost.newTabSpec(TopicListFragment.class.getSimpleName()).setIndicator(forumPlateIndicator),
        		TopicListFragment.class, null);
        
        View createPostIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_new, null);
        mTabHost.addTab(mTabHost.newTabSpec(CreatePostFragment.class.getSimpleName()).setIndicator(createPostIndicator),
        		CreatePostFragment.class, null);
        
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
		}
	};
	
	

}
