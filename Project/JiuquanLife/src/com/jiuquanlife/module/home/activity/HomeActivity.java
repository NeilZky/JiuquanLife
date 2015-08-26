package com.jiuquanlife.module.home.activity;

import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuquanlife.R;
import com.jiuquanlife.app.App;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.module.base.FragmentContentActivity;
import com.jiuquanlife.module.focus.fragment.FocusFragment;
import com.jiuquanlife.module.forum.activity.ForumTabActvity;
import com.jiuquanlife.module.forum.activity.ReadliyTakeActivity;
import com.jiuquanlife.module.forum.fragment.ForumTabContentFragment;
import com.jiuquanlife.module.home.adapter.HomeAdapter;
import com.jiuquanlife.module.house.fragment.HouseFragment;
import com.jiuquanlife.module.im.RongCloudBll;
import com.jiuquanlife.module.login.LoginActivity;
import com.jiuquanlife.module.tab.HouseTabActivity;
import com.jiuquanlife.utils.ImageLoaderHelper;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.TextViewUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.view.MovingView;
import com.jiuquanlife.view.SexangleImageView;
import com.jiuquanlife.view.SexangleImageView.OnSexangleImageClickListener;

public class HomeActivity extends Activity implements OnPageChangeListener {

	protected static final int REQUEST_LOGIN = 1;
	private SexangleImageView homeSiv;
	private SexangleImageView houseSiv;
	private SexangleImageView hotelSiv;
	private SexangleImageView readliyTakeSiv;
	private SexangleImageView loginSiv;
	private MovingView mv;
	private ViewPager homeVp;
	private View leftHomeView;
	private View rightHomeView;
	private HomeAdapter homeAdapter;
	private ImageView empPhotoIv;
	private ImageView badgeIv;
	private TextView empNameTv;
	private TextView pageTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	public void initView() {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		pageTv = (TextView) findViewById(R.id.tv_page_home);
		badgeIv = (ImageView) findViewById(R.id.iv_badge_home);
		initLoginView();
		initViewPager();
		initSixAngelView();
		initMovingView();
		RongCloudBll.getInstance().reconnectRongCloud(callBack);
	}

	private void initLoginView() {

		empNameTv = (TextView) findViewById(R.id.tv_emp_name_home);
		empPhotoIv = (ImageView) findViewById(R.id.iv_emp_photo_home);
		refreshLoginBtn();
	}

	private void refreshLoginBtn() {

		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER,
				User.class);
		if (user == null) {
			TextViewUtils.setText(empNameTv, "登陆");
			empPhotoIv.setImageResource(R.drawable.login_user);
		} else {
			ImageLoaderHelper.loadRectPhoto(empPhotoIv,
					UrlUtils.getPhotoUrl(user.uid + ""));
			TextViewUtils.setText(empNameTv, user.userName);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshLoginBtn();
		if(App.getInstance().imCount!=0) {
			badgeIv.setVisibility(View.VISIBLE);
		} else {
			badgeIv.setVisibility(View.GONE);
		}
		
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
		homeVp.setOnPageChangeListener(this);
		homeAdapter = new HomeAdapter();
		leftHomeView = getLayoutInflater().inflate(R.layout.view_home_left,
				null);
		rightHomeView = getLayoutInflater().inflate(R.layout.view_home_right,
				null);
		homeAdapter.addView(leftHomeView);
		homeAdapter.addView(rightHomeView);
		homeVp.setAdapter(homeAdapter);
	}

	private void initSixAngelView() {

		homeSiv = (SexangleImageView) leftHomeView.findViewById(R.id.siv_main);
		houseSiv = (SexangleImageView) leftHomeView
				.findViewById(R.id.siv_house);
		hotelSiv = (SexangleImageView) leftHomeView
				.findViewById(R.id.siv_community);
		readliyTakeSiv = (SexangleImageView) leftHomeView
				.findViewById(R.id.siv_readliy_take);
		// loginSiv = (SexangleImageView)
		// leftHomeView.findViewById(R.id.siv_login);
		homeSiv.setOnSexangleImageClick(listener);
		houseSiv.setOnSexangleImageClick(listener);
		hotelSiv.setOnSexangleImageClick(listener);
		readliyTakeSiv.setOnSexangleImageClick(listener);
		// loginSiv.setOnSexangleImageClick(listener);
	}

	private void initMovingView() {

		mv = (MovingView) findViewById(R.id.mv);
		Rect rect = new Rect();
		this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		mv.setBitmap(R.drawable.ic_home_bg);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_LOGIN) {
			refreshLoginBtn();
		}
	}

	OnSexangleImageClickListener listener = new OnSexangleImageClickListener() {

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
			case R.id.siv_marriage:
				onClickMarriage();
			}
		}

		//TODO 跳转到爱上你
		private void onClickMarriage() {
			
		}
		
		//TODO 跳转到找好店
		private void onClickStore() {
			
		}
		
		//TODO 跳转到爱发布
		private void onClickPublish() {
					
		}

		/**
		 * 随手拍
		 */
		private void onClickReadliyTake() {

			Intent intent = new Intent(HomeActivity.this,
					ReadliyTakeActivity.class);
			startActivity(intent);
		}

		private void onClickHouse() {

			Intent intent = new Intent(HomeActivity.this, HouseTabActivity.class);
			intent.putExtra(HouseTabActivity.INTENT_KEY_TAB_TAG,
					HouseFragment.class.getSimpleName());
			startActivity(intent);
		}

		private void onClickFocus() {

			Intent intent = new Intent(HomeActivity.this,
					FragmentContentActivity.class);
			intent.putExtra(FragmentContentActivity.EXTRA_FRAGMENT_NAME,
					FocusFragment.class.getName());
			startActivity(intent);
		}

		private void onClickCommunity() {

			Intent intent = new Intent(HomeActivity.this, ForumTabActvity.class);
			intent.putExtra(HouseTabActivity.INTENT_KEY_TAB_TAG,
					ForumTabContentFragment.class.getSimpleName());
			startActivity(intent);
		}
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_home_login:

			User user = SharePreferenceUtils.getObject(
					SharePreferenceUtils.USER, User.class);
			if (user == null) {
				Intent intent = new Intent(HomeActivity.this,
						LoginActivity.class);
				startActivityForResult(intent, REQUEST_LOGIN);
			} else {
				Intent intent = new Intent(HomeActivity.this,
						HomeUserCenterActivity.class);
				startActivity(intent);
			}

		default:
			break;
		}

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
	public void onPageSelected(int position) {
		
		if(position == 0) {
			pageTv.setText("1/2");
		} else {
			pageTv.setText("2/2");
		}
	}
}
