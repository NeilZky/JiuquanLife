package com.jiuquanlife.module.home.activity;

import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

import com.jiuquanlife.R;
import com.jiuquanlife.module.base.FragmentContentActivity;
import com.jiuquanlife.module.focus.fragment.FocusFragment;
import com.jiuquanlife.module.forum.activity.ForumTabActvity;
import com.jiuquanlife.module.forum.activity.ReadliyTakeActivity;
import com.jiuquanlife.module.forum.fragment.ForumTabContentFragment;
import com.jiuquanlife.module.home.adapter.HomeAdapter;
import com.jiuquanlife.module.house.fragment.HouseFragment;
import com.jiuquanlife.module.im.RongCloudBll;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.module.tab.NavTabActivity;
import com.jiuquanlife.view.MovingView;
import com.jiuquanlife.view.SexangleImageView;
import com.jiuquanlife.view.SexangleImageView.OnSexangleImageClickListener;

public class HomeActivity extends Activity {

	private SexangleImageView homeSiv;
	private SexangleImageView houseSiv;
	private SexangleImageView hotelSiv;
	private SexangleImageView readliyTakeSiv;
	private SexangleImageView loginSiv;
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
		RongCloudBll.getInstance().reconnectRongCloud(callBack);
	}
	
	private ConnectCallback callBack = new ConnectCallback() {
		
		@Override
		public void onSuccess(String arg0) {
			
		}
		
		@Override
		public void onError(ErrorCode arg0) {
			
		}
		
		@Override
		public void onTokenIncorrect() {
			
		}
	};
	
	
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
		readliyTakeSiv = (SexangleImageView) leftHomeView.findViewById(R.id.siv_readliy_take);
		loginSiv = (SexangleImageView) leftHomeView.findViewById(R.id.siv_login);
		homeSiv.setOnSexangleImageClick(listener);
		houseSiv.setOnSexangleImageClick(listener);
		hotelSiv.setOnSexangleImageClick(listener);
		readliyTakeSiv.setOnSexangleImageClick(listener);
		loginSiv.setOnSexangleImageClick(listener);
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
			case R.id.siv_readliy_take:
				onClickReadliyTake();
				break;
			case R.id.siv_login:
				Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
				startActivity(intent);
				break;
			}
		}

		/**
		 * ÀÊ ÷≈ƒ
		 */
		private void onClickReadliyTake() {
			
			Intent intent = new Intent(HomeActivity.this, ReadliyTakeActivity.class);
			startActivity(intent);
		}

		private void onClickHouse() {
			
			Intent intent = new Intent(HomeActivity.this, NavTabActivity.class);
			intent.putExtra(NavTabActivity.INTENT_KEY_TAB_TAG, HouseFragment.class.getSimpleName());
			startActivity(intent);
		}

		private void onClickFocus() {
			
			Intent intent = new Intent(HomeActivity.this, FragmentContentActivity.class);
			intent.putExtra(FragmentContentActivity.EXTRA_FRAGMENT_NAME, FocusFragment.class.getName());
			startActivity(intent);
		}

		private void onClickCommunity() {
			
			Intent intent = new Intent(HomeActivity.this, ForumTabActvity.class);
			intent.putExtra(NavTabActivity.INTENT_KEY_TAB_TAG, ForumTabContentFragment.class.getSimpleName());
			startActivity(intent);
		}
	};
}
