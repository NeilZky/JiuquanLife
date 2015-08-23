package com.jiuquanlife.module.home.activity;


import java.util.HashMap;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Response.Listener;
import com.jiuquanlife.R;
import com.jiuquanlife.constance.UrlConstance;
import com.jiuquanlife.entity.User;
import com.jiuquanlife.http.RequestHelper;
import com.jiuquanlife.module.base.BaseActivity;
import com.jiuquanlife.module.forum.activity.ProfileAcitivity;
import com.jiuquanlife.module.house.fragment.HouseFragment;
import com.jiuquanlife.module.house.fragment.HouseMineFragment;
import com.jiuquanlife.module.tab.HouseTabActivity;
import com.jiuquanlife.utils.AppUtils;
import com.jiuquanlife.utils.GsonUtils;
import com.jiuquanlife.utils.SharePreferenceUtils;
import com.jiuquanlife.utils.UrlUtils;
import com.jiuquanlife.view.CircleImageView;
import com.jiuquanlife.vo.forum.usercenter.UserCenterJson;
import com.jiuquanlife.vo.forum.usercenter.UserData;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeUserCenterActivity extends BaseActivity{
	
	private CircleImageView civ_photo_user_center;
	private UserCenterJson userCenterJson;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		
		initViews();
		getData();
	}

	private void initViews() {
	
		setContentView(R.layout.activity_home_user_center);
		civ_photo_user_center = (CircleImageView) findViewById(R.id.civ_photo_user_center);
	}
	
	
	public void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("r", "user/userinfo");
		String mAppHash = AppUtils.getAppHash();
		User user = SharePreferenceUtils.getObject(SharePreferenceUtils.USER, User.class);
		map.put("accessToken", user.token);
		map.put("accessSecret", user.secret);
		map.put("appHash", mAppHash);
		map.put("userId", user.uid + "");
		RequestHelper.getInstance().getRequestMap(getActivity(),
				UrlConstance.FORUM_URL, map, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						userCenterJson = GsonUtils.toObj(response, UserCenterJson.class);
						fillView(userCenterJson);
					}
				}, new RequestHelper.OnFinishListener() {

					@Override
					public void onFinish() {
					}
				});
	}

	protected void fillView(UserCenterJson json) {
		
		if(json==null) {
			return;
		}
		setText(R.id.tv_user_title_auc, json.userTitle);
		ImageLoader.getInstance().displayImage( UrlUtils.getPhotoUrl(String.valueOf(json.icon)), civ_photo_user_center);
		if(json.body!=null && json.body.creditShowList!=null) {
			for(UserData ud : json.body.creditShowList) {
				if("credits".equals(ud.type)) {
					setText(R.id.tv_score_user_center, ud.data);
				}
				if("extcredits2".equals(ud.type)) {
					setText(R.id.tv_money_user_center, ud.data);
				}
			}
		}
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ll_profile_mine:
			onClickProfileMine();
			break;
		case R.id.ll_house_home_user_center:
			onClickHouse();
			break;
		case R.id.ll_marriage_home_user_center:
			onClickMarriage();
			break;
		case R.id.ll_job_home_user_center:
			onClickJob();
			break;
		case R.id.ll_publish_home_user_center:
			onClickPublish();
			break;	
		case R.id.ll_store_home_user_center:
			onClickStore();
			break;	
		case R.id.ll_settings_home_user_center:
			onClickSetting();
			break;	
		case R.id.btn_login_out_home_user_center:
			onClickLogout();
			break;
		default:
			break;
		}
	}
	
	private void onClickLogout() {
		
		SharePreferenceUtils.clearAll();
		finish();
	}

	//TODO 跳转到设置
	private void onClickSetting() {
		
	}

	//TODO 跳转到找好店
	private void onClickStore() {
		// TODO Auto-generated method stub
		
	}

	//TODO 跳转到爱发布
	private void onClickPublish() {
		
	}

	//TODO 跳转到找工作
	private void onClickJob() {
		
	}

	//TODO 跳转到爱上你
	private void onClickMarriage() {
		
	}

	private void onClickHouse() {
		
		Intent intent = new Intent(getActivity(), HouseTabActivity.class);
		intent.putExtra(HouseTabActivity.INTENT_KEY_TAB_TAG, HouseMineFragment.class.getSimpleName());
		intent.putExtra(HouseTabActivity.INTENT_KEY_TAB_TAG,
				HouseFragment.class.getSimpleName());
		startActivity(intent);
	}

	private void onClickProfileMine() {
		
		if(userCenterJson!=null && userCenterJson.body!=null &&userCenterJson.body.profileList!=null && !userCenterJson.body.profileList.isEmpty()) {
			Intent intent = new Intent(getActivity(), ProfileAcitivity.class);
			intent.putExtra(ProfileAcitivity.EXTRA_DATA, userCenterJson.body.profileList);
			startActivity(intent);
		} 
	}
	
	
}
