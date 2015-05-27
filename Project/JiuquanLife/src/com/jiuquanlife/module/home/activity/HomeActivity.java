package com.jiuquanlife.module.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.jiuquanlife.R;
import com.jiuquanlife.module.focus.fragment.FocusFragment;
import com.jiuquanlife.module.forum.activity.ForumTabActvity;
import com.jiuquanlife.module.forum.fragment.ForumTabContentFragment;
import com.jiuquanlife.module.home.adapter.HomeAdapter;
import com.jiuquanlife.module.house.fragment.HouseFragment;
import com.jiuquanlife.module.tab.NavTabActivity;
import com.jiuquanlife.view.MovingView;
import com.jiuquanlife.view.SexangleImageView;
import com.jiuquanlife.view.SexangleImageView.OnSexangleImageClickListener;

public class HomeActivity extends Activity {

	private SexangleImageView homeSiv;
	private SexangleImageView houseSiv;
	private SexangleImageView hotelSiv;
	private MovingView mv;
	private ViewPager homeVp;
	private View leftHomeView ;
	private View rightHomeView ;
	private HomeAdapter homeAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	
	public void initView(){
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initViewPager();
		initSixAngelView();
		initMovingView();
	}
	
	private void initViewPager() {
		
		homeVp = (ViewPager) findViewById(R.id.vp_home);
		homeAdapter = new HomeAdapter();
		leftHomeView = getLayoutInflater().inflate(R.layout.view_home_left, null);
		rightHomeView = getLayoutInflater().inflate(R.layout.view_home_left, null);
		homeAdapter.addView(leftHomeView);
		homeAdapter.addView(rightHomeView);
		homeVp.setAdapter(homeAdapter);
	}
	
	
	private void initSixAngelView() {
		
		homeSiv = (SexangleImageView) leftHomeView.findViewById(R.id.siv_main);
		houseSiv = (SexangleImageView) leftHomeView.findViewById(R.id.siv_house);
		hotelSiv = (SexangleImageView) leftHomeView.findViewById(R.id.siv_community);
		homeSiv.setOnSexangleImageClick(listener);
		houseSiv.setOnSexangleImageClick(listener);
		hotelSiv.setOnSexangleImageClick(listener);
	}
	
	private void initMovingView() {
		
		mv = (MovingView) findViewById(R.id.mv);
		Rect rect= new Rect();  
		this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);  
		mv.setBitmap(R.drawable.bg_nav);
	}
	
	OnSexangleImageClickListener listener=new OnSexangleImageClickListener() {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.siv_main:
				onClickFocus();
				break;
			case R.id.siv_community:
				onClickCommunity();
				break;
			case R.id.siv_house:
				onClickHouse();
				break;
			}
		}

		private void onClickHouse() {
			
			Intent intent = new Intent(HomeActivity.this, NavTabActivity.class);
			intent.putExtra(NavTabActivity.INTENT_KEY_TAB_TAG, HouseFragment.class.getSimpleName());
			startActivity(intent);
		}

		private void onClickFocus() {
			
			Intent intent = new Intent(HomeActivity.this, NavTabActivity.class);
			intent.putExtra(NavTabActivity.INTENT_KEY_TAB_TAG, FocusFragment.class.getSimpleName());
			startActivity(intent);
		}

		private void onClickCommunity() {
			
			Intent intent = new Intent(HomeActivity.this, ForumTabActvity.class);
			intent.putExtra(NavTabActivity.INTENT_KEY_TAB_TAG, ForumTabContentFragment.class.getSimpleName());
			startActivity(intent);
		}
	};
}
