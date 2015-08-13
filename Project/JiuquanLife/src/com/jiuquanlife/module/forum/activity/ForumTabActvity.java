package com.jiuquanlife.module.forum.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.forum.fragment.CreatePostTypeFragment;
import com.jiuquanlife.module.forum.fragment.ForumMineFragment;
import com.jiuquanlife.module.forum.fragment.ForumMsgFragment;
import com.jiuquanlife.module.forum.fragment.ForumTabContentFragment;
import com.jiuquanlife.module.forum.fragment.TopicListFragment;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class ForumTabActvity extends FragmentActivity{
	
	public static final String INTENT_KEY_TAB_TAG = "tab_tag";
	
	private FragmentTabHost mTabHost;
	private View msgPlateIndicator;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forumtab);
        initTabs();
        App.getInstance().forumTabActvity = this;
    }
	
    @Override
    protected void onResume() {

    	super.onResume();
    	getData();
    	refresMsgBadge();
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
        mTabHost.addTab(mTabHost.newTabSpec(CreatePostTypeFragment.class.getSimpleName()).setIndicator(createPostIndicator),
        		CreatePostTypeFragment.class, null);
        
        msgPlateIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_msg, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumMsgFragment.class.getSimpleName()).setIndicator(msgPlateIndicator),
        		ForumMsgFragment.class, null);
        
        View minePlateIndicator = getLayoutInflater().inflate(R.layout.indicator_forum_mine, null);
        mTabHost.addTab(mTabHost.newTabSpec(ForumMineFragment.class.getSimpleName()).setIndicator(minePlateIndicator),
        		ForumMineFragment.class, null);
        
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

	
	public void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "message/heart");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		if(user == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return;
		}
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("userId", user.uid + "");
		RequestHelper.getInstance().getRequestMap(this,
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						int count  = 0;
						try {
							JSONObject jsonObject = new JSONObject(response);
							JSONObject body = jsonObject.getJSONObject("body");
							JSONObject replyInfo = body.getJSONObject("replyInfo");
							count = replyInfo.getInt("count");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						App.getInstance().replyMsgCount = count;
						if(App.getInstance().forumTabActvity!=null) {
							App.getInstance().forumTabActvity.refresMsgBadge();
						}
						System.out.println(response);
						System.out.println(response);
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}
	
	
	@Override
	protected void onDestroy() {

		super.onDestroy();
		App.getInstance().forumTabActvity = null;
	}

	public void refresMsgBadge() {
		
		ImageView iv_badge_indicator_im = (ImageView) msgPlateIndicator.findViewById(R.id.iv_badge_indicator_im);
		if(App.getInstance().imCount >0 || App.getInstance().replyMsgCount >0) {
			iv_badge_indicator_im.setVisibility(View.VISIBLE);
		} else {
			iv_badge_indicator_im.setVisibility(View.GONE);
		}
		ForumMsgFragment fragment = (ForumMsgFragment) getSupportFragmentManager().findFragmentByTag(ForumMsgFragment.class.getSimpleName());
		if(fragment!=null) {
			fragment.refresMsgBadge();
		}
	}
	

}
