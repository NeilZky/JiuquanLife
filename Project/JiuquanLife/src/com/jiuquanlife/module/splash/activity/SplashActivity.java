package com.jiuquanlife.module.splash.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jiuquanlife.R;
import com.jiuquanlife.module.home.activity.HomeActivity;
import com.jiuquanlife.module.home.adapter.SplashAdapter;
import com.jiuquanlife.utils.SharePreferenceUtils;

public class SplashActivity extends Activity implements OnPageChangeListener {

    private static final int SHOW_TIME_MIN = 2000;

    private ViewPager vp_splash;
    private Button btn_jump_splash;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        vp_splash = (ViewPager) findViewById(R.id.vp_splash);
        btn_jump_splash = (Button) findViewById(R.id.btn_jump_splash);
        SplashAdapter sa = new SplashAdapter(this);
        vp_splash.setAdapter(sa);
        vp_splash.setOnPageChangeListener(this);
        boolean openedApp = SharePreferenceUtils.getBoolean(SharePreferenceUtils.OPENED_APP);
        if(openedApp) {
        	jump();
        } 
    }
    
    public void onClickJump(View v) {
    	
    	SharePreferenceUtils.putBoolean(SharePreferenceUtils.OPENED_APP, true);
    	jump();
    }
    
    private void jump() {
    	
    	Intent intent = new Intent();
        intent.setClass(SplashActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int positon) {
		
		if(positon ==5 ) {
			btn_jump_splash.setVisibility(View.VISIBLE);
		} else {
			btn_jump_splash.setVisibility(View.GONE);
		}
	}
    

}
