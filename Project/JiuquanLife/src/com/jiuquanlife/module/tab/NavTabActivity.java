package com.jiuquanlife.module.tab;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.OnTabChangeListener;

import com.jiuquanlife.R;
import com.jiuquanlife.module.focus.fragment.FocusFragment;
import com.jiuquanlife.module.forum.fragment.MainForumFragment;

public class NavTabActivity extends FragmentActivity{
	
	public static final String INTENT_KEY_TAB_TAG = "tab_tag";
	
	private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navtab);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content_navtab);
        View focusIndicator = getLayoutInflater().inflate(R.layout.indicator_focus, null);
        mTabHost.addTab(mTabHost.newTabSpec(FocusFragment.class.getSimpleName()).setIndicator(focusIndicator),
                FocusFragment.class, null);
        View communityIndicator = getLayoutInflater().inflate(R.layout.indicator_forum, null);
        mTabHost.addTab(mTabHost.newTabSpec(MainForumFragment.class.getSimpleName()).setIndicator(communityIndicator),
        		MainForumFragment.class, null);
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
